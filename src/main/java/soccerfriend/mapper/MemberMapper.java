package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Member;

@Mapper
public interface MemberMapper {

    public int insert(Member member);

    public void delete(String LoginId);

    public Member getMemberByLoginId(String loginId);

    public boolean isLoginIdExist(String loginId);

    public boolean isNicknameExist(String nickname);

    public void setSoccerInfo(@Param("loginId") String loginId, @Param("soccerInfoId") int soccerInfoId);

    public void updateNickname(@Param("loginId") String loginId, @Param("nickname") String nickname);

    public void updatePassword(@Param("loginId") String loginId, @Param("password") String password);
}
