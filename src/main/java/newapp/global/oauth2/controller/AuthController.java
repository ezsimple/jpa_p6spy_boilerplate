package newapp.global.oauth2.controller;

import io.jsonwebtoken.Claims;
import io.mkeasy.resolver.CommandMap;
import lombok.RequiredArgsConstructor;
import newapp.domain.entity.UserRefreshTokenEntity;
import newapp.domain.repository.UserRefreshTokenRepository;
import newapp.global.oauth2.properties.AppProperties;
import newapp.global.oauth2.response.ApiResponse;
import newapp.global.oauth2.service.UserPrincipal;
import newapp.global.oauth2.token.AuthToken;
import newapp.global.oauth2.token.AuthTokenProvider;
import newapp.global.oauth2.type.RoleType;
import newapp.global.util.CookieUtil;
import newapp.global.util.HeaderUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) {

        String userId = commandMap.getParam("username");
        String password = commandMap.getParam("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token ?????? DB ??????
        UserRefreshTokenEntity userRefreshTokenEntity = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshTokenEntity == null) {
            // ?????? ?????? ?????? ??????
            userRefreshTokenEntity = new UserRefreshTokenEntity(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshTokenEntity);
        } else {
            // DB??? refresh ?????? ????????????
            userRefreshTokenEntity.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // access token ??????
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token ?????? ??????
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token ?????? DB ??????
        UserRefreshTokenEntity userRefreshTokenEntity = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshTokenEntity == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh ?????? ????????? 3??? ????????? ?????? ??????, refresh ?????? ??????
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh ?????? ??????
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB??? refresh ?????? ????????????
            userRefreshTokenEntity.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }
}
