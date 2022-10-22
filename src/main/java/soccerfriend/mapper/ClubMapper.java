package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.Club;


@Mapper
public interface ClubMapper {

    public void insert(Club club);

    public Club getClubByName(String name);

    public boolean isNameExist(String name);

    public boolean isIdExist(int id);
}
