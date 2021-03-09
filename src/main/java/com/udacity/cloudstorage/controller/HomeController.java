package com.udacity.cloudstorage.controller;


import com.udacity.cloudstorage.entity.CredentialForm;
import com.udacity.cloudstorage.entity.FileForm;
import com.udacity.cloudstorage.entity.NoteForm;
import com.udacity.cloudstorage.entity.User;
import com.udacity.cloudstorage.service.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialsService;
    private UserService userService;
    private EncryptionService encryptionService;



    public HomeController(UserService userService, FileService fileService, NoteService noteService,
                          CredentialService credentialsService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("files", this.fileService.getFilesByUserId(userId));
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("credentials", credentialsService.getCredentialsByUserId(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }

    @PostMapping
    public String newFile(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile, Model model) {

        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userId = user.getUserId();
        List<String> files = fileService.getFilesByUserId(userId);


         MultipartFile multipartFile = newFile.getFile();
         String fileName = multipartFile.getOriginalFilename();

         boolean fileIsDuplicate = false;

         for (String file: files) {
             if (file.equals(fileName)) {
                    fileIsDuplicate = true;
                    break;
                }
            }

            if (!fileIsDuplicate) {

                 try {
                  fileService.addFile(multipartFile, userName);
                    }
                 catch (IOException exception) {
                     exception.printStackTrace();
                     model.addAttribute("result", "error");
                     model.addAttribute("message", "The file is too large to upload > 5 MB ");

                 }
                model.addAttribute("result", "success");
            }
            else {
                model.addAttribute("result", "error");
                model.addAttribute("message", "You have tried to add a duplicate file.");
            }

        model.addAttribute("files", fileService.getFilesByUserId(userId));
        return "result";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFiledata();
    }


    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {
        fileService.deleteFile(fileName);
        Integer userId = getUserId(authentication);
        model.addAttribute("files", fileService.getFilesByUserId(userId));
        model.addAttribute("result", "success");

        return "result";
    }
}
