package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class SignController {

    @GetMapping("/sign_in.do")
    public String signIn(Model model, CommandMap commandMap) {
        return "sign/sign_in";
    }

    @PostMapping("/sign_ok.do")
    public String signOk(Model model, CommandMap commandMap) {
        return "sign/sign_in";
    }

    @GetMapping("/sign_out.do")
    public String signOut(Model model, CommandMap commandMap) {
        return "sign/sign_out";
    }


}
