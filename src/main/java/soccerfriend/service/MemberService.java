package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NotMatchException;
import soccerfriend.mapper.MemberMapper;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;
import soccerfriend.utility.PasswordWarning;
import soccerfriend.utility.RedisUtil;

import java.util.Optional;

import static soccerfriend.exception.ExceptionInfo.*;
import static soccerfriend.utility.CodeGenerator.getEmailAuthorizationCode;
import static soccerfriend.utility.CodeGenerator.getPasswordRandomly;
import static soccerfriend.utility.PasswordWarning.PASSWORD_FIND;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;
    private final EmailService emailService;
    private final RedisUtil redisUtil;
    private final RedisTemplate redisTemplate;

    /**
     * 회원가입을 수행합니다.
     *
     * @param member memberId, password, nickname, positionsId, addressId를 포함하는 member 객체
     */
    public void signUp(Member member) {
        if (isMemberIdExist(member.getMemberId())) {
            throw new DuplicatedException(ID_DUPLICATED);
        }
        if (isNicknameExist(member.getNickname())) {
            throw new DuplicatedException(NICKNAME_DUPLICATED);
        }
        if (isEmailExist(member.getEmail())) {
            throw new DuplicatedException(EMAIL_DUPLICATED);
        }
        Member encryptedMember = Member.builder()
                                       .memberId(member.getMemberId())
                                       .password(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()))
                                       .email(member.getEmail())
                                       .nickname(member.getNickname())
                                       .positionsId(member.getPositionsId())
                                       .addressId(member.getAddressId())
                                       .point(0)
                                       .build();

        mapper.insert(encryptedMember);
    }

    /**
     * 회원정보를 삭제합니다.
     */
    public void deleteAccount(int id) {
        mapper.delete(id);
    }

    /**
     * 회원정보를 영구삭제합니다. (관리자용)
     */
    public void deletePermanently(int id) {
        mapper.deletePermanently(id);
    }

    /**
     * 며칠 이전의 삭제된 계정들을 영구삭제합니다.
     *
     * @param days 영구삭제하고자 하는 계정삭제의 최소 일수
     */
    public void deletePermanentlyDaysBefore(int days) {
        mapper.deletePermanentlyDaysBefore(days);
    }

    /**
     * 특정 id의 member를 반환합니다.
     *
     * @param id member의 id
     * @return 특정 id의 member 객체
     */
    public Member getMemberById(int id) {
        Member member = mapper.getMemberById(id);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        return member;
    }

    /**
     * 특정 memberId의 member를 반환합니다.
     *
     * @param memberId member의 memberId
     * @return 특정 memberId의 member 객체
     */
    public Member getMemberByMemberId(String memberId) {
        Member member = mapper.getMemberByMemberId(memberId);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        return member;
    }

    /**
     * 특정 email의 member를 반환합니다.
     *
     * @param email member의 email
     * @return 특정 email의 member 객체
     */
    public Member getMemberByEmail(String email) {
        Member member = mapper.getMemberByEmail(email);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        return member;
    }

    /**
     * 해당 loginId를 사용중인 member가 있는지 확인합니다.
     *
     * @param memberId 존재 유무를 확인하려는 memberId
     * @return memberId 존재 유무(true: 있음, false: 없음)
     */
    public boolean isMemberIdExist(String memberId) {
        return mapper.isMemberIdExist(memberId);
    }

    /**
     * 해당 nickname을 사용중인 member가 있는지 확인합니다.
     *
     * @param nickname 존재 유무를 확인하려는 nickname
     * @return nickname 존재 유무(true: 있음, false: 없음)
     */
    public boolean isNicknameExist(String nickname) {
        return mapper.isNicknameExist(nickname);
    }

    /**
     * 해당 email 사용중인 member가 있는지 확인합니다.
     *
     * @param email 존재 유무를 확인하려는 nickname
     * @return email 존재 유무(true: 있음, false: 없음)
     */
    public boolean isEmailExist(String email) {
        return mapper.isEmailExist(email);
    }

    /**
     * loginId와 password를 입력받아 해당 member를 반환합니다.
     *
     * @param memberId
     * @param password
     * @return member의 Optional 객체
     */
    public Optional<Member> getMemberByMemberIdAndPassword(String memberId, String password) {

        if (!isMemberIdExist(memberId)) return Optional.empty();

        Optional<Member> member =
                Optional.ofNullable(mapper.getMemberByMemberId(memberId));

        if (BCrypt.checkpw(password, member.get().getPassword())) {
            return member;
        }

        return Optional.empty();
    }

    /**
     * member의 nickname을 수정합니다.
     *
     * @param nickname 새로 수정하려는 nickname
     */
    public void updateNickname(int id, String nickname) {
        if (isNicknameExist(nickname)) {
            throw new DuplicatedException(NICKNAME_DUPLICATED);
        }
        mapper.updateNickname(id, nickname);
    }

    /**
     * member의 password를 PasswordRequest 의해 변경합니다.
     * PasswordRequest는 이전 비밀번호, 새로운 비밀번호를 포함합니다.
     *
     * @param id              member의 id
     * @param passwordRequest before(현재 password), after(새로운 password)를 가지는 객체
     */
    public void updatePasswordByRequest(int id, UpdatePasswordRequest passwordRequest) {
        String before = passwordRequest.getBefore();
        String after = passwordRequest.getAfter();
        String encryptedCurrent = getMemberById(id).getPassword();

        if (BCrypt.checkpw(after, encryptedCurrent)) {
            throw new NotMatchException(PASSWORD_SAME);
        }

        after = BCrypt.hashpw(after, BCrypt.gensalt());
        mapper.updatePassword(id, after);
    }

    /**
     * member의 비밀번호를 변경합니다.
     *
     * @param id       member의 id
     * @param password 새로운 비밀번호
     */
    public void updatePassword(int id, String password) {
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        mapper.updatePassword(id, encryptedPassword);
    }

    /**
     * member의 point를 증가시킵니다.
     *
     * @param id    member의 id
     * @param point 증가시키고자 하는 point의 양
     */
    public void increasePoint(int id, int point) {
        mapper.increasePoint(id, point);
    }

    /**
     * member의 point를 감소시킵니다.
     *
     * @param id    member의 id
     * @param point 감소시키고자 하는 point의 양
     */
    public void decreasePoint(int id, int point) {
        Member member = getMemberById(id);
        if (member.getPoint() < point) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }
        mapper.decreasePoint(id, point);
    }

    /**
     * email 인증과정 중 인증코드를 생성하고 코드를 이메일로 전달하는 과정을 수행합니다.
     *
     * @param email 인증하려는 email
     */
    public void emailAuthentication(String email) {
        String code = getEmailAuthorizationCode();
        redisTemplate.opsForValue().set(email, code, 5 * 60);
        emailService.sendAuthorizationCode(code, email);
    }

    /**
     * 해당 email을 인증코드로 인증합니다.
     *
     * @param email 인증하려는 email
     * @param code  인증코드
     * @return 인증여부
     */
    public boolean approveEmail(String email, String code) {
        String emailCode = redisUtil.getStringData(email);
        if (!code.equals(emailCode)) {
            return false;
        }

        redisUtil.deleteData(email);
        return true;
    }

    /**
     * 아이디 찾기과정 중 이메일 인증을 완료한 후 아이디를 이메일로 전송해줍니다.
     *
     * @param email 사용자의 email
     * @param code  email 인증번호
     * @return
     */
    public void sendMemberId(String email, String code) {
        if (!approveEmail(email, code)) {
            throw new BadRequestException(CODE_INCORRECT);
        }
        Member member = getMemberByEmail(email);

        emailService.sendMemberId(member.getMemberId(), email);
    }

    /**
     * member의 비밀번호 경고 내용을 확인합니다.
     *
     * @param id member의 id
     * @return PasswordWarning의 code
     */
    public int getPasswordWarning(int id) {
        Member member = getMemberById(id);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        return mapper.getPasswordWarning(id);
    }

    /**
     * member에게 비밀번호 경고 내용을 추가합니다.
     *
     * @param id              member의 id
     * @param passwordWarning 경고내용
     */
    public void setPasswordWarning(int id, PasswordWarning passwordWarning) {
        Member member = getMemberById(id);
        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        mapper.setPasswordWarning(id, passwordWarning.getCode());
    }

    /**
     * 이메일 인증을 확인한 후 임시 비밀번호를 이메일로 전송합니다.
     *
     * @param email 임시 비밀번호를 발급하려는 email
     * @param code  email 인증번호
     */
    public void sendTemporaryPassword(String email, String code) {
        if (!approveEmail(email, code)) {
            throw new BadRequestException(CODE_INCORRECT);
        }

        Member member = getMemberByEmail(email);
        int id = member.getId();

        String password = getPasswordRandomly();
        updatePassword(id, password);
        setPasswordWarning(id, PASSWORD_FIND);

        if (member == null) {
            throw new BadRequestException(MEMBER_NOT_EXIST);
        }

        emailService.sendTemporaryPassword(password, email);
    }
}
