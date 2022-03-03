package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.CustomerReqDao;
import newapp.domain.entity.CustomerReqEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HelloController {

    private final SqlSession sqlSession;
    private final QueryFactory queryFactory;
    private final CustomerReqDao customerReqDao;

    private String getStatement(String ns, String nsId) {
        return ns + "." + nsId;
    }

    @GetMapping(value = {"/"})
    public String index(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();

        String ns = "newapp.crud";
        String nsId = "select";

//        Object result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
//        result = queryFactory.getResult(ns, nsId, result);

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

        CustomerReqEntity param = new CustomerReqEntity();
        List<CustomerReqEntity> result2 = customerReqDao.selectTblCallAssist(param).fetch();
        log.debug("{}", result2);

//        try {
//            nsId = "delete";
//            int r = sqlSession.delete(getStatement(ns, nsId), commandMap.getQueryMap());
//            log.debug("{}", r);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        return "이거왜이래";
    }

    @GetMapping(value = {"/hello.do"})
    public String hello(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return "hello";
    }

}
