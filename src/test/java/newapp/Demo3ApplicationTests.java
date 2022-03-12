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
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@WebAppConfiguration
class Demo3ApplicationTests {

  @Autowired
  private UserDao userDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void contextLoads() {
    // newUser("admin@mypms.io", "관리자", "qwer1234", "admin");

  }


}
