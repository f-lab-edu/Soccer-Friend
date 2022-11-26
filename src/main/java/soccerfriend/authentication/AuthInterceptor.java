package soccerfriend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static soccerfriend.exception.ExceptionInfo.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginService loginService;
    private final ClubMemberService clubMemberService;
    private final BulletinService bulletinService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MemberLoginCheck memberLoginCheck = ((HandlerMethod) handler).getMethodAnnotation(MemberLoginCheck.class);
        BulletinChangeable bulletinChangeable = ((HandlerMethod) handler).getMethodAnnotation(BulletinChangeable.class);
        BulletinReadable bulletinReadable = ((HandlerMethod) handler).getMethodAnnotation(BulletinReadable.class);
        IsClubLeaderOrManager isClubLeaderOrManager = ((HandlerMethod) handler).getMethodAnnotation(IsClubLeaderOrManager.class);
        Map<String, String> pathVariables =
                (Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (memberLoginCheck != null) {
            loginService.getMemberId();
        }

        if (bulletinReadable != null) {
            Integer bulletinId = Integer.parseInt(pathVariables.get("id"));
            if (bulletinId == null) {
                throw new BadRequestException(BULLETIN_NOT_EXIST);
            }

            int memberId = loginService.getMemberId();
            int clubId = bulletinService.getBulletinById(bulletinId).getClubId();
            if (!clubMemberService.isClubMember(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (bulletinChangeable != null) {
            Integer bulletinId = Integer.parseInt(pathVariables.get("id"));
            if (bulletinId == null) {
                throw new BadRequestException(BULLETIN_NOT_EXIST);
            }

            int memberId = loginService.getMemberId();
            int clubId = bulletinService.getBulletinById(bulletinId).getClubId();
            if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (isClubLeaderOrManager != null) {
            Integer clubId = Integer.valueOf(pathVariables.get("clubId"));
            if (clubId == null) {
                throw new BadRequestException(CLUB_NOT_EXIST);
            }

            int memberId = loginService.getMemberId();
            if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        return true;
    }
}
