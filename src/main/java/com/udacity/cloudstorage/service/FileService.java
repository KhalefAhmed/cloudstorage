package com.udacity.cloudstorage.service;


import com.udacity.cloudstorage.entity.File;
import com.udacity.cloudstorage.mapper.FileMapper;
import com.udacity.cloudstorage.mapper.UserMapper;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<String> getFilesByUserId(Integer userId){
        return fileMapper.getFilesByUserId(userId);
    }

    public void addFile(MultipartFile multipartFile, String userName) throws IOException {

        try {
            byte[] fileData = multipartFile.getBytes();
            String fileName = multipartFile.getOriginalFilename();
            String contentType = multipartFile.getContentType();
            String fileSize = String.valueOf(multipartFile.getSize());
            Integer userId = userMapper.getUser(userName).getUserId();
            File file = new File(null, fileName, contentType, fileSize, userId, fileData);
            fileMapper.insert(file);
        }catch (IOException e) {
            // @TODO log an error and throw a new exception (?)
            System.out.println(e.getMessage());
        }




    }

    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }

    }
