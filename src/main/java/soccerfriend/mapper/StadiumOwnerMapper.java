package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.utility.InputForm.UpdateStadiumOwnerRequest;

@Mapper
public interface StadiumOwnerMapper {

    public void insert(StadiumOwner stadiumOwner);

    public void delete(String stadiumOwnerId);

    public StadiumOwner getStadiumOwner(String stadiumOwnerId);

    public boolean isStadiumOwnerIdExist(String stadiumOwnerId);

    public void updateStadiumOwner(@Param("target") String stadiumOwnerId, @Param("request") UpdateStadiumOwnerRequest stadiumOwnerRequest);

    public void updatePassword(@Param("stadiumOwnerId") String stadiumOwnerId, @Param("password") String password);
}
