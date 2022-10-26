package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Member;

@Mapper
public interface MemberMapper {

    public void insert(Member member);

    public void delete(int id);

    public Member getMemberByMemberId(String memberId);

    public Member getMemberById(int id);

    public boolean isMemberIdExist(String memberId);

    public boolean isNicknameExist(String nickname);

    public void updateNickname(@Param("id") int id, @Param("nickname") String nickname);

    public void updatePassword(@Param("id") int id, @Param("password") String password);

    public void increasePoint(@Param("id") int id, @Param("point") int point);

    public void decreasePoint(@Param("id") int id, @Param("point") int point);
}
