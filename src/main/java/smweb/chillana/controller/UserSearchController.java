package smweb.chillana.controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smweb.chillana.Service.UserService;
import smweb.chillana.model.UserModel;
import smweb.chillana.model.UserProfileModel;
import smweb.chillana.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserSearchController {



    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<UserModel>> searchUsers(@RequestParam("query") String query) {
        List<UserModel> users = userService.searchUsers(query);
        users.forEach(user -> user.setUserProfileModel(null));
        return ResponseEntity.ok(users);
    }


}
