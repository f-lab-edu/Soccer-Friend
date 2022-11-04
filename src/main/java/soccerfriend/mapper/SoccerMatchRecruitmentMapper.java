package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.utility.InputForm.UpdateSoccerMatchRecruitmentRequest;

import java.util.List;

@Mapper
public interface SoccerMatchRecruitmentMapper {

    public void insert(SoccerMatchRecruitment soccerMatchRecruitment);

    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(int id);

    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(int clubId);

    public void update(@Param("id") int id, @Param("request") UpdateSoccerMatchRecruitmentRequest request);

    public void setClubBId(@Param("id") int id, @Param("clubBId") int clubBId);

    public boolean isNoClubBId(int id);
}
