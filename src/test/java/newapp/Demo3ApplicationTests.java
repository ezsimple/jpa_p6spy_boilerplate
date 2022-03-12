package newapp;

import newapp.domain.dao.UserDao;
import newapp.domain.entity.UserEntity;
import newapp.global.oauth2.type.RoleType;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class Demo3ApplicationTests {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void contextLoads() {
    newUser("admin@mypms.io", "관리자", "qwer1234", "admin");
  }

  private void newUser(String userEmail, String userNm, String password, String role) {

    /**
     * email Patter 검사
     */
    String regexPattern = "^(.+)@(\\S+)$";
    boolean matched = Pattern.compile(regexPattern)
            .matcher(userEmail)
            .matches();
    assertTrue(matched);

    UserEntity userEntity = new UserEntity();
    userEntity.setUserId(userEmail);
    userEntity.setPassword(passwordEncoder.encode(password));
    userEntity.setUsername(userNm);
    userEntity.setEmail(userEmail);
    userEntity.setEmailVerifiedYn("Y");

    RoleType roleType = RoleType.of("GUEST");
    if(StringUtils.containsAnyIgnoreCase(role, "admin"))
      roleType = RoleType.of("ROLE_ADMIN");

    if(StringUtils.containsAnyIgnoreCase(role, "user"))
      roleType = RoleType.of("ROLE_USER");

    userEntity.setRoleType(roleType);

    LocalDateTime now = LocalDateTime.now();
    userEntity.setRegDt(now);
    userEntity.setModDt(now);

    userDao.save(userEntity);

  }

}
