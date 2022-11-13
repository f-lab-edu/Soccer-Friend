package soccerfriend.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

import static soccerfriend.exception.ExceptionInfo.NOT_LOGIN;

@RequiredArgsConstructor
@Aspect
@Component
public class MemberAuthAspect {
    private final LoginService loginService;
    private final MemberService memberService;

    @Around("@annotation(soccerfriend.aop.MemberAuth)")
    public void memberAuthentication(ProceedingJoinPoint joinPoint) {
        int memberId = loginService.getMemberId();
        if(memberService.isPasswordWarning(memberId)){
            throw new NoPermissionException(NOT_LOGIN);
        }
    }
}