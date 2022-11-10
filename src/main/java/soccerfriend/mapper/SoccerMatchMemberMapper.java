package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.dto.SoccerMatchMember;

import java.util.List;


@Mapper
public interface SoccerMatchMemberMapper {

    public void insert(SoccerMatchMember soccerMatchMember);

    public SoccerMatchMember getSoccerMatchMemberById(int id);

    public boolean isSoccerMatchMemberExist(@Param("soccerMatchId") int soccerMatchId, @Param("memberId") int memberId);

    public void approve(int id);

    public List<SoccerMatchMember> getApprovedSoccerMatchMember(@Param("soccerMatchId") int soccerMatchId, @Param("clubId") int clubId);

    public List<SoccerMatchMember> getNotApprovedSoccerMatchMember(@Param("soccerMatchId") int soccerMatchId, @Param("clubId") int clubId);
}
