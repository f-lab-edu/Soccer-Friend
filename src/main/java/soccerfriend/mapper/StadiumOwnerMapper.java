package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.utility.InputForm.UpdateStadiumOwnerRequest;

@Mapper
public interface StadiumOwnerMapper {

    public int insert(StadiumOwner stadiumOwner);

    public void delete(int id);

    public StadiumOwner getStadiumOwnerByStadiumOwnerId(String stadiumOwnerId);

    public StadiumOwner getStadiumOwnerById(int id);

    public boolean isStadiumOwnerIdExist(String stadiumOwnerId);

    public void updateStadiumOwner(@Param("id") int id, @Param("request") UpdateStadiumOwnerRequest stadiumOwnerRequest);

    public void updatePassword(@Param("id") int id, @Param("password") String password);
}
