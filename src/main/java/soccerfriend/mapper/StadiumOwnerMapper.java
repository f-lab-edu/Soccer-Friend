package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.StadiumOwner;

@Mapper
public interface StadiumOwnerMapper {

    public int insert(StadiumOwner stadiumOwner);

    public void delete(String stadiumOwnerId);

    public StadiumOwner getStadiumOwner(String stadiumOwnerId);

    public boolean isStadiumOwnerIdExist(String stadiumOwnerId);

    // 수정
    public void updateStadiumOwner(@Param("stadiumOwnerId") String stadiumOwnerId, StadiumOwner stadiumOwner);

    public void updatePassword(@Param("stadiumOwnerId") String stadiumOwnerId, @Param("password") String password);
}
