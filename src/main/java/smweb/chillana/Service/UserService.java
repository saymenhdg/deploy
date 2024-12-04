package smweb.chillana.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smweb.chillana.model.RolesModel;
import smweb.chillana.model.UserModel;
import smweb.chillana.repository.RolesRepository;
import smweb.chillana.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel registerUser(UserModel userModel) {
        if (userRepository.findByUsername(userModel.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        RolesModel userRole = rolesRepository.findByRole("USER");
        if (userRole == null) {
            userRole = new RolesModel();
            userRole.setRole("USER");
            rolesRepository.save(userRole);
        }
        userModel.setRole(userRole);
        System.out.println("User has been registered with the USER role.");
        return userRepository.save(userModel);
    }




    public UserModel loginUser(UserModel userModel) {
        UserModel UserExists = userRepository.findByUsername(userModel.getUsername());
        if (UserExists != null && UserExists.getPassword().equals(userModel.getPassword())) {
            System.out.println("User logged in");
            return UserExists;
        }else {
            throw new IllegalArgumentException("Username or password doesn't match");
        }


    }
    public List<UserModel> searchUsers(String query) {
        return userRepository.findByUsernameContainingIgnoreCase(query);
    }




    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setAge(updatedUser.getAge());
            userRepository.save(existingUser);
        }
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
