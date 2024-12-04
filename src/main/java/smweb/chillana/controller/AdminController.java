package smweb.chillana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smweb.chillana.Service.PostService;
import smweb.chillana.Service.UserService;
import smweb.chillana.model.PostModel;
import smweb.chillana.model.UserModel;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<UserModel> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        UserModel user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/editUser";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") UserModel updatedUser) {
        userService.updateUser(updatedUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/posts")
    public String managePosts(Model model) {
        try {
            List<PostModel> posts = postService.getAllPosts();
            model.addAttribute("posts", posts);
        } catch (Exception e) {
            System.err.println("Error fetching posts: " + e.getMessage());
        }
        return "admin/managePosts";
    }


    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable("id") int id, Model model) {
        PostModel post = postService.getAllPosts().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        model.addAttribute("post", post);
        return "admin/editPost";
    }


    @PostMapping("/posts/update")
    public String updatePost(@ModelAttribute("post") PostModel post) {
        PostModel existingPost = postService.findPostById(post.getId());
        existingPost.setCaption(post.getCaption());
        if (post.getPostImgFile() != null && !post.getPostImgFile().isEmpty()) {
            try {
                byte[] imageBytes = post.getPostImgFile().getBytes();
                existingPost.setPostImg(imageBytes);
            } catch (IOException e) {
                System.err.println("Error uploading file: " + e.getMessage());
            }
        }

        
        postService.updatePost(existingPost);
        return "redirect:/admin/posts";
    }



    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable("id") int id) {
        postService.deletePostById(id);
        return "redirect:/admin/posts";
    }
}
