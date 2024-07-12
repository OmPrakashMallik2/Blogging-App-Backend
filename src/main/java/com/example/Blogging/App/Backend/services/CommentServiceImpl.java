package com.example.Blogging.App.Backend.services;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import com.example.Blogging.App.Backend.Entity.CommentEntity;
import com.example.Blogging.App.Backend.Entity.UserEntity;
import com.example.Blogging.App.Backend.repositories.BlogRepository;
import com.example.Blogging.App.Backend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public CommentEntity getCommentById(Integer commentId) throws Exception {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new Exception("Comment not found of this commentId");
        }
        return comment.get();
    }

    @Override
    public List<CommentEntity> getAllCommentsByBlogId(Integer blogId) throws Exception {
        BlogEntity blog = blogService.getBlogById(blogId);
        List<CommentEntity> commentEntityList = new ArrayList<>();
        for (Integer commentId : blog.getComments()) {
            commentEntityList.add(getCommentById(commentId));
        }
        return commentEntityList;
    }

    @Override
    public List<CommentEntity> getAllCommentsByUserId(Integer userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public CommentEntity addCommentOnBlog(CommentEntity comment, Integer blogId, Integer userId) throws Exception {
        UserEntity user = userService.getUserById(userId);
        BlogEntity blog = blogService.getBlogById(blogId);
        comment.setUserId(user.getUserId());
        comment.setBlogId(blog.getBlogId());
        CommentEntity addedComment = commentRepository.save(comment);

        List<Integer> comments = blog.getComments();
        comments.add(addedComment.getCommentId());
        blog.setComments(comments);
        blogRepository.save(blog);
        return addedComment;
    }

    @Override
    public Boolean likeAComment(Integer commentId, Integer userId) throws Exception {
        CommentEntity comment = getCommentById(commentId);
        UserEntity user = userService.getUserById(userId);
        List<Integer> likes = comment.getLikes();
        if (!likes.contains(userId)) {
            likes.add(userId);
        } else {
            likes.remove(userId);
        }
        comment.setLikes(likes);
        commentRepository.save(comment);
        return true;
    }

    @Override
    public Boolean deleteComment(Integer commentId) throws Exception {
        CommentEntity foundComment = getCommentById(commentId);
        BlogEntity blog = blogService.getBlogById(foundComment.getBlogId());
        List<Integer> comments = blog.getComments();
        comments.remove(commentId);
        blog.setComments(comments);
        blogRepository.save(blog);
        commentRepository.delete(foundComment);
        return true;
    }

    @Override
    public CommentEntity updateComment(Integer commentId, String comment) throws Exception {
        CommentEntity foundComment = getCommentById(commentId);
        if (!comment.isEmpty()) {
            foundComment.setComment(comment);
            commentRepository.save(foundComment);
        } else {
            throw new Exception("Comment cant be empty");
        }
        return foundComment;
    }
}
