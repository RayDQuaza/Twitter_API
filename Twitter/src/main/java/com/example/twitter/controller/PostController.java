package com.example.twitter.controller;

import com.example.twitter.entity.Comment;
import com.example.twitter.entity.MyError;
import com.example.twitter.entity.Post;
import com.example.twitter.entity.User;
import com.example.twitter.service.PostService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class PostController {



    @Autowired
    private PostService postService;

//    @PostMapping("/post")
//    public ResponseEntity<String> createPost(@RequestParam Integer userId, @RequestParam String postBody) {
//        Post post = postService.createPost(userId, postBody);
//        if (post == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
//    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest request) {
        String postBody= request.getPostBody();
        Integer userId= request.getUserID();
        Post post = postService.createPost(userId, postBody);
        if (post == null) {
            MyError m=new MyError("User does not exist");
//            String abc="User does not exist";
//            Gson gson = new Gson();
//
////            String exampleString = "Hello, world!";
//            // Convert string to JSON. Wrap the string in an object structure if needed.
//            String jsonString = gson.toJson(abc);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam Integer postID) {
        Post post = postService.getPost(postID);
        if (post == null) {
            MyError m=new MyError("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
//        System.out.println(post.getPostBody());
//        Object ob=new Object(){
//          public String postBody= post.getPostBody();
//          public List<Comment> LULU = post.getComments();
//        };

        // List<UserDisplay> userDisplayList = UserUtils.convertToUserDisplayList(userList);
        PFD pp=new PFD(post);
        return ResponseEntity.status(HttpStatus.OK).body(pp);
    }

//    @PatchMapping("/post")
//    public ResponseEntity<String> editPost(@PathVariable Integer postId, @RequestParam String postBody) {
//        String response = postService.editPost(postId, postBody);
//        if (response.equals("Post does not exist")) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@RequestBody EditPostRequest request) {
        Integer postId= request.getPostID();
        String postBody= request.getPostBody();
        String response = postService.editPost(postId, postBody);
        MyError m=new MyError(response);
        if (response.equals("Post does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam Integer postID) {
        String response = postService.deletePost(postID);
        MyError m=new MyError(response);
        if (response.equals("Post does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/")
    public ResponseEntity<List<PFD>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PFD> pps=new ArrayList<>();
        for(int i=0;i<posts.size();i++){
            pps.add(new PFD(posts.get(i)));
        }
        Collections.reverse(pps);
        return ResponseEntity.status(HttpStatus.OK).body(pps);
        //return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    class PFD{
        Integer postID;
        String postBody;
        String date;
        List<CFD2> comments;

        PFD(Post pp){
            this.postID=pp.getId();
            this.postBody=pp.getPostBody();
            this.date=pp.getDate();
            ArrayList<CFD2> temp=new ArrayList<>();
            for(int i=0;i<pp.getComments().size();i++){
                temp.add(new CFD2(Optional.ofNullable(pp.getComments().get(i))));
            }
            this.comments=temp;
        }

        public Integer getPostID() {
            return postID;
        }

        public void setPostID(Integer postID) {
            this.postID = postID;
        }

        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<CFD2> getComments() {
            return comments;
        }

        public void setComments(List<CFD2> comments) {
            this.comments = comments;
        }
    }

    public class CFD2{
        Integer commentID;
        String commentBody;
        UFD2 CommentCreator;

        CFD2(Optional<Comment> cmt){
            this.commentID=cmt.get().getId();
            this.commentBody=cmt.get().getCommentBody();
            this.CommentCreator= new UFD2(cmt.get().getUser());
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

        public UFD2 getCommentCreator() {
            return CommentCreator;
        }

        public void setCommentCreator(UFD2 commentCreator) {
            CommentCreator = commentCreator;
        }
    }


    class UFD2{
        Integer userID;
        String name;

        UFD2(User user){
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

//    class CFD{
//        Integer commentID;
//        String commentBody;
//        CommentController.UFD CommentCreator;
//
//        CFD(Optional<Comment> cmt){
//            this.commentID=cmt.get().getId();
//            this.commentBody=cmt.get().getCommentBody();
//            this.CommentCreator= new UFD(cmt.get().getUser());
//        }
//
//        public Integer getCommentID() {
//            return commentID;
//        }
//
//        public void setCommentID(Integer commentID) {
//            this.commentID = commentID;
//        }
//
//        public String getCommentBody() {
//            return commentBody;
//        }
//
//        public void setCommentBody(String commentBody) {
//            this.commentBody = commentBody;
//        }
//
//        public CommentController.UFD getCommentCreator() {
//            return CommentCreator;
//        }
//
//        public void setCommentCreator(CommentController.UFD commentCreator) {
//            CommentCreator = commentCreator;
//        }
//    }
//
//
//    class UFD{
//        Integer userID;
//        String name;
//
//        UFD(User user){
//            this.userID= user.getUserId();
//            this.name= user.getName();
//        }
//
//        public Integer getUserID() {
//            return userID;
//        }
//
//        public void setUserID(Integer userID) {
//            this.userID = userID;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }


    private static class PostCreateRequest {
        String postBody;
        Integer userID;

        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }
    }

    private static class EditPostRequest {
        String postBody;
        Integer postID;

        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public Integer getPostID() {
            return postID;
        }

        public void setPostID(Integer postID) {
            this.postID = postID;
        }
    }

    public static class PostDisplay{
        Integer postID;
        String postBody;
        Date date;
        List<CommentDisplay> comments;

        public Integer getPostID() {
            return postID;
        }

        public void setPostID(Integer postID) {
            this.postID = postID;
        }

        public String getPostBody() {
            return postBody;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public List<CommentDisplay> getComments() {
            return comments;
        }

//        public void setComments(List<Comment> comments) {
//            List<CommentDisplay> cc=new ArrayList<CommentDisplay>();
//            for(int i=0;i< comments.size();i++)
//            {
//                CommentDisplay a=new CommentDisplay();
//                a.setCommentId(comments.get(i).getId());
//                a.setCommentBody(comments.get(i).getCommentBody());
//                cc.add(new CommentDisplay());
//            }
//        }
    }




    public static class UserDisplay{
        Integer userID;
        String name;

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

        public static List<User.UserDisplayFP> convertToUserDisplayList(List<User> userList) {
            return userList.stream()
                    .map(User::toUserDisplay)
                    .collect(Collectors.toList());
        }
    }

    public static class CommentDisplay{
        Integer commentId;
        String commentBody;
        UserDisplay user;

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public String getCommentBody() {
            return commentBody;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public UserDisplay getUser() {
            return user;
        }

        public void setUser(UserDisplay user) {
            this.user = user;
        }
    }

}



