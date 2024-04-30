package com.example.twitter.service;

import com.example.twitter.entity.Comment;
import com.example.twitter.entity.Post;
import com.example.twitter.entity.User;
import com.example.twitter.repository.CommentRepository;
import com.example.twitter.repository.PostRepository;
import com.example.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public String createComment(Integer postId, Integer userId, String commentBody) {

        User userOptional = userRepository.findByUserID(userId);
        if (userOptional==null) {
            return "User does not exist";
        }

        Post postOptional = postRepository.findById(postId);
        if (postOptional==null) {
            return "Post does not exist";
        }



        Comment comment = new Comment();
        comment.setPost(postOptional);
        comment.setUser(userOptional);
        comment.setCommentBody(commentBody);
        comment.setDate(new Date());
        commentRepository.save(comment);
        return "Comment created successfully";
    }

    public Optional<Comment> getComment(Integer commentId) {
        return commentRepository.findById(commentId);
    }

    public String editComment(Integer commentId, String commentBody) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            return "Comment does not exist";
        }

        Comment comment = commentOptional.get();
        comment.setCommentBody(commentBody);
        commentRepository.save(comment);
        return "Comment edited successfully";
    }

    public String deleteComment(Integer commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            return "Comment does not exist";
        }

        commentRepository.deleteById(commentId);
        return "Comment deleted";
    }

}
