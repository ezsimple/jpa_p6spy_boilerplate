package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.RequiredArgsConstructor;
import newapp.domain.biz.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HelloController {

  @GetMapping(value= {"/"})
  public String index(ModelMap model, CommandMap commandMap) throws Exception {
    commandMap.debugParams();
    return "index";
  }

  @GetMapping(value= {"/hello.do"})
  public String hello(ModelMap model, CommandMap commandMap) throws Exception {
    commandMap.debugParams();
    return "hello";
  }

}
