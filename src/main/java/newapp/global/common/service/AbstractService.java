package newapp.global.common.service;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.QueryMap;
import io.mkeasy.utils.WebUtil;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.extern.slf4j.Slf4j;
import newapp.global.util.PropertiesUtil;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public abstract class AbstractService implements ServiceInf {
	
//	@Autowired
//    ExcelService excelService;
	
	@Autowired
    FileService fileService;

	@Autowired
	PropertiesUtil propertiesUtil;

	@Autowired
	WebUtil webUtil;

//	protected String checkValidExcelFormat(MultipartFile file, CommandMap commandMap) throws Exception {
//		String fid = commandMap.getParam("fid");
//    	String dir = propertiesUtil.getProperty("download.dir");
//    	if(StringUtils.isEmpty(dir))
//    		dir = webUtil.getResourceRoot()+"/static/dl";
//        String sampleFile = propertiesUtil.getProperty("download.file"+fid);
//        String sampleFilePath = dir + "/" + sampleFile;
//
//        // Hidden Parameter 처리
//        String headerRowNo = commandMap.getParam("headerRowNo");
//        if(StringUtils.isEmpty(headerRowNo)) headerRowNo = "1";
//        int readRowNo = Integer.parseInt(headerRowNo);
//        Map<String, String> sampleHeader = excelService.getHeader(sampleFilePath, readRowNo);
//
//		String uploadFilePath = fileService.upload(file);
//        Map<String, String> uploadHeader = excelService.getHeader(uploadFilePath, readRowNo);
//
//        excelService.diff(sampleHeader, uploadHeader);
//		return uploadFilePath;
//	}
//
//	protected List<Map<String, Object>> makeTotalList(List<Map<String, String>> result) {
//		List<Map<String, Object>> totalList = new ArrayList<Map<String, Object>>();
//        for(int i=0; i<result.size(); i++) {
//            Map<String, String> o = result.get(i);
//			String g1 = o.get("A");
//			String g2 = o.get("B");
//			String g3 = o.get("C");
//			String g4 = o.get("D");
//			String g5 = o.get("E");
//			String g6 = o.get("F");
//			String g7 = o.get("G");
//			String g8 = o.get("H");
//			String g9= o.get("I");
//			String g10 = o.get("J");
//			String g11 = o.get("K");
//			String g12 = o.get("L");
//			String g13 = o.get("M");
//			String g14 = o.get("N");
//			String g15 = o.get("O");
//			String g16 = o.get("P");
//			String g17 = o.get("Q");
//			String g18 = o.get("R");
//			String g19 = o.get("S");
//			String g20 = o.get("T");
//			String g21 = o.get("U");
//			String g22 = o.get("V");
//			String g23 = o.get("W");
//			String g24 = o.get("X");
//			String g25 = o.get("Y");
//			String g26 = o.get("Z");
//
//			String g27 = o.get("AA");
//			String g28 = o.get("AB");
//			String g29 = o.get("AC");
//			String g30 = o.get("AD");
//			String g31 = o.get("AE");
//			String g32 = o.get("AF");
//			String g33 = o.get("AG");
//			String g34 = o.get("AH");
//			String g35 = o.get("AI");
//			String g36 = o.get("AJ");
//			String g37 = o.get("AK");
//			String g38 = o.get("AL");
//			String g39 = o.get("AM");
//			String g40 = o.get("AN");
//			String g41 = o.get("AO");
//			String g42 = o.get("AP");
//			String g43 = o.get("AQ");
//			String g44 = o.get("AR");
//			String g45 = o.get("AS");
//			String g46 = o.get("AT");
//			String g47 = o.get("AU");
//			String g48 = o.get("AV");
//			String g49 = o.get("AW");
//			String g50 = o.get("AX");
//			String g51 = o.get("AY");
//			String g52 = o.get("AZ");
//
//			String g53 = o.get("BA");
//			String g54 = o.get("BB");
//			String g55 = o.get("BC");
//			String g56 = o.get("BD");
//			String g57 = o.get("BE");
//			String g58 = o.get("BF");
//			String g59 = o.get("BG");
//			String g60 = o.get("BH");
//			String g61 = o.get("BI");
//			String g62 = o.get("BJ");
//			String g63 = o.get("BK");
//			String g64 = o.get("BL");
//			String g65 = o.get("BM");
//			String g66 = o.get("BN");
//			String g67 = o.get("BO");
//			String g68 = o.get("BP");
//			String g69 = o.get("BQ");
//			String g70 = o.get("BR");
//			String g71 = o.get("BS");
//			String g72 = o.get("BT");
//			String g73 = o.get("BU");
//			String g74 = o.get("BV");
//			String g75 = o.get("BW");
//			String g76 = o.get("BX");
//			String g77 = o.get("BY");
//			String g78 = o.get("BZ");
//
//			String g79 = o.get("CA");
//			String g80 = o.get("CB");
//			String g81 = o.get("CC");
//			String g82 = o.get("CD");
//			String g83 = o.get("CE");
//			String g84 = o.get("CF");
//			String g85 = o.get("CG");
//			String g86 = o.get("CH");
//
//			QueryMap params = new QueryMap();
//			params.put("g1", g1);
//			params.put("g2", g2);
//			params.put("g3", g3);
//			params.put("g4", g4);
//			params.put("g5", g5);
//			params.put("g6", g6);
//			params.put("g7", g7);
//			params.put("g8", g8);
//			params.put("g9", g9);
//			params.put("g10", g10);
//			params.put("g11", g11);
//			params.put("g12", g12);
//			params.put("g13", g13);
//			params.put("g14", g14);
//			params.put("g15", g15);
//			params.put("g16", g16);
//			params.put("g17", g17);
//			params.put("g18", g18);
//			params.put("g19", g19);
//			params.put("g20", g20);
//			params.put("g21", g21);
//			params.put("g22", g22);
//			params.put("g23", g23);
//			params.put("g24", g24);
//			params.put("g25", g25);
//			params.put("g26", g26);
//			params.put("g27", g27);
//			params.put("g28", g28);
//			params.put("g29", g29);
//			params.put("g30", g30);
//			params.put("g31", g31);
//			params.put("g32", g32);
//			params.put("g33", g33);
//			params.put("g34", g34);
//			params.put("g35", g35);
//			params.put("g36", g36);
//			params.put("g37", g37);
//			params.put("g38", g38);
//			params.put("g39", g39);
//			params.put("g40", g40);
//			params.put("g41", g41);
//			params.put("g42", g42);
//			params.put("g43", g43);
//			params.put("g44", g44);
//			params.put("g45", g45);
//			params.put("g46", g46);
//			params.put("g47", g47);
//			params.put("g48", g48);
//			params.put("g49", g49);
//			params.put("g50", g50);
//			params.put("g51", g51);
//			params.put("g52", g52);
//			params.put("g53", g53);
//			params.put("g54", g54);
//			params.put("g55", g55);
//			params.put("g56", g56);
//			params.put("g57", g57);
//			params.put("g58", g58);
//			params.put("g59", g59);
//			params.put("g60", g60);
//			params.put("g61", g61);
//			params.put("g62", g62);
//			params.put("g63", g63);
//			params.put("g64", g64);
//			params.put("g65", g65);
//			params.put("g66", g66);
//			params.put("g67", g67);
//			params.put("g68", g68);
//			params.put("g69", g69);
//			params.put("g70", g70);
//			params.put("g71", g71);
//			params.put("g72", g72);
//			params.put("g73", g73);
//			params.put("g74", g74);
//			params.put("g75", g75);
//			params.put("g76", g76);
//			params.put("g77", g77);
//			params.put("g78", g78);
//			params.put("g79", g79);
//			params.put("g80", g80);
//			params.put("g81", g81);
//			params.put("g82", g82);
//			params.put("g83", g83);
//			params.put("g84", g84);
//			params.put("g85", g85);
//			params.put("g86", g86);
//
//			totalList.add(params);
//		}
//		return totalList;
//	}

	protected JSONObject getJSONObject(JSONObject innerObj, String key) {
		try {
            return (JSONObject) innerObj.get(key);
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	protected JSONArray getJSONArray(JSONObject innerObj, String key) {
		try {
			return (JSONArray) innerObj.get(key);
		} catch (Exception e) {
			return new JSONArray();
		}
	}

	protected String getValue(JSONObject tmpObj, String key) throws Exception {
		boolean has = tmpObj.has(key);
		String defaultValue = "-";
		String value = has ? String.valueOf(tmpObj.get(key)):defaultValue;
		
		if(StringUtils.equals(value, defaultValue))
			return value;
		
		if(StringUtils.equals(value, "null"))
			return defaultValue;

		return value;
	}
	
	protected String getLabel(JSONObject labelObj, String key) throws Exception {
		boolean has = labelObj.has(key);
		String value = has ? String.valueOf(labelObj.get(key)):key;
		return value;
		
	}
	
//	protected void insertTemp(List<Map<String, String>> result) throws Exception {
//		final String ns   = "oldegg.common";
//		String nsId = "truncateTemp";
//		QueryFactory.execute(ns, nsId, null);
//
//		List<Map<String, Object>> totalList = makeTotalList(result);
//
//        nsId = "insertTemp";
//		for(Map<String, Object> param : totalList) {
//			QueryFactory.execute(ns, nsId, new CaseInsensitiveMap(param));
//		}
//	}

}
