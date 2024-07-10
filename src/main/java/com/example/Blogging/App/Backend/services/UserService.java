package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.Entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserEntity> getAllUser();
    public UserEntity createUser(UserEntity user);
    public UserEntity getUserById(Integer userId) throws Exception;
    public Boolean followUser(Integer userId1, Integer userId2) throws Exception;

}
