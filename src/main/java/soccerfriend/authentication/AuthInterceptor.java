package soccerfriend.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.BulletinService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static soccerfriend.exception.ExceptionInfo.CLUB_NOT_EXIST;
import static soccerfriend.exception.ExceptionInfo.NO_CLUB_PERMISSION;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginService loginService;
    private final ClubMemberService clubMemberService;
    private final BulletinService bulletinService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MemberLoginCheck memberLoginCheck = ((HandlerMethod) handler).getMethodAnnotation(MemberLoginCheck.class);
        IsClubLeaderOrManager isClubLeaderOrManager = ((HandlerMethod) handler).getMethodAnnotation(IsClubLeaderOrManager.class);
        IsClubMember isClubMember = ((HandlerMethod) handler).getMethodAnnotation(IsClubMember.class);

        if (memberLoginCheck != null) {
            loginService.getMemberId();
        }

        if (isClubMember != null) {
            Map<String, String> pathVariables =
                    (Map<String, String>) request
                            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            int memberId = loginService.getMemberId();
            Integer clubId = Integer.parseInt(pathVariables.get("clubId"));
            if (clubId == null) {
                throw new BadRequestException(CLUB_NOT_EXIST);
            }

            if (!clubMemberService.isClubMember(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (isClubLeaderOrManager != null) {
            Map<String, String> pathVariables =
                    (Map<String, String>) request
                            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            int memberId = loginService.getMemberId();
            Integer clubId = Integer.parseInt(pathVariables.get("clubId"));
            if (clubId == null) {
                throw new BadRequestException(CLUB_NOT_EXIST);
            }

            if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        return true;
    }
}
