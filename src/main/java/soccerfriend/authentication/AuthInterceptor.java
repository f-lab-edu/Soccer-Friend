package soccerfriend.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import soccerfriend.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MemberLoginCheck memberLoginCheck = ((HandlerMethod) handler).getMethodAnnotation(MemberLoginCheck.class);
        if (memberLoginCheck != null) {
            loginService.getMemberId();
        }

        return true;
    }
}
