package soccerfriend.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

@RequiredArgsConstructor
@Aspect
@Component
public class MemberAuthAspect {
    private final LoginService loginService;
    private final MemberService memberService;

    /**
     * member의 로그인 여부를 확인합니다.
     */
    @Before("@annotation(soccerfriend.aop.MemberLoginCheck) && execution(* soccerfriend.controller..*.*(..))")
    public void memberAuthentication() {
        int memberId = loginService.getMemberId();
    }
}