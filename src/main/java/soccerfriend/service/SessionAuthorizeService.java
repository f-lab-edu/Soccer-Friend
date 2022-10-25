package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Member;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.exception.exception.NotExistException;
import soccerfriend.exception.exception.NotMatchException;
import soccerfriend.utility.InputForm.LoginRequest;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static soccerfriend.exception.ExceptionCode.LOGIN_FORM_INCORRECT;
import static soccerfriend.exception.ExceptionCode.LOGIN_INFO_NOT_EXIST;
import static soccerfriend.utility.SessionKey.SESSION_LOGIN_MEMBER;
import static soccerfriend.utility.SessionKey.SESSION_LOGIN_STADIUM_OWNER;

@RequiredArgsConstructor
@Service
public class SessionAuthorizeService implements AuthorizeService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final StadiumOwnerService stadiumOwnerService;

    @Override
    public String getMemberId() {
        String id = (String) httpSession.getAttribute(SESSION_LOGIN_MEMBER);
        if (id == null) {
            throw new NotExistException(LOGIN_INFO_NOT_EXIST);
        }
        return id;
    }

    @Override
    public String getStadiumOwnerId() {
        String id = (String) httpSession.getAttribute(SESSION_LOGIN_STADIUM_OWNER);
        if (id == null) {
            throw new NotExistException(LOGIN_INFO_NOT_EXIST);
        }
        return id;
    }

    /**
     * Member의 로그인을 수행합니다.
     *
     * @param loginRequest id, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void memberLogin(LoginRequest loginRequest) {
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginRequest.getId(), loginRequest.getPassword());

        if (!member.isPresent()) {
            throw new NotMatchException(LOGIN_FORM_INCORRECT);
        }

        httpSession.setAttribute(SESSION_LOGIN_MEMBER, loginRequest.getId());
        httpSession.setMaxInactiveInterval(30 * 60);
    }

    /**
     * StadiumOwner의 로그인을 수행합니다.
     *
     * @param loginRequest id, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void stadiumOwnerLogin(LoginRequest loginRequest) {
        Optional<StadiumOwner> stadiumOwner = stadiumOwnerService.getStadiumOwnerByStadiumOwnerIdAndPassword(loginRequest.getId(), loginRequest.getPassword());

        if (!stadiumOwner.isPresent()) {
            throw new NotMatchException(LOGIN_FORM_INCORRECT);
        }

        httpSession.setAttribute(SESSION_LOGIN_STADIUM_OWNER, loginRequest.getId());
        httpSession.setMaxInactiveInterval(30 * 60);
    }

    /**
     * 로그아웃을 수행합니다.
     */
    @Override
    public void logout() {
        httpSession.invalidate();
    }
}
