package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.ClubMember;

import java.util.List;

@Mapper
public interface ClubMemberMapper {

    public void insert(ClubMember clubMember);

    public boolean isClubLeader(@Param("clubId") int clubId, @Param("memberId") int memberId);

    public boolean isClubStaffOrLeader(@Param("clubId") int clubId, @Param("memberId") int memberId);

    public boolean isClubMember(@Param("clubId") int clubId, @Param("memberId") int memberId);

    public boolean isApplied(@Param("clubId") int clubId, @Param("memberId") int memberId);

    public ClubMember getClubMemberById(int id);

    public List<ClubMember> getClubMembers(int clubId);

    public List<ClubMember> getNotApprovedClubMembers(int clubId);

    public void setApprovedTrue(int id);

    public void delete(@Param("clubId") int clubId, @Param("memberId") int memberId);

    public void payMonthlyFee(@Param("clubId") int clubId, @Param("memberId") int memberId);
}
