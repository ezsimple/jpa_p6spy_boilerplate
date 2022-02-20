package newapp.global.common.service;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.webapp.processor.ExcelFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Service
public class ExcelService extends AbstractService {
	
    @Autowired
    ExcelFactory excelFactory;
    
    public Map<String, String> getHeader(String filePath, int headerRowNo) throws Exception {
    	return excelFactory.getHeader(filePath, headerRowNo);
    }

	public List<Map<String, String>> upload(String filePath, ModelMap model, CommandMap commandMap, int readRowNo) throws Exception {
		return excelFactory.upload(filePath, model, commandMap, readRowNo);
	}

	public void download(HttpServletRequest request, HttpServletResponse response
			,String dnFileName // 다운로드 엑셀파일명
			,List<String> headerNames // 엑셀파일 컬럼명
			,List<String> fieldNames  // 쿼리결과 필드명
			,List<Map<String, Object>> result) throws Exception {
		excelFactory.download(request, response, dnFileName, headerNames, fieldNames, result);
	}

    public void diff(Map<String, String> sampleHeader, Map<String, String> uploadHeader) throws Exception {
    	if(sampleHeader == null || uploadHeader == null)
    		throw new Exception("헤더값이 없습니다.");
    	
    	log.debug("sampleHeader : {}", sampleHeader);
    	log.debug("uploadHeader : {}", uploadHeader);
    	
    	// 공백문자 제거하고 헤더 비교하기
		for (Entry<String, String> sampleEntry : sampleHeader.entrySet()) {
			String key = sampleEntry.getKey();
			String value1 = StringUtils.trim(sampleEntry.getValue());
			String value2 = StringUtils.trim(uploadHeader.get(key));
			if(!StringUtils.equals(value1, value2))
				throw new Exception("지정된 형식의 엑셀이 아닙니다");
		}
		
		// 공백문자가 존재하는 경우 사용할 수 없음
		// if(!sampleHeader.equals(uploadHeader))
		//   throw new Exception("지정된 형식의 엑셀이 아닙니다");
    	
    }
	
}
