package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Member;

@Mapper
public interface MemberMapper {

    public int insert(Member member);

    public void delete(String memberId);

    public Member getMember(String memberId);

    public boolean isMemberIdExist(String memberId);

    public boolean isNicknameExist(String nickname);

    public void setSoccerInfo(@Param("memberId") String memberId, @Param("soccerInfoId") int soccerInfoId);

    public void updateNickname(@Param("memberId") String memberId, @Param("nickname") String nickname);

    public void updatePassword(@Param("memberId") String memberId, @Param("password") String password);
}
