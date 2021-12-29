package newapp.global.core;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 스프링 컨텍스트 환경변수 조회
 * @Author : mhlee
 */
@Component
@RequiredArgsConstructor
public class SpringContext {

    private final Environment environment;

    public Environment getEnvironment() {
        if(environment == null)
            throw new RuntimeException("환경설정이 되지 않았습니다.");
        return environment;
    }

    public boolean isLocal() {
        return isActiveProfile("local");
    }

    public boolean isDev() {
        return isActiveProfile("dev");
    }

    public boolean isProd() {
        return isActiveProfile("prod");
    }

    private boolean isActiveProfile(String profileName) {
        if (Arrays.stream(getEnvironment().getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase(profileName)))
            return true;
        return false;
    }

}
