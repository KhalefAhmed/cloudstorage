package com.udacity.cloudstorage.service;


import com.udacity.cloudstorage.entity.User;
import com.udacity.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvaible(String username){
        return userMapper.getUser(username) == null;
    }

    public int creatUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);
        return userMapper.insertUser(new User(null, user.getUsername() ,encodedSalt ,hashedPassword, user.getFirstName() ,user.getLastName()));
    }

    public User getUser(String username){
        return userMapper.getUser(username);
    }
}
