package newapp.global.common.interceptor;

import io.mkeasy.utils.NetUtil;
import lombok.extern.slf4j.Slf4j;
import newapp.global.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter
{
    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    PropertiesUtil propertiesUtil;

    @Autowired
    private Environment env;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {

        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("X-Frame-Option", "DENY");

        HttpServletRequest req = ((HttpServletRequest) request);
        HttpSession session = req.getSession();

        long _ms = System.currentTimeMillis();
        LocalDateTime _now = LocalDateTime.now();
        String _key = propertiesUtil.getProperty("download.key");

        session.setAttribute("_key", _key);
        session.setAttribute("_dateFormat", "yyyy-MM-dd");
        session.setAttribute("_now", _now.toDate());
        session.setAttribute("_date", dateFormater.format(new Date()));
        session.setAttribute("_year", _now.getYear());
        session.setAttribute("_ms", _ms);

        return isIpAllowed();
    }

    private String getProfile() {
        String[] profiles = env.getActiveProfiles();

        if(profiles.length == 0)
            profiles = env.getDefaultProfiles();

        // log.debug("profiles => {}", profiles[0]);

        return profiles[0];
    }

    private boolean isIpAllowed() {
        String clientIp = NetUtil.getClientIP();
        String env = getProfile();

        if (StringUtils.equals(env, "local"))
            return true;

        // 사무실에서만 접근 가능
        if (!(StringUtils.startsWith(clientIp, "192.168.3")     	// 3층
                || StringUtils.startsWith(clientIp, "192.168.4")   	// 4층
                || StringUtils.startsWith(clientIp, "210.92.91.133")   	// 회사 공인 IP
                )) {
            log.warn("clientIp : {} is rejected", clientIp);
            return false;
        }
        return true;
    }

}
