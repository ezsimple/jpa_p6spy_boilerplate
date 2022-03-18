package newapp.domain.biz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.DateUtil;
import io.mkeasy.utils.MapUtil;
import io.mkeasy.webapp.processor.ExcelFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.CompanyDao;
import newapp.domain.dao.CustomerReqDao;
import newapp.domain.dao.UserDao;
import newapp.domain.dto.CustomerReqDTO;
import newapp.domain.dto.SearchDTO;
import newapp.domain.dto.StatDTO;
import newapp.domain.entity.CompanyEntity;
import newapp.domain.entity.CustomerReqEntity;
import newapp.domain.entity.UserEntity;
import newapp.global.common.service.AbstractService;
import newapp.global.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallAssistService extends AbstractService {

    private final CustomerReqDao customerReqDao;
    private final SessionUtil sessionUtil;
    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final ObjectMapper objectMapper;

    /**
     * 목록 조회 및 검색 하기
     *
     * @param model
     * @param commandMap
     * @return
     */
    public Map searchCallAssist(ModelMap model, CommandMap commandMap) throws JsonProcessingException {

        storeProjNm(model);

        SearchDTO searchDTO = getSearchDTO(commandMap);

        Map resultMap = MapUtil.newMap();

        List<CustomerReqDTO> list = customerReqDao.selectTblCallAssist(searchDTO).fetch();
        resultMap.put("rows", list);

        StatDTO statDTO = customerReqDao.statTblCallAssist(searchDTO);
        resultMap.put("stat", statDTO.toCalcStat());

        return resultMap;
    }


    /**
     * 신규/수정 페이지 보기
     *
     * @param model
     * @param commandMap
     * @param viewType
     * @return
     * @throws Exception
     */
    public String selectCallAssistView(ModelMap model, CommandMap commandMap, String viewType) throws Exception {

        storeProjNm(model);
        storeCompanies(model);
        storeUserNm(model);

        String no = commandMap.getParam("no");

        // 신규등록 페이지 보기
        if (StringUtils.isEmpty(no)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            LocalDateTime datetime = LocalDateTime.now();
            String regDate = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Map view = MapUtil.newMap();
            view.put("reqDate", regDate);
            model.addAttribute("view", view);
            return "board/call_assist_view";
        }

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearchNo(Long.parseLong(no));
        CustomerReqDTO customerReqDTO = customerReqDao.selectTblCallAssist(searchDTO).fetchFirst();
        model.addAttribute("view", customerReqDTO);

        if (StringUtils.equals(viewType, "view"))
            return "board/call_assist_view";

        return "board/call_assist_inner";
    }

    // 추가,수정,삭제 처리
    @Transactional
    public String upsertCallAssistView(ModelMap model, CommandMap commandMap) throws Exception {
        String no = commandMap.getParam("no");
        String iuFlag = commandMap.getParam("iuFlag");

        if (StringUtils.equals(iuFlag, "U")) { // 수정 처리시
            // --------------------------------------------------------
            // 완료여부만 변경하고, 처리일자를 입력하지 않는 사례 보완.
            // --------------------------------------------------------
            String _resDate = commandMap.getParam("resDate");
            String _doneSts = commandMap.getParam("doneSts");
            if (StringUtils.isEmpty(_resDate)
                    && !StringUtils.equals(_doneSts, "접수")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String resDate = sdf.format(LocalDateTime.now());
                commandMap.put("resDate", resDate);
            }
        }

        CustomerReqEntity customerReqEntity = customerReqDao.toCustomerReqEntity(commandMap);

        if (StringUtils.equals(iuFlag, "I") || StringUtils.equals(iuFlag, "U")) {
            customerReqEntity = customerReqDao.save(customerReqEntity);
            no = String.valueOf(customerReqEntity.getNo());
        }

        if (StringUtils.equals(iuFlag, "D")) {
            customerReqDao.delete(customerReqEntity);
        }

        String url = "redirect:/board/call_assist_view.do";
        if (!StringUtils.isEmpty(no)) url += "?no=" + no;
        if (StringUtils.equals(iuFlag, "D")) url = "redirect:/board/call_assist.do";

        return url;
    }

    private final ExcelFactory excelFactory;

	// 엑셀 다운로드
	public String excelDownload(HttpServletRequest request, HttpServletResponse response
            , ModelMap model, CommandMap commandMap) throws Exception {

        String projNm = sessionUtil.getUserProjectNm();

        SearchDTO searchDTO = getSearchDTO(commandMap);

        List<CustomerReqDTO> list = customerReqDao.selectTblCallAssist(searchDTO).fetch();

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

	    String downloadFileName = projNm + "-" + today +".xlsx";

		List<String> headerNames = new ArrayList<String>();
        headerNames.add("번호");
        headerNames.add("분류명");
        headerNames.add("요청회사");
        headerNames.add("요청자명");
        headerNames.add("요청자연락처");
        headerNames.add("진행상태");
        headerNames.add("등록자명");
        headerNames.add("요청내용");
        headerNames.add("응답내용");
        headerNames.add("접수일자");
        headerNames.add("처리일자");

		List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("no");
        fieldNames.add("kindNm");
        fieldNames.add("reqCompanyNm");
        fieldNames.add("reqUserNm");
        fieldNames.add("reqUserPhoneNo");
        fieldNames.add("progressNm");
        fieldNames.add("userNm");
        fieldNames.add("reqContent");
        fieldNames.add("resContent");
        fieldNames.add("reqDate");
        fieldNames.add("resDate");

        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        list.forEach(o -> {
            Map<String, Object> map = objectMapper.convertValue(o, new TypeReference<Map<String, Object>>() {});
            params.add(map);
        });

	    excelFactory.download(request, response, downloadFileName, headerNames, fieldNames, params);

		JSONObject res = new JSONObject();
		res.put("success", true);
		return  res.toString(2);
	}

    private SearchDTO getSearchDTO(CommandMap commandMap) {
        SearchDTO searchDTO = new SearchDTO();

        String searchNo = commandMap.getParam("searchNo");
        if (!StringUtils.isEmpty(searchNo))
            searchDTO.setSearchNo(Long.parseLong(searchNo));

        String searchWord = commandMap.getParam("searchWord");
        if (!StringUtils.isEmpty(searchWord))
            searchDTO.setSearchWord(searchWord);

        String dates = commandMap.getParam("dates");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDt = customerReqDao.findFirstReqDt();
        if (firstDt == null) firstDt = now;
        searchDTO.setStartDt(firstDt);
        searchDTO.setEndDt(now);
        if (!StringUtils.isEmpty(dates)) {
            String[] day = StringUtils.split(dates, "~");
            LocalDateTime startDt = DateUtil.toLocalDateTime(day[0], "yyyy-MM-dd");
            LocalDateTime endDt = DateUtil.toLocalDateTime(day[1], "yyyy-MM-dd");
            searchDTO.setStartDt(startDt);
            searchDTO.setEndDt(endDt);
        }
        return searchDTO;
    }

    /**
     * 현재 사용자가 선택한 ProjNm 을 페이지로 전달
     *
     * @param model
     */
    private void storeProjNm(ModelMap model) {
        String projNm = sessionUtil.getUserProjectNm();
        model.addAttribute("projNm", projNm);
    }

    /**
     * 상담자 정보 표시
     *
     * @param model
     */
    private void storeUserNm(ModelMap model) {
        model.addAttribute("userNm", sessionUtil.getUserNm());
    }

    /**
     * 프로젝트 관련 회사목록
     *
     * @param model
     * @return
     */
    private List<CompanyEntity> storeCompanies(ModelMap model) {
        String userId = sessionUtil.getUserId();

        Optional<UserEntity> userEntity = userDao.findByUserId(userId);
        if (!userEntity.isPresent()) Collections.emptyList();

        Long projNo = userEntity.get().getProjectEntity().getProjNo();
        List<CompanyEntity> companies = companyDao.findByProjNo(projNo);
        model.addAttribute("companies", companies);

        return companies;
    }

}
