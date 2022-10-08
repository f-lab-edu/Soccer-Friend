package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.SoccerInfo;

@Mapper
public interface SoccerInfoMapper {

    public int insert(SoccerInfo soccerInfo);
}
