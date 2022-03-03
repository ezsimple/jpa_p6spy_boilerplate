package newapp.domain.biz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import newapp.global.common.service.AbstractService;
// import newapp.global.common.service.ExcelService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelperService extends AbstractService {
	
	// private final ExcelService excelService;

//	// 월요일 육종가
//	public String excel1(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		log.debug("{}", result);
//
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//
//	// 매달 시세
//	public String excel2(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//
//	// 농장데이터 이관
//	public String excel3(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//
//	// 다비육종 생시체중
//	public String excel4(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//
//	// 검정자료 IMF값
//	public String excel5(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//	// 도드람농가 업데이트
//	public String excel6(MultipartFile excelfile, ModelMap model, CommandMap commandMap) throws Exception {
//		List<Map<String, String>> result = excelService.upload(excelfile, model, commandMap);
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}
//
//	// 자돈등기 파일출력
//	public String excel7(HttpServletRequest request, HttpServletResponse response, ModelMap model, CommandMap commandMap) throws Exception {
//
//		String ns = "oldegg.excel";
//	    String nsId="selectSuJadonEntity";
//
//	    Object result = QueryFactory.executeTx(ns, nsId, commandMap.getQueryMap());
//	    result = QueryFactory.getResult(ns, nsId, result);
//	    List<Map<String, Object>> list = QueryFactory.toList(result);
//
//	    String dnFileName = "자돈등기 파일.xlsx";
//
//		List<String> headerNames = new ArrayList<String>();
//		headerNames.add("농장번호");
//		headerNames.add("자돈번호");
//
//		List<String> fieldNames = new ArrayList<String>();
//		fieldNames.add("farmNo");
//		fieldNames.add("jadonPigId");
//
//	    excelService.download(request, response
//	    		, dnFileName, headerNames, fieldNames, list);
//
//		JSONObject res = new JSONObject();
//		res.put("success", true);
//		return  res.toString(2);
//	}

}
