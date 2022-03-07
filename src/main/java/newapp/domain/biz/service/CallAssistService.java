package newapp.domain.biz.service;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.MapUtil;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.domain.dao.CustomerReqDao;
import newapp.domain.dto.CustomerReqDTO;
import newapp.domain.dto.SearchDTO;
import newapp.domain.dto.StatDTO;
import newapp.domain.entity.CustomerReqEntity;
import newapp.global.common.service.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallAssistService extends AbstractService {
	
	private final QueryFactory queryFactory;
	private final CustomerReqDao customerReqDao;

	/**
	 * 목록 조회 및 검색 하기
	 * @param model
	 * @param commandMap
	 * @return
	 */
	public Map searchCallAssist(ModelMap model, CommandMap commandMap) {

		SearchDTO searchDTO = new SearchDTO();

		String searchNo = commandMap.getParam("searchNo");
		if(!StringUtils.isEmpty(searchNo))
			searchDTO.setSearchNo(Long.parseLong(searchNo));

		String searchWord = commandMap.getParam("searchWord");
		if(!StringUtils.isEmpty(searchWord))
			searchDTO.setSearchWord(searchWord);

		String dates = commandMap.getParam("dates");
		if(!StringUtils.isEmpty(dates)) {
			String[] day = StringUtils.split(dates, "~");
			LocalDateTime startDt = LocalDateTime.parse(day[0]);
			LocalDateTime endDt = LocalDateTime.parse(day[1]);
			searchDTO.setStartDt(startDt);
			searchDTO.setEndDt(endDt);
		}

		Map map = MapUtil.newMap();

		List<CustomerReqDTO> list = customerReqDao.selectTblCallAssist(searchDTO).fetch();
		map.put("rows", list);

		StatDTO statDTO = customerReqDao.statTblCallAssist2(searchDTO);
		Map stat = MapUtil.newMap();
		stat.put("countTotal", statDTO.getCountTotal());
		stat.put("countSearch", statDTO.getCountSearch());
		stat.put("countDoneToday", statDTO.getCountDoneToday());
		stat.put("countReqToday", statDTO.getCountReqToday());
		stat.put("percentKind0", statDTO.getPercentKind0());
		stat.put("percentKind1", statDTO.getPercentKind1());
		stat.put("percentKind2", statDTO.getPercentKind2());
		stat.put("percentKind3", statDTO.getPercentKind3());
		stat.put("percentKind4", statDTO.getPercentKind4());
		map.put("stat", stat);

		return map;
	}

	/**
	 * 신규/수정 페이지 보기
	 * @param model
	 * @param commandMap
	 * @param viewType
	 * @return
	 * @throws Exception
	 */
	public String selectCallAssistView(ModelMap model, CommandMap commandMap, String viewType) throws Exception {
		String no = commandMap.getParam("no");

		// 신규등록 페이지 보기
		if(StringUtils.isEmpty(no)) {
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

		if(StringUtils.equals(viewType, "view"))
			return "board/call_assist_view";

		return "board/call_assist_inner";
	}

	// 추가,수정,삭제 처리
	public String upsertCallAssistView(ModelMap model, CommandMap commandMap) throws Exception {
		String no = commandMap.getParam("no");
		String iuFlag = commandMap.getParam("iuFlag");

		String ns = "oldegg.board";
		String nsId = "insertTblCallAssist";

		if(StringUtils.equals(iuFlag, "U")) { // 수정 처리시
			nsId = "updateTblCallAssist";

			// --------------------------------------------------------
			// 완료여부만 변경하고, 처리일자를 입력하지 않는 사례 보완.
			// --------------------------------------------------------
			String _resDate = commandMap.getParam("resDate");
			String _doneSts = commandMap.getParam("doneSts");
			if(StringUtils.isEmpty(_resDate)
					&& !StringUtils.equals(_doneSts, "접수")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String resDate = sdf.format(LocalDateTime.now());
				commandMap.put("resDate", resDate);
			}
		}

		if(StringUtils.equals(iuFlag, "D")) nsId = "deleteTblCallAssist";

		queryFactory.executeTx(ns, nsId, commandMap.getQueryMap());

		String url = "redirect:/board/call_assist_view.do";
		if(!StringUtils.isEmpty(no)) url+="?no="+no;

		if(StringUtils.equals(iuFlag, "D")) url = "redirect:/board/call_assist.do";

		return url;
	}

}
