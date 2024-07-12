package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.Entity.CommentEntity;
import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.repositories.BlogRepository;
import com.example.Blogging.App.Backend.repositories.CommentRepository;
import com.example.Blogging.App.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

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
            user1.getFollowers().remove(userId2);
            user2.getFollowings().remove(userId1);
        }

        userRepository.save(user1);
        userRepository.save(user2);
        return true;
    }

    @Override
    public Boolean deleteUser(Integer userId) throws Exception {
        UserEntity foundUser = getUserById(userId);
        List<Integer> blogs = foundUser.getBlogs();
        for (Integer blogId : blogs) {
            Optional<BlogEntity> blog = blogRepository.findById(blogId);
            blog.ifPresent(blogEntity -> blogRepository.delete(blogEntity));
        }

        List<CommentEntity> comments = commentRepository.findByUserId(userId);
        for (CommentEntity comment : comments) {
            commentRepository.delete(comment);
        }

        // delete followings
        List<Integer> followings = foundUser.getFollowings();
        for (Integer followingId : followings) {
            UserEntity person = getUserById(followingId);
            List<Integer> followers = person.getFollowers();
            followers.remove(userId);
            person.setFollowers(followers);
            userRepository.save(person);
        }

        // delete followers
        List<Integer> followers = foundUser.getFollowers();
        for (Integer followerId : followers) {
            UserEntity person = getUserById(followerId);
            List<Integer> following = person.getFollowers();
            following.remove(userId);
            person.setFollowings(followers);
            userRepository.save(person);
        }

        userRepository.delete(foundUser);
        return true;
    }

    @Override
    public UserEntity updateUser(Integer userId, UserEntity updatedUser) throws Exception {
        UserEntity foundUser = getUserById(userId);

        if (updatedUser.getName() != null) {
            foundUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            foundUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            foundUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getBio() != null) {
            foundUser.setBio(updatedUser.getBio());
        }

        if (updatedUser.getBlogs() != null) {
            foundUser.setBlogs(updatedUser.getBlogs());
        }

        if (updatedUser.getFollowers() != null) {
            foundUser.setFollowers(updatedUser.getFollowers());
        }

        if (updatedUser.getFollowings() != null) {
            foundUser.setFollowings(updatedUser.getFollowings());
        }

        return userRepository.save(foundUser);
    }
}
