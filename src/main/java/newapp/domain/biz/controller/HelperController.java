package newapp.domain.biz.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import newapp.domain.biz.service.HelperService;
import newapp.global.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.mkeasy.resolver.CommandMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
class HelperController {
	
	@Resource 
	ApplicationContext ctx;
	
	private final PropertiesUtil propertiesUtil;
	private final HelperService helperService;

	final String VIEW_YUKJONGGA = "excel/yukjongga";
	final String VIEW_SISAE = "excel/sisae";
	final String VIEW_FARM_MIGRATION ="excel/farm_migration";
	final String VIEW_DABI_SEANGSI_CHEJUNG = "excel/dabi_seangsi_chejung";
	final String VIEW_JUMJUNGZARYU_IMF = "excel/jumjungzaryu_imf";
	final String VIEW_DODRAM_UPDATE = "excel/dodram_update";
	final String VIEW_JADONDUNGGI_PRINT = "excel/jadondunggi_print";

	// 월요일 육종가 업로드
	@RequestMapping(value= {"/excel/yukjongga.do"}, method=RequestMethod.POST)
	public String excel1(@RequestParam(value="file") MultipartFile file
			, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel1(file, model, commandMap);
		return VIEW_YUKJONGGA;
	} 
	// 월요일 육종가
	@RequestMapping(value= {"/excel/yukjongga.do"}, method=RequestMethod.GET)
	public String excel1(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_YUKJONGGA;
	} 
	

	// 매달 시세 업로드
	@RequestMapping(value= {"/excel/sisae.do"}, method=RequestMethod.POST)
	public String excel2(@RequestParam(value="file") MultipartFile file, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel2(file, model, commandMap);
		return VIEW_SISAE;
	} 
	// 매달 시세
	@RequestMapping(value= {"/excel/sisae.do"}, method=RequestMethod.GET)
	public String excel2(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_SISAE;
	}
	


	// 농장데이터 이관 업로드
	@RequestMapping(value= {"/excel/farm_migration.do"}, method=RequestMethod.POST)
	public String excel3(@RequestParam(value="file") MultipartFile file, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel3(file, model, commandMap);
		return VIEW_FARM_MIGRATION;
	} 
	// 농장데이터 이관
	@RequestMapping(value= {"/excel/farm_migration.do"}, method=RequestMethod.GET)
	public String excel3(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_FARM_MIGRATION;
	}
	


	// 다비육종 생시체중 업로드
	@RequestMapping(value= {"/excel/dabi_seangsi_chejung.do"}, method=RequestMethod.POST)
	public String excel4(@RequestParam(value="file") MultipartFile file, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel4(file, model, commandMap);
		return VIEW_DABI_SEANGSI_CHEJUNG;
	} 
	// 다비육종 생시체중
	@RequestMapping(value= {"/excel/dabi_seangsi_chejung.do"}, method=RequestMethod.GET)
	public String excel4(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_DABI_SEANGSI_CHEJUNG;
	}
	


	// 검정자료 IMF값 업로드
	@RequestMapping(value= {"/excel/jumjungzaryu_imf.do"}, method=RequestMethod.POST)
	public String excel5(@RequestParam(value="file") MultipartFile file, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel5(file, model, commandMap);
		return VIEW_JUMJUNGZARYU_IMF;
	} 
	// 검정자료 IMF값
	@RequestMapping(value= {"/excel/jumjungzaryu_imf.do"})
	public String excel5(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_JUMJUNGZARYU_IMF;
	}
	


	// 도드람농가 업데이트 업로드
	@RequestMapping(value= {"/excel/dodram_update.do"}, method=RequestMethod.POST)
	public String excel6(@RequestParam(value="file") MultipartFile file, ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		helperService.excel6(file, model, commandMap);
		return VIEW_DODRAM_UPDATE;
	} 
	// 도드람농가 업데이트
	@RequestMapping(value= {"/excel/dodram_update.do"}, method=RequestMethod.GET)
	public String excel6(ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_DODRAM_UPDATE;
	}
	
	

	// 자돈등기 파일출력
	@ResponseBody
	@RequestMapping(value= {"/excel/jadondunggi_print.do"}, method=RequestMethod.POST)
	public String excel7(HttpServletRequest request, HttpServletResponse response
			,ModelMap model, CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return helperService.excel7(request, response, model, commandMap);
	}
	@RequestMapping(value= {"/excel/jadondunggi_print.do"}, method=RequestMethod.GET)
	public String excel7(CommandMap commandMap) throws Exception {
		commandMap.debugParams();
		return VIEW_JADONDUNGGI_PRINT;
	}


}
