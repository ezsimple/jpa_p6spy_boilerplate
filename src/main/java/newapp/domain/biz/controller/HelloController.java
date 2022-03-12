package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.CustomerReqDao;
import newapp.domain.dao.UserDao;
import newapp.domain.dto.CustomerReqDTO;
import newapp.domain.dto.SearchDTO;
import newapp.domain.entity.CustomerReqEntity;
import newapp.domain.entity.UserEntity;
import newapp.global.oauth2.type.RoleType;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HelloController {

    private final SqlSession sqlSession;
    private final QueryFactory queryFactory;
    private final CustomerReqDao customerReqDao;

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    private String getStatement(String ns, String nsId) {
        return ns + "." + nsId;
    }

    @GetMapping(value = {"/"})
    public String index(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return "index";
    }

    /**
     * Test : bash$  data=$(shuf -i 60-99 -n 1); curl -XGET "http://localhost:8000/?no=3&data=$data"
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = {"/hello"})
    public String hello(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();

        String ns = "newapp.crud";
        String nsId = "select";

        nsId = "insert";
        int r = sqlSession.insert(getStatement(ns, nsId), commandMap.getQueryMap());
        log.debug("{}", r);

        nsId = "update";
        r = sqlSession.update(getStatement(ns, nsId), commandMap.getQueryMap());
        log.debug("{}", r);

        nsId = "select";
        Object result = sqlSession.selectList(getStatement(ns, nsId), commandMap.getQueryMap());
        log.debug("{}", result);

        result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
        log.debug("{}", result);

        SearchDTO param = new SearchDTO();
        List<CustomerReqDTO> result2 = customerReqDao.selectTblCallAssist(param).fetch();
        log.debug("{}", result2);

        nsId = "selectMaxNo";
        result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
        // result = queryFactory.getResult(ns, nsId, result);
        Map<String, Object> map = queryFactory.toMap(result);
        log.debug("{}", map);

        nsId = "delete";
        CaseInsensitiveMap params = new CaseInsensitiveMap(commandMap.getQueryMap());
        params.put("no", map.get("maxNo"));
        result = queryFactory.execute(ns, nsId, params);
        // result = queryFactory.getResult(ns, nsId, result);
        r = queryFactory.toInt(result);
        log.debug("{}", r);

        newUser("admin@mypms.io", "관리자", "qwer1234", "admin");

        return "hello";
    }


    private void newUser(String userEmail, String userNm, String password, String role) throws Exception {

        /**
         * email Patter 검사
         */
        String regexPattern = "^(.+)@(\\S+)$";
        boolean matched = Pattern.compile(regexPattern)
                .matcher(userEmail)
                .matches();
        assertTrue(matched);

        UserEntity userEntity = userDao.getUser(userEmail);
        if(userEntity!=null)
            throw new Exception("Already exist userId : " + userEmail);

        userEntity = new UserEntity();
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
