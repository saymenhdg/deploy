package smweb.chillana.repository;


import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import smweb.chillana.model.UserModel;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByUsername(String username);
    List<UserModel> findByUsernameContainingIgnoreCase(String username);
}
