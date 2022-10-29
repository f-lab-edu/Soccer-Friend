package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.dto.SoccerMatchRecruitment;

import java.util.List;


@Mapper
public interface SoccerMatchMapper {

    public void insert(int soccerMatchRecruitmentId);

    public SoccerMatch getSoccerMatchById(int id);

    public SoccerMatch getSoccerMatchBySoccerMatchRecruitmentId(int soccerMatchRecruitmentId);

    public List<SoccerMatch> getSoccerMatchByClubId(int clubId);

    public void increaseClub1Score(int id);

    public void increaseClub2Score(int id);

    public int getClub1Id(int id);

    public int getClub2Id(int id);

    public boolean isClubExist(@Param("id") int id, @Param("clubId") int clubId);

}
