package newapp.domain.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.entity.UserEntity;
import newapp.domain.repository.UserRepository;
import newapp.global.oauth2.type.RoleType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional
public class UserDao {

    private final UserRepository userRepository;

    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * 주의 : password는 암호화된 값을 전달해야 합니다.
     * @param userEmail
     * @param userNm
     * @param password
     * @param role
     * @return
     * @throws Exception
     */
    public UserEntity newUser(String userEmail, String userNm, String password, String role) throws Exception {

        /**
         * email Patter 검사
         */
        String regexPattern = "^(.+)@(\\S+)$";
        boolean matched = Pattern.compile(regexPattern)
                .matcher(userEmail)
                .matches();
        assertTrue(matched);

        Optional<UserEntity> entity = findByUserId(userEmail);
        if (entity.isPresent())
            throw new Exception("Already exist userId : " + userEmail);

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userEmail);
        userEntity.setPassword(password);
        userEntity.setUsername(userNm);
        userEntity.setEmail(userEmail);
        userEntity.setEmailVerifiedYn("Y");

        RoleType roleType = RoleType.of("GUEST");
        if (StringUtils.containsAnyIgnoreCase(role, "admin"))
            roleType = RoleType.of("ROLE_ADMIN");

        if (StringUtils.containsAnyIgnoreCase(role, "user"))
            roleType = RoleType.of("ROLE_USER");

        userEntity.setRoleType(roleType);

        LocalDateTime now = LocalDateTime.now();
        userEntity.setRegDt(now);
        userEntity.setModDt(now);

        return save(userEntity);
    }

}

