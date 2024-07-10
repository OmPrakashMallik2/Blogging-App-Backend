package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.repositories.BlogRepository;
import com.example.Blogging.App.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BlogEntity> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public BlogEntity getBlogById(Integer blogId) throws Exception {
        Optional<BlogEntity> foundBlog = blogRepository.findById(blogId);
        if (foundBlog.isEmpty()) {
            throw new Exception("Blog of this if not found");
        }
        return foundBlog.get();
    }

    @Override
    public List<BlogEntity> getAllBlogByUserId(Integer userId) throws Exception {
        UserEntity user = userService.getUserById(userId);
        if (user == null) {
            throw new Exception("user of this userId not found.");
        }
        List<BlogEntity> blogEntityList = new ArrayList<>();
        for (Integer blogId : user.getBlogs()) {
            blogEntityList.add(getBlogById(blogId));
        }
        return blogEntityList;
    }

    @Override
    public BlogEntity createBlog(BlogEntity blogEntity, Integer userId) throws Exception {
        UserEntity user = userService.getUserById(userId);

        if (user == null) {
            throw new Exception("user of this userId not found.");
        }
        blogEntity.setUserId(userId);
        BlogEntity addedBlog = blogRepository.save(blogEntity);
        List<Integer> blogs = user.getBlogs();
        blogs.add(addedBlog.getBlogId());
        user.setBlogs(blogs);
        userRepository.save(user);
        return addedBlog;
    }

    @Override
    public Boolean likeBlog(Integer blogId, Integer userId) throws Exception {
        BlogEntity blog = getBlogById(blogId);
        UserEntity user = userService.getUserById(userId);

        if (blog == null || user == null) {
            throw new Exception("user or blog not found");
        }
        if (!blog.getLikes().contains(userId)) {
            blog.getLikes().add(userId);
        } else {
            blog.getLikes().remove(userId);
        }
        blogRepository.save(blog);
        return true;
    }
}
