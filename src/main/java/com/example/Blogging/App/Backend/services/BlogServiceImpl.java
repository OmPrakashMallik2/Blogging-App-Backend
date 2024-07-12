package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.Entity.CommentEntity;
import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.repositories.BlogRepository;
import com.example.Blogging.App.Backend.repositories.CommentRepository;
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
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

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
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new Exception("user of this userId not found.");
        }
        UserEntity user = foundUser.get();
        List<BlogEntity> blogEntityList = new ArrayList<>();
        for (Integer blogId : user.getBlogs()) {
            blogEntityList.add(getBlogById(blogId));
        }
        return blogEntityList;
    }

    @Override
    public BlogEntity createBlog(BlogEntity blogEntity, Integer userId) throws Exception {
        Optional<UserEntity> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new Exception("user of this userId not found of this userId");
        }
        UserEntity user = foundUser.get();
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
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (blog == null || foundUser.isEmpty()) {
            throw new Exception("user or blog not found");
        }
        UserEntity user = foundUser.get();
        if (!blog.getLikes().contains(userId)) {
            blog.getLikes().add(userId);
        } else {
            blog.getLikes().remove(userId);
        }
        blogRepository.save(blog);
        return true;
    }

    @Override
    public Boolean deleteBlog(Integer blogId, Integer userId) throws Exception {
        BlogEntity blog = getBlogById(blogId);
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new Exception("user not found of this userId");
        }
        UserEntity user = foundUser.get();
        if (user.getBlogs().contains(blog.getBlogId())) {
            List<CommentEntity> comments = commentRepository.findByUserId(blogId);
            for (CommentEntity comment : comments) {
                commentRepository.delete(comment);
            }
            blogRepository.delete(blog);
            List<Integer> blogs = user.getBlogs();
            blogs.remove(blogId);
            user.setBlogs(blogs);
            userRepository.save(user);
        } else {
            throw new Exception("blog not found in user's blog list");
        }
        return true;
    }

    @Override
    public BlogEntity updateBlog(Integer blogId, Integer userId, BlogEntity newBlog) throws Exception {
        BlogEntity blog = getBlogById(blogId);
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new Exception("user not found of this userId");
        }
        UserEntity user = foundUser.get();
        if (user.getBlogs().contains(blog.getBlogId())) {
            blog.setHeading(newBlog.getHeading());
            blog.setContent(newBlog.getContent());
            blogRepository.save(blog);
        } else {
            throw new Exception("blog not found in user's blog list");
        }
        return blog;
    }
}
