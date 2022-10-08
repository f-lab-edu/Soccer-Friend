package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Member;

@Mapper
public interface MemberMapper {

    public int insert(Member member);

    public Member getMemberByLoginIdAndLoginPassword(String loginId, String loginPassword);

    public boolean isLoginIdExist(String login_id);

    public boolean isNicknameExist(String nickname);

    public void setSoccerInfo(@Param("memberId") int memberId, @Param("soccerInfoId") int soccerInfoId);
}
