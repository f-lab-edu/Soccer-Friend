package soccerfriend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import soccerfriend.dto.Bulletin;
import soccerfriend.dto.Post;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.BulletinService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.LoginService;
import soccerfriend.service.PostService;

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
    private final RedisTemplate redisTemplate;
    private final PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MemberLoginCheck memberLoginCheck = ((HandlerMethod) handler).getMethodAnnotation(MemberLoginCheck.class);
        BulletinChangeable bulletinChangeable = ((HandlerMethod) handler).getMethodAnnotation(BulletinChangeable.class);
        BulletinReadable bulletinReadable = ((HandlerMethod) handler).getMethodAnnotation(BulletinReadable.class);
        IsClubLeaderOrManager isClubLeaderOrManager = ((HandlerMethod) handler).getMethodAnnotation(IsClubLeaderOrManager.class);
        BulletinWriteAuth bulletinWriteAuth = ((HandlerMethod) handler).getMethodAnnotation(BulletinWriteAuth.class);
        NotRecentlyPost notRecentlyPost = ((HandlerMethod) handler).getMethodAnnotation(NotRecentlyPost.class);
        PostWriteAuth postWriteAuth = ((HandlerMethod) handler).getMethodAnnotation(PostWriteAuth.class);

        Map<String, String> pathVariables =
                (Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (memberLoginCheck != null) {
            loginService.getMemberId();
        }

        if (bulletinReadable != null) {
            Integer bulletinId = Integer.parseInt(pathVariables.get("id"));
            if (bulletinId == null) {
                log.warn("PathVaribale에 id가 없습니다.");
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
                log.warn("PathVaribale에 id가 없습니다.");
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
                log.warn("PathVaribale에 clubId가 없습니다.");
                throw new BadRequestException(CLUB_NOT_EXIST);
            }

            int memberId = loginService.getMemberId();
            if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (bulletinWriteAuth != null) {
            String bulletinVariable = pathVariables.get("bulletinId");
            if (bulletinVariable == null) {
                log.warn("PathVaribale에 bulletinId가 없습니다.");
                throw new BadRequestException(BULLETIN_NOT_EXIST);
            }

            int memberId = loginService.getMemberId();
            int bulletinId = Integer.parseInt(bulletinVariable);
            Bulletin bulletin = bulletinService.getBulletinById(bulletinId);
            int clubId = bulletin.getClubId();

            if (!clubMemberService.isClubMember(clubId, memberId)) {
                throw new NoPermissionException(NO_CLUB_PERMISSION);
            }
        }

        if (notRecentlyPost != null) {
            int memberId = loginService.getMemberId();
            String value = (String) redisTemplate.opsForValue().get(RECENTLY_POST + " " + memberId);
            if (value != null) {
                throw new NoPermissionException(RECENTLY_CREATE_POST);
            }
        }

        if (postWriteAuth != null) {
            int memberId = loginService.getMemberId();
            Integer postId = Integer.parseInt(pathVariables.get("postId"));
            if (postId == null) {
                log.warn("PathVaribale에 postId가 없습니다.");
                throw new BadRequestException(POST_NOT_EXIST);
            }

            Post post = postService.getPostById(postId);
            if (memberId != post.getWriter()) {
                throw new NoPermissionException(NO_POST_PERMISSION);
            }
        }

        return true;
    }
}
