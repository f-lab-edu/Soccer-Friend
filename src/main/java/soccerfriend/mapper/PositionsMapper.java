package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.Positions;

import java.util.List;

@Mapper
public interface PositionsMapper {

    public List<Positions> getAll();
}
