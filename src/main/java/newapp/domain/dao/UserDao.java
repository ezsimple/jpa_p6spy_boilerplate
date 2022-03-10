package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.UserEntity;
import newapp.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserDao {
    private final UserRepository userRepository;

    public UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
