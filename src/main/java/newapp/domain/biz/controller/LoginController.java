package newapp.domain.biz.controller;

import io.mkeasy.resolver.CommandMap;
import io.mkeasy.utils.NetUtil;
import lombok.extern.slf4j.Slf4j;
import newapp.global.oauth2.type.RoleType;
import org.apache.commons.lang.StringUtils;
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
        String ip = NetUtil.getClientIP();
        if(StringUtils.equals(ip, "127.0.0.1")) {
            model.addAttribute("username", "admin@mypms.io");
            model.addAttribute("password", "qwer1234");
        }
        return "login/login";
    }

    @GetMapping("/logout.do")
    public String logout(Model model, CommandMap commandMap) {
        return "login/logout";
    }

    @GetMapping("/deny.do")
    public String deny(Model model, CommandMap commandMap) {
        log.debug("{}, {}", RoleType.ADMIN.getCode(), RoleType.ADMIN.name());
        return "login/deny";
    }

}
