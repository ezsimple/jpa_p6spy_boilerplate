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
     * Google OAuth2 redirect address
     * @param model
     * @param commandMap
     * @return
     */
    @GetMapping("/login/oauth2/code/google")
    public String google(Model model, CommandMap commandMap) {
        return "login/google";
    }

    /**
     * Facebook OAuth2 redirect address
     * @param model
     * @param commandMap
     * @return
     */
    @GetMapping("/login/oauth2/code/facebook")
    public String facebook(Model model, CommandMap commandMap) {
        return "login/facebook";
    }

    /**
     * Naver OAuth2 redirect address
     * @param model
     * @param commandMap
     * @return
     */
    @GetMapping("/login/oauth2/code/naver")
    public String naver(Model model, CommandMap commandMap) {
        return "login/naver";
    }

    /**
     * Kakao OAuth2 redirect address
     * email 정보가 민감정보라니 .... ㄷㄷ
     * @param model
     * @param commandMap
     * @return
     */
    @GetMapping("/login/oauth2/code/kakao")
    public String kakao(Model model, CommandMap commandMap) {
        return "login/kakao";
    }
}
