package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    List<BlogEntity> getAllBlogs();

    BlogEntity getBlogById(Integer blogId) throws Exception;

    List<BlogEntity> getAllBlogByUserId(Integer userId) throws Exception;

    BlogEntity createBlog(BlogEntity blogEntity, Integer userId) throws Exception;

    Boolean likeBlog(Integer blogId, Integer userId) throws Exception;

    Boolean deleteBlog(Integer blogId,Integer userId) throws Exception;

    BlogEntity updateBlog(Integer blogId, Integer userId, BlogEntity newBlog) throws Exception;

}
