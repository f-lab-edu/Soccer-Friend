package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Address;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.utility.InputForm;
import soccerfriend.utility.InputForm.UpdateSoccerMatchRecruitmentRequest;

import java.util.List;

@Mapper
public interface SoccerMatchRecruitmentMapper {

    public void insert(SoccerMatchRecruitment soccerMatchRecruitment);

    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(int id);

    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(int clubId);

    public void update(@Param("id") int id, @Param("request") UpdateSoccerMatchRecruitmentRequest request);

    public void setClub2Id(@Param("id") int id, @Param("club2Id") int club2Id);

    public boolean isNoClub2Id(int id);
}
