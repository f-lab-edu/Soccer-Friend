package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.ClubSoccerMatchRecord;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.ClubSoccerMatchRecordMapper;

import static soccerfriend.exception.ExceptionInfo.ALREADY_CLUB_HAS_RECORD;
import static soccerfriend.exception.ExceptionInfo.CLUB_HAS_NO_RECORD;

@Service
@RequiredArgsConstructor
public class ClubSoccerMatchRecordService {

    private final ClubSoccerMatchRecordMapper mapper;

    public void create(int clubId) {
        if (isClubIdExist(clubId)) {
            throw new BadRequestException(ALREADY_CLUB_HAS_RECORD);
        }

        ClubSoccerMatchRecord clubSoccerMatchRecord = new ClubSoccerMatchRecord(clubId, 0, 0, 0);
        mapper.insert(clubSoccerMatchRecord);
    }

    public boolean isClubIdExist(int clubId) {
        return mapper.isClubIdExist(clubId);
    }

    public void increaseWin(int clubId) {
        if (!isClubIdExist(clubId)) {
            throw new BadRequestException(CLUB_HAS_NO_RECORD);
        }

        mapper.increaseWin(clubId);
    }

    public void increaseDraw(int clubId) {
        if (!isClubIdExist(clubId)) {
            throw new BadRequestException(CLUB_HAS_NO_RECORD);
        }

        mapper.increaseDraw(clubId);
    }

    public void increaseLose(int clubId) {
        if (!isClubIdExist(clubId)) {
            throw new BadRequestException(CLUB_HAS_NO_RECORD);
        }

        mapper.increaseLose(clubId);
    }
}
