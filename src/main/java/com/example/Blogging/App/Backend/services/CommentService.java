package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentEntity getCommentById(Integer commentId) throws Exception;

    List<CommentEntity> getAllCommentsByBlogId(Integer blogId) throws Exception;

    List<CommentEntity> getAllCommentsByUserId(Integer userId);

    List<CommentEntity> getAllComments();

    CommentEntity addCommentOnBlog(CommentEntity comment, Integer blogId, Integer userId) throws Exception;

    Boolean likeAComment(Integer commentId, Integer userId) throws Exception;

    Boolean deleteComment(Integer commentId) throws Exception;

    CommentEntity updateComment(Integer commentId, String comment) throws Exception;
}
