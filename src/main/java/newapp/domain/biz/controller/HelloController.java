package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.*;
import newapp.domain.dto.CustomerReqDTO;
import newapp.domain.dto.SearchDTO;
import newapp.domain.entity.CodeEntity;
import newapp.domain.entity.CompanyEntity;
import newapp.domain.entity.ProjectEntity;
import newapp.domain.entity.UserEntity;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HelloController {

    private final SqlSession sqlSession;
    private final PasswordEncoder passwordEncoder;

    private final QueryFactory queryFactory;
    private final CustomerReqDao customerReqDao;
    private final CodeDao codeDao;
    private final CompanyDao companyDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;

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

        return "hello";
    }

    @ResponseBody
    @GetMapping(value = {"/init"})
    public String init() throws Exception {

        LocalDateTime now = LocalDateTime.now();

        // 코드 종류 : 000
        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setUseYn("Y");
        codeEntity.setGname("종류");
        codeEntity.setGid("000");
        codeEntity.setCname("버거");
        codeEntity.setCid("000");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("개선");
        codeEntity.setCid("001");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("요구");
        codeEntity.setCid("002");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("문의");
        codeEntity.setCid("003");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("기타");
        codeEntity.setCid("004");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        // 코드 : 진행상황 : 001
        codeEntity.setGname("진행상황");
        codeEntity.setGid("001");
        codeEntity.setCname("대기");
        codeEntity.setCid("000");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("접수");
        codeEntity.setCid("001");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("검토");
        codeEntity.setCid("002");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("완료");
        codeEntity.setCid("003");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("보류");
        codeEntity.setCid("004");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        codeEntity.setCname("기각");
        codeEntity.setCid("005");
        codeEntity.setCode6(codeEntity.getGid()+codeEntity.getCid());
        codeEntity.setOrderNo(Long.parseLong(codeEntity.getCid()));
        codeDao.save(codeEntity);

        // 프로젝트 초기화
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setUseYn("Y");
        projectEntity.setRegId("system");
        projectEntity.setRegDt(now);
        projectEntity.setModId("system");
        projectEntity.setModDt(now);
        projectEntity.setProjNo(0L);
        projectEntity.setProjNm("ZMON");
        projectEntity.setPickYn("Y");
        ProjectEntity proj1 = projectDao.save(projectEntity);

        // 회사정보
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setUseYn("Y");
        companyEntity.setNo(0L);
        companyEntity.setName("INSoft");
        companyEntity.setMemo("아이엔소프트");
        companyEntity.setProjectEntity(proj1);
        companyDao.save(companyEntity);

        companyEntity.setNo(1L);
        companyEntity.setName("SK C&C");
        companyEntity.setMemo("SK C&C");
        companyEntity.setProjectEntity(proj1);
        companyDao.save(companyEntity);

        // 기본 사용자 추가
        String password = passwordEncoder.encode("qwer1234");
        UserEntity user1 = userDao.newUser("admin@mypms.io", "관리자", password, "admin");
        UserEntity user2 = userDao.newUser("mhlee@in-soft.co.kr", "이민호", password, "user");

        // 프로젝트 할당
        user1.setProjectEntity(proj1);
        userDao.save(user1);

        // 프로젝트 할당
        user2.setProjectEntity(proj1);
        userDao.save(user2);

        return "init finished";
    }


}
