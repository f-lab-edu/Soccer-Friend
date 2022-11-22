package soccerfriend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import soccerfriend.dto.Bulletin;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.BulletinService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.LoginService;
import soccerfriend.utility.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static soccerfriend.exception.ExceptionInfo.*;
import static soccerfriend.service.PostService.RECENTLY_POST;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginService loginService;
    private final ClubMemberService clubMemberService;
    private final BulletinService bulletinService;
    private final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MemberLoginCheck memberLoginCheck = ((HandlerMethod) handler).getMethodAnnotation(MemberLoginCheck.class);
        IsClubLeaderOrManager isClubLeaderOrManager = ((HandlerMethod) handler).getMethodAnnotation(IsClubLeaderOrManager.class);
        IsClubMember isClubMember = ((HandlerMethod) handler).getMethodAnnotation(IsClubMember.class);
        BulletinWriteAuth bulletinWriteAuth = ((HandlerMethod) handler).getMethodAnnotation(BulletinWriteAuth.class);
        NotRecentlyPost notRecentlyPost = ((HandlerMethod) handler).getMethodAnnotation(NotRecentlyPost.class);

        if (memberLoginCheck != null) {
            loginService.getMemberId();
        }

        if (isClubMember != null) {
            Map<String, String> pathVariables =
                    (Map<String, String>) request
                            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            int memberId = loginService.getMemberId();
            String clubIdVariable = pathVariables.get("clubId");
            if (clubIdVariable == null) {
                log.warn("PathVaribale에 clubId가 없습니다.");
                throw new BadRequestException(CLUB_NOT_EXIST);
            }
            int clubId = Integer.parseInt(clubIdVariable);

            if (!clubMemberService.isClubMember(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (isClubLeaderOrManager != null) {
            Map<String, String> pathVariables =
                    (Map<String, String>) request
                            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            int memberId = loginService.getMemberId();
            String clubIdVariable = pathVariables.get("clubId");
            if (clubIdVariable == null) {
                log.warn("PathVaribale에 clubId가 없습니다.");
                throw new BadRequestException(CLUB_NOT_EXIST);
            }
            int clubId = Integer.parseInt(clubIdVariable);

            if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (bulletinWriteAuth != null) {
            Map<String, String> pathVariables =
                    (Map<String, String>) request
                            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            int memberId = loginService.getMemberId();
            String bulletinVariable = pathVariables.get("bulletinId");
            if (bulletinVariable == null) {
                log.warn("PathVaribale에 bulletinId가 없습니다.");
                throw new BadRequestException(BULLETIN_NOT_EXIST);
            }

            int bulletinId = Integer.parseInt(bulletinVariable);
            Bulletin bulletin = bulletinService.getBulletinById(bulletinId);
            int clubId = bulletin.getClubId();

            if (!clubMemberService.isClubMember(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (notRecentlyPost != null) {
            int memberId = loginService.getMemberId();
            String value = redisUtil.getStringData(RECENTLY_POST + " " + memberId);
            if (value != null) {
                throw new NoPermissionException(RECENTLY_CREATE_POST);
            }
        }

        return true;
    }
}
