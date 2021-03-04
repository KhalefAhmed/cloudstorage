package com.udacity.cloudstorage.controller;

import com.udacity.cloudstorage.entity.*;
import com.udacity.cloudstorage.services.NoteService;

import com.udacity.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("note")
@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm fileForm,
                              @ModelAttribute("newNote") NoteForm noteForm,
                              @ModelAttribute("newCredential") CredentialForm credentialForm, Model model){
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", this.noteService.getNotesByUserId(userId));
        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }

    @PostMapping("add-note")
    public String newNote(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
                          @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
                          Model model){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userId = user.getUserId();
        String newTitle = newNote.getTitle();
        String noteIdStr = newNote.getNoteId();
        String newDescription = newNote.getDescription();
        List<Note> notes = noteService.getNotesByUserId(userId);
        boolean noteIsDuplicate = false;

        for (Note note: notes) {
            if (note.getNoteTitle().equals(newTitle) && note.getNoteDescription().equals(newDescription)) {
                noteIsDuplicate = true;
                break;
            }
        }

        if (noteIdStr.isEmpty() && !noteIsDuplicate) {
            noteService.createNote(newTitle, newDescription, userName);
            model.addAttribute("result", "success");
        }
        else if(noteIsDuplicate){
            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate note.");
        }
        else{
            Note existingNote = getNote(Integer.parseInt(noteIdStr));
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
            model.addAttribute("result", "success");

        }
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        return "result";
    }


    @GetMapping(value = "/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId) {
        return noteService.getNote(noteId);
    }

    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(
            Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("newNote") NoteForm newNote,
            @ModelAttribute("newFile") FileForm newFile, @ModelAttribute("newCredential") CredentialForm newCredential,
            Model model) {

        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("result", "success");
        return "result";
    }
}
