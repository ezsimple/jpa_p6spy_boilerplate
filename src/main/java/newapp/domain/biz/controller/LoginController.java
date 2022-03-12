package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    /**
     * 주의) AuthController에서 /login, /refresh 처리를 합니다.
     * @param model
     * @param commandMap
     * @return
     */
    @GetMapping("/login.do")
    public String login(Model model, CommandMap commandMap) {
        return "login/login";
    }

    @GetMapping("/logout.do")
    public String logout(Model model, CommandMap commandMap) {
        return "login/logout";
    }

}
