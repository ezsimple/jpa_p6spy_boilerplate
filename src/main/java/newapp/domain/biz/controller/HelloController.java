package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.CustomerReqDao;
import newapp.domain.entity.CustomerReqEntity;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    /**
     * Test : bash$  data=$(shuf -i 60-99 -n 1); curl -XGET "http://localhost:8000/?no=3&data=$data"
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/"})
    public String index(ModelMap model, CommandMap commandMap) throws Exception {
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

        CustomerReqEntity param = new CustomerReqEntity();
        List<CustomerReqEntity> result2 = customerReqDao.selectTblCallAssist(param).fetch();
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

        return "index";
    }

    @GetMapping(value = {"/hello.do"})
    public String hello(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return "hello";
    }

}
