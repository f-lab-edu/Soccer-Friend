package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Member;
import soccerfriend.mapper.MemberMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;

    public int signUp(Member member) {
        Member encryptedMember = Member.builder()
                                       .loginId(member.getLoginId())
                                       .password(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()))
                                       .nickname(member.getNickname())
                                       .point(0)
                                       .build();

        return mapper.insert(encryptedMember);
    }

    public boolean isLoginIdExist(String login_id) {
        return mapper.isLoginIdExist(login_id);
    }

    public boolean isNicknameExist(String nickname) {
        return mapper.isNicknameExist(nickname);
    }

    public Optional<Member> getMemberByLoginIdAndPassword(String loginId, String password) {

        if (!isLoginIdExist(loginId)) return Optional.empty();

        Optional<Member> member =
                Optional.ofNullable(mapper.getMemberByLoginId(loginId));

        if (BCrypt.checkpw(password, member.get()
                                                .getPassword())) {
            return member;
        }

        return Optional.empty();
    }

    public void setSoccerInfo(int memberId, int soccerInfoId) {
        mapper.setSoccerInfo(memberId, soccerInfoId);
    }
}
