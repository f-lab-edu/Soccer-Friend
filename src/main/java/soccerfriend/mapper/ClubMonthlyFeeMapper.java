package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.ClubMonthlyFee;

@Mapper
public interface ClubMonthlyFeeMapper {

    public void insert(@Param("clubId") int clubId, @Param("clubMemberId") int clubMemberId, @Param("price") int price, @Param("year") int year, @Param("month") int month);

    public boolean isClubMonthlyFeeExist(@Param("clubId") int clubId, @Param("clubMemberId") int clubMemberId, @Param("year") int year, @Param("month") int month);
}
