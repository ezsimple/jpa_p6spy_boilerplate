package newapp.domain.biz.service;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.MapUtil;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.global.common.service.AbstractService;
import newapp.global.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallAssistService extends AbstractService {
	
	@Resource
	ApplicationContext ctx;

	private final PropertiesUtil propertiesUtil;
	private final QueryFactory queryFactory;

	// 조회 및 검색 하기
	public Map searchCallAssist(ModelMap model, CommandMap commandMap) throws Exception {
		String ns = "oldegg.board";
		String nsId = "selectTblCallAssist";

		// 날짜검색을 요청한 경우
		String searchWord = commandMap.getParam("searchWord");
		String dates = commandMap.getParam("dates");
		if(!StringUtils.isEmpty(dates)) {
			String[] day = StringUtils.split(dates, "~");
			commandMap.put("startDt", day[0]);
			commandMap.put("endDt", day[1]);
		}

		// 검색 요청인 경우 쿼리가 search 쿼리를 사용
		if(!StringUtils.isEmpty(searchWord) || !StringUtils.isEmpty(dates)) {
			nsId = "searchTblCallAssist";
		}

		Map map = MapUtil.newMap();
		Object result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
		result = queryFactory.getResult(ns, nsId, result);
		List list = queryFactory.toList(result);
		map.put("rows", list);

		nsId = "statTblCallAssist";
		result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
		result = queryFactory.getResult(ns, nsId, result);
		list = queryFactory.toList(result);
		map.put("stat", list);

		return map;
	}

	public String selectCallAssistView(ModelMap model, CommandMap commandMap, String viewType) throws Exception {
		String ns = "oldegg.board";
		String nsId = "selectTblCallAssist";
		String no = commandMap.getParam("no");

		// 신규등록 페이지 보기
		if(StringUtils.isEmpty(no)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDateTime datetime = LocalDateTime.now();
			String formated_date = sdf.format(datetime.toDate());
			Map view = MapUtil.newMap();
			view.put("reqDate", formated_date);
			model.addAttribute("view", view);
			return "board/call_assist_view";
		}

		// 수정 페이지 보기
		Object result = queryFactory.execute(ns, nsId, commandMap.getQueryMap());
		result = queryFactory.getResult(ns, nsId, result);
		List list = queryFactory.toList(result);
		if(list!=null && list.size()>0)
			model.addAttribute("view", list.get(0));
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
			String resDate = commandMap.getParam("resDate");
			String doneSts = commandMap.getParam("doneSts");
			if(StringUtils.isEmpty(resDate)
					&& !StringUtils.equals(doneSts, "접수")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				LocalDateTime datetime = LocalDateTime.now();
				String formated_date = sdf.format(datetime.toDate());
				commandMap.put("resDate", formated_date);
			}
		}
		if(StringUtils.equals(iuFlag, "D")) nsId = "deleteTblCallAssist";
		Object result = queryFactory.executeTx(ns, nsId, commandMap.getQueryMap());

		String url = "redirect:/board/call_assist_view.do";
		if(!StringUtils.isEmpty(no)) url+="?no="+no;
		if(StringUtils.equals(iuFlag, "D")) url = "redirect:/board/call_assist.do";

		return url;
	}

}
