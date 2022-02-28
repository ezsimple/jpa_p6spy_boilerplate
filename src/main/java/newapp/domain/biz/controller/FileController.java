package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newapp.global.common.service.FileService;
import newapp.global.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
class FileController {
	
	@Resource 
	ApplicationContext ctx;
	
	private final PropertiesUtil propertiesUtil;
	private final FileService fileService;

	@RequestMapping(value= {"/dl.do"})
	public void download(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception {
		fileService.download(request, response, commandMap);
	}

}
