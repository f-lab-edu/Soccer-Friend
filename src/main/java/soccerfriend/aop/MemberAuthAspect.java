package soccerfriend.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

import static soccerfriend.exception.ExceptionInfo.CHANGE_PASSWORD_REQUIRED;
import static soccerfriend.utility.PasswordWarning.NO_WARNING;

@RequiredArgsConstructor
@Aspect
@Component
public class MemberAuthAspect {
    private final LoginService loginService;
    private final MemberService memberService;

    /**
     * member의 로그인 여부를 확인합니다.
     * 로그인한 member의 비밀번호 상태에 이상이 있는지 확인합니다.
     */
    @Before("@annotation(soccerfriend.aop.MemberLoginCheck)")
    public void memberAuthentication() {
        int memberId = loginService.getMemberId();
        if (memberService.getPasswordWarning(memberId) != NO_WARNING.getCode()) {
            throw new NoPermissionException(CHANGE_PASSWORD_REQUIRED);
        }
    }
}