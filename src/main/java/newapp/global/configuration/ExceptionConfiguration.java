package newapp.global.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionConfiguration {
	
//	@ExceptionHandler(Exception.class)
//    protected ResponseEntity<String> handleException(Exception e) {
//        JSONObject message = new JSONObject();
//		message.put("error", e.getMessage());
//        return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

	@ExceptionHandler({Exception.class})
    protected ModelAndView handleException(HttpServletRequest request, Exception e) {
        String statusCode = "500";
        String errTitle = "웁스! 뭔가 잘못된것 같군요.";
        String errMessage = e.getMessage();
        if(StringUtils.containsIgnoreCase(errMessage, "xls")
        		|| StringUtils.contains(errMessage, "엑셀"))
			errTitle = "웁스! 엑셀파일에 오류가 있는것 같군요.";

        ModelAndView mv = new ModelAndView();
        mv.addObject("statusCode", statusCode);
        mv.addObject("errTitle", errTitle);
        mv.addObject("errMessage", errMessage);
        mv.setViewName("error/error");

        return mv;
    }

}
