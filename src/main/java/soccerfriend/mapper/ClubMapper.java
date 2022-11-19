package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Club;


@Mapper
public interface ClubMapper {

    public void insert(Club club);

    public Club getClubById(int id);

    public Club getClubByName(String name);

    public boolean isNameExist(String name);

    public boolean isIdExist(int id);

    public void updateName(@Param("id") int id, @Param("name") String name);

    public void updateAddressId(@Param("id") int id, @Param("addressId") int addressId);

    public void updateMonthlyFee(@Param("id") int id, @Param("monthlyFee") int monthlyFee);

    public void increasePoint(@Param("id") int id, @Param("point") int point);

    public void decreasePoint(@Param("id") int id, @Param("point") int point);

    public void increaseBulletinNum(int id);

    public void decreaseBulletinNum(int id);
}
