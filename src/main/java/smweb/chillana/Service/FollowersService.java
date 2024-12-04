package smweb.chillana.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smweb.chillana.model.FollowersModel;
import smweb.chillana.model.UserModel;
import smweb.chillana.repository.FollowRepository;
import smweb.chillana.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FollowersService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public FollowersService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public FollowersModel followUser(String followerUsername,String followingUsername) {
        UserModel follower = userRepository.findByUsername(followerUsername);
        UserModel following = userRepository.findByUsername(followingUsername);

        Optional<FollowersModel> exsitingFollower = followRepository.findByFollowerAndFollowing(follower, following);
        if (exsitingFollower.isPresent()) {
            throw new IllegalStateException("Already following user");
        }
        FollowersModel followerModel = new FollowersModel();
        followerModel.setFollower(follower);
        followerModel.setFollowing(following);
        return followRepository.save(followerModel);
    }

    public void unfollowUser(String followerUsername,String followingUsername) {
        UserModel follower = userRepository.findByUsername(followerUsername);
        UserModel following = userRepository.findByUsername(followingUsername);
        Optional<FollowersModel> followersModel = followRepository.findByFollowerAndFollowing(follower,following);

        if (followersModel.isPresent()) {
            followRepository.delete(followersModel.get());
        }else {
            throw new IllegalStateException("already not following user");
        }
    }

    public boolean isFollowing(String followerUsername,String followingUsername) {
        UserModel follower = userRepository.findByUsername(followerUsername);
        UserModel following = userRepository.findByUsername(followingUsername);

        return followRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }

    public List<UserModel> getFollowers(UserModel user) {
        return followRepository.findByFollowing(user)
                .stream()
                .map(FollowersModel::getFollower)
                .toList();
    }

    public List<UserModel> getFollowing(UserModel user) {
        return followRepository.findByFollower(user)
                .stream()
                .map(FollowersModel::getFollowing)
                .toList();
    }
}
