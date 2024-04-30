package com.example.twitter.controller;


import com.example.twitter.entity.Comment;
import com.example.twitter.entity.MyError;

import com.example.twitter.entity.User;
import com.example.twitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequest request) {
        Integer postId= request.getPostID();
        Integer userId = request.getUserID();
        String commentBody= request.getCommentBody();
        String response = commentService.createComment(postId, userId, commentBody);
        if(response.equals("Comment created successfully"))
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        MyError m=new MyError(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam Integer commentID) {
        Optional<Comment> commentOptional = commentService.getComment(commentID);
        if (commentOptional.isEmpty()) {
            MyError m=new MyError("Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
        CFD dd=new CFD(commentOptional);
        //dd.setCC(new UFD(cd.getUser()));
        //String a=dd.toString();
//        return ResponseEntity.ok(commentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(dd);
    }

//    @GetMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getComment(@RequestParam Integer CommentID) {
//        Optional<Comment> commentOptional = commentService.getComment(CommentID);
//        if (commentOptional.isEmpty()) {
//            MyError m=new MyError("Comment does not exist");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
//        }
//        CFD dd=new CFD(commentOptional.get());
////        return ResponseEntity.ok(commentOptional.get());
//        return ResponseEntity.ok(dd);
//    }


    @PatchMapping("/comment")
    public ResponseEntity<?> editComment(@RequestBody CommentEditRequest request) {
        Integer commentId = request.getCommentID();
        String commentBody = request.getCommentBody();
        String response = commentService.editComment(commentId, commentBody);
        if(response.equals("Comment does not exist"))
        {
            MyError m=new MyError("Comment does not exist");
            return ResponseEntity.ok(m);

        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam Integer commentID) {
        String response = commentService.deleteComment(commentID);
        if(response.equals("Comment does not exist"))
        {
            MyError m=new MyError("Comment does not exist");
            return ResponseEntity.ok(m);

        }
        return ResponseEntity.ok(response);
    }




    public class CFD{
        Integer commentID;
        String commentBody;
        UFD CommentCreator;

        CFD(Optional<Comment> cmt){
            this.commentID=cmt.get().getId();
            this.commentBody=cmt.get().getCommentBody();
            this.CommentCreator= new UFD(cmt.get().getUser());
        }

        public Integer getCommentID() {
            return commentID;
        }

        public void setCommentID(Integer commentID) {
            this.commentID = commentID;
        }

        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public UFD getCommentCreator() {
            return CommentCreator;
        }

        public void setCommentCreator(UFD commentCreator) {
            CommentCreator = commentCreator;
        }
    }


    class UFD{
        Integer userID;
        String name;

        UFD(User user){
            this.userID= user.getUserId();
            this.name= user.getName();
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class CommentCreateRequest {
        String commentBody;
        Integer postID;
        Integer userID;

        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public Integer getPostID() {
            return postID;
        }

        public void setPostID(Integer postID) {
            this.postID = postID;
        }
    }

    public static class CommentEditRequest {
        String commentBody;
        Integer commentID;

        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public Integer getCommentID() {
            return commentID;
        }

        public void setCommentID(Integer commentID) {
            this.commentID = commentID;
        }
    }

}
