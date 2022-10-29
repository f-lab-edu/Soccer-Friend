package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NotMatchException;
import soccerfriend.mapper.MemberMapper;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import java.util.Optional;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;

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
        Member encryptedMember = Member.builder()
                                       .memberId(member.getMemberId())
                                       .password(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()))
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
     * 특정 id의 member를 반환합니다.
     *
     * @param id member의 id
     * @return 특정 id의 member 객체
     */
    public Member getMemberById(int id) {
        Member member = mapper.getMemberById(id);
        if(member == null){
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
     * member의 password를 변경합니다.
     *
     * @param passwordRequest before(현재 password), after(새로운 password)를 가지는 객체
     */
    public void updatePassword(int id, UpdatePasswordRequest passwordRequest) {
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
     * member의 point를 증가시킵니다.
     *
     * @param id member의 id
     * @param point 증가시키고자 하는 point의 양
     */
    public void increasePoint(int id, int point) {
        mapper.increasePoint(id, point);
    }

    /**
     * member의 point를 감소시킵니다.
     *
     * @param id member의 id
     * @param point 감소시키고자 하는 point의 양
     */
    public void decreasePoint(int id, int point) {
        Member member = getMemberById(id);
        if (member.getPoint() < point) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }
        mapper.decreasePoint(id, point);
    }
}
