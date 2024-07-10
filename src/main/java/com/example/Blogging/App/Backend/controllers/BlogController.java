package com.example.Blogging.App.Backend.controllers;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping()
    public List<BlogEntity> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{blogId}")
    public BlogEntity getBlogById(@PathVariable Integer blogId) throws Exception {
        return blogService.getBlogById(blogId);
    }

    @GetMapping("/user/{userId}")
    public List<BlogEntity> getAllBlogsByUserId(@PathVariable Integer userId) throws Exception {
        return blogService.getAllBlogByUserId(userId);
    }

    @PostMapping("/{userId}")
    public BlogEntity addBlog(@RequestBody BlogEntity blog, @PathVariable Integer userId) throws Exception {
        return blogService.createBlog(blog, userId);
    }

    @PutMapping("/like/{blogId}/{userId}")
    public Boolean likeBlog(@PathVariable Integer blogId, @PathVariable Integer userId) throws Exception {
        return blogService.likeBlog(blogId, userId);
    }
}
