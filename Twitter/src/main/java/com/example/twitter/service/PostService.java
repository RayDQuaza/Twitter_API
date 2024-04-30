package com.example.twitter.service;

import com.example.twitter.entity.Post;
import com.example.twitter.entity.User;
import com.example.twitter.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    //private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

//    private static String formatDate(Date date) {
//        try {
//            return DATE_FORMATTER.format(date);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService; // Assuming UserService is available

    public Post createPost(Integer userId, String postBody) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return null;
        }
        Post post = new Post();
        post.setUser(user);
        post.setPostBody(postBody);
        Date ddt=new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = fmt.format(ddt);
        //System.out.println("Formatted date: " + formattedDate);
        post.setDate(formattedDate);
        postRepository.save(post);
        return post;
    }

    public Post getPost(Integer postId) {
        Post pp= postRepository.findById(postId);
        return pp;
    }

    public String editPost(Integer postId, String postBody) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            return "Post does not exist";
        }
        post.setPostBody(postBody);
        postRepository.save(post);
        return "Post edited successfully";
    }

    @Transactional
    public String deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            return "Post does not exist";
        }
        postRepository.deleteById(postId);
        return "Post deleted";
    }

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByDateDesc();
    }


    // Other methods related to posts...
}
