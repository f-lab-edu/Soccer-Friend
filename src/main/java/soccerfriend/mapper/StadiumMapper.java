package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Club;
import soccerfriend.dto.Stadium;
import soccerfriend.utility.InputForm;
import soccerfriend.utility.InputForm.UpdateStadiumRequest;

import java.util.List;


@Mapper
public interface StadiumMapper {

    public void insert(Stadium stadium);

    public boolean isStadiumOwner(@Param("id") int id, @Param("stadiumOwnerId") int stadiumOwnerId);

    public Stadium getStadiumById(int id);

    public List<Stadium> getStadiumByStadiumOwnerId(int stadiumOwnerId);

    public void updateStadium(@Param("id") int id, @Param("stadium") UpdateStadiumRequest stadium);

    public void updateStadiumOwner(@Param("id") int id, @Param("stadiumOwnerId") int stadiumOwnerId);
}
