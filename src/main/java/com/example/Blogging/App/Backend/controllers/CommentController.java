package com.example.Blogging.App.Backend.controllers;

import com.example.Blogging.App.Backend.Entity.CommentEntity;
import com.example.Blogging.App.Backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping()
    public List<CommentEntity> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/blog/{blogId}")
    public List<CommentEntity> getAllCommentsByBlogId(@PathVariable Integer blogId) throws Exception {
        return commentService.getAllCommentsByBlogId(blogId);
    }

    @GetMapping("/{commentId}")
    public CommentEntity getCommentById(@PathVariable Integer commentId) throws Exception {
        return commentService.getCommentById(commentId);
    }

    @PostMapping("/{blogId}/{userId}")
    public CommentEntity createComment(@RequestBody CommentEntity comment, @PathVariable Integer blogId, @PathVariable Integer userId) throws Exception {
        return commentService.addCommentOnBlog(comment, blogId, userId);
    }

    @PutMapping("/{commentId}/{userId}")
    public Boolean likeComment(@PathVariable Integer commentId, @PathVariable Integer userId) throws Exception {
        return commentService.likeAComment(commentId, userId);
    }

    @DeleteMapping("/{commentId}")
    public Boolean deleteComment(@PathVariable Integer commentId) throws Exception {
        return commentService.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    public CommentEntity updateComment(@PathVariable Integer commentId, @RequestBody String comment) throws Exception {
        return commentService.updateComment(commentId, comment);
    }

}
