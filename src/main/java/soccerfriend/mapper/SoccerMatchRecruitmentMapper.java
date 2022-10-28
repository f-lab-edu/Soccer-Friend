package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Address;
import soccerfriend.dto.SoccerMatchRecruitment;

import java.util.List;

@Mapper
public interface SoccerMatchRecruitmentMapper {

    public void insert(SoccerMatchRecruitment soccerMatchRecruitment);

    public void delete(int id);

    public void getSoccerMatchRecruitmentById(int id);

    public void getSoccerMatchRecruitmentByClubId(int clubId);

    public void update(int id);

    public void setClub2Id(@Param("id") int id, @Param("club2Id") int club2Id);
}
