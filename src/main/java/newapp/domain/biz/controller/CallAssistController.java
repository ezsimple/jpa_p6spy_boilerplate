package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.biz.service.CallAssistService;
import newapp.global.util.SessionUtil;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
class CallAssistController {

    private final CallAssistService callAssistService;
    private final SessionUtil sessionUtil;

    /**
     * 조회 및 검색 하기
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = {"/board/call_assist.do"})
    public Map searchCallAssist(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return callAssistService.searchCallAssist(model, commandMap);
    }

    /**
     * 리스트 페이지 보기
     * @param session
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/board/call_assist.do"})
    public String listCallAssist(Session session, ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();

        String projNm = sessionUtil.getUserProjectNm();
        model.addAttribute("projNm", projNm);
        return "board/call_assist";
    }

    /**
     * 엑셀 다운로드
     * @param request
     * @param response
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = {"/board/call_assist_excel.do"})
    public String excelCallAssist(HttpServletRequest request, HttpServletResponse response
            , ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return callAssistService.excelDownload(request, response, model, commandMap);
    }

    /**
     * 상세보기
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/board/call_assist_view.do"})
    public String selectCallAssistView(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return callAssistService.selectCallAssistView(model, commandMap, "view");
    }

    /**
     * 리스트와 함께 상세보기
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/board/call_assist_inner.do"})
    public String selectCallAssistInner(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return callAssistService.selectCallAssistView(model, commandMap, "inner");
    }

    /**
     * 추가,수정,삭제 처리
     * @param model
     * @param commandMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/board/call_assist_view.do"})
    public String upsertCallAssistView(ModelMap model, CommandMap commandMap) throws Exception {
        commandMap.debugParams();
        return callAssistService.upsertCallAssistView(model, commandMap);
    }

}
