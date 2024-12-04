package smweb.chillana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smweb.chillana.model.FollowersModel;
import smweb.chillana.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowersModel, Integer> {
    Optional<FollowersModel> findByFollowerAndFollowing(UserModel Follower,UserModel Following);
    List<FollowersModel> findByFollower(UserModel Follower);
    List<FollowersModel> findByFollowing(UserModel Following);
}
