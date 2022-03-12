package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.UserEntity;
import newapp.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional
public class UserDao {

    private final UserRepository userRepository;

    public Optional<UserEntity> getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }

}

