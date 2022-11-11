package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.ClubSoccerMatchRecord;


@Mapper
public interface ClubSoccerMatchRecordMapper {
    public void insert(ClubSoccerMatchRecord clubSoccerMatchRecord);

    public void increaseWin(int clubId);

    public void increaseDraw(int clubId);

    public void increaseLose(int clubId);

    public boolean isClubIdExist(int clubId);
}
