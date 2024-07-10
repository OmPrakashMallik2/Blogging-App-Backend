package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public UserEntity getUserById(Integer userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new Exception("User not found of this userId");
        }
        return userEntity.get();
    }

    @Override
    public Boolean followUser(Integer userId1, Integer userId2) throws Exception {
        UserEntity user1 = getUserById(userId1);
        UserEntity user2 = getUserById(userId2);

        if (user1 == null || user2 == null || user1 == user2) {
            return false;
        }
        if (!user1.getFollowers().contains(userId2)) {
            user1.getFollowers().add(userId2);
            user2.getFollowings().add(userId1);
        } else {
            return null;
        }

        userRepository.save(user1);
        userRepository.save(user2);
        return true;
    }
}
