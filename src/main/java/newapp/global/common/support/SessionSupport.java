package newapp.global.common.support;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SessionSupport {

    public static void setAttribute(String name, String value) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(name, value);
    }

    public static String getAttribute(String name, String defaultValue) {

        return SessionSupport.getAttribute(name).orElse(defaultValue);
    }

    public static Optional<String> getAttribute(String name) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Object value = request.getSession().getAttribute(name);
        if (value == null) {
            return Optional.empty();
        } else {
            return Optional.of(value.toString());
        }
    }
}
