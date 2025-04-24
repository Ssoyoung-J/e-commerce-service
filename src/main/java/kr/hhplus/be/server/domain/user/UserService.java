package kr.hhplus.be.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserInfo.User getUser(Long userId) {
        User user = userRepository.findById(userId);

        return UserInfo.User.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }
}
