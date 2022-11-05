package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.SoccerMatch;

import java.util.List;


@Mapper
public interface SoccerMatchMapper {

    public void insert(int soccerMatchRecruitmentId);

    public SoccerMatch getSoccerMatchById(int id);

    public SoccerMatch getSoccerMatchBySoccerMatchRecruitmentId(int soccerMatchRecruitmentId);

    public List<SoccerMatch> getSoccerMatchByClubId(int clubId);

    public void increaseHostClubScore(int id);

    public void increaseParticipationClubScore(int id);

    public int getHostClubId(int id);

    public int getParticipationClubId(int id);

    public boolean isClubExist(@Param("id") int id, @Param("clubId") int clubId);

}
