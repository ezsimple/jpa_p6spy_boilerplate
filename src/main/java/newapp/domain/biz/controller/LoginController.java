package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class LoginController {

    /**
     * provider OAuth2 redirect address
     * providers : google, facebook, naver, kakao
     * @param commandMap
     * @return
     */
    @GetMapping("/login/oauth2/code/{provider}")
    public String redirect(@PathVariable("provider") String provider, CommandMap commandMap) {
        return "login/"+provider;
    }

}
