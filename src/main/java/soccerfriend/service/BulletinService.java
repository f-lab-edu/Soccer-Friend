package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.Bulletin;
import soccerfriend.dto.Club;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.mapper.BulletinMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class BulletinService {
    private final ClubService clubService;
    private final BulletinMapper mapper;
    private final int MAX_BULLETIN_NUM = 8;
    private final RedisTemplate redisTemplate;

    @Transactional
    public void create(int clubId, Bulletin bulletin) {
        String name = bulletin.getName();
        Club club = clubService.getClubById(clubId);
        int bulletinNum = club.getBulletinNum();
        Bulletin newBulletin = Bulletin.builder()
                                       .clubId(clubId)
                                       .name(name)
                                       .category(bulletin.getCategory())
                                       .build();

        if (!clubService.isIdExist(clubId)) {
            throw new BadRequestException(CLUB_ID_NOT_EXIST);
        }
        if (isNameExist(clubId, name)) {
            throw new DuplicatedException(BULLETIN_NAME_DUPLICATED);
        }
        if (bulletinNum >= MAX_BULLETIN_NUM) {
            throw new BadRequestException(CLUB_BULLETINS_FULL);
        }

        mapper.insert(newBulletin);
        clubService.increaseBulletinNum(clubId);
    }

    @Transactional
    public void delete(int id) {
        Bulletin bulletin = getBulletinById(id);
        int clubId = bulletin.getClubId();

        mapper.delete(id);
        clubService.decreaseBulletinNum(clubId);
        redisTemplate.delete("BULLETIN::BULLETIN" + String.valueOf(id));
        redisTemplate.delete("BULLETIN::BULLETIN CLUB" + String.valueOf(clubId));
    }

    public void deletePermanently(int id) {
        if (!isIdExistIncludeDeleted(id)) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }
        mapper.deletePermanently(id);
    }

    public boolean isIdExist(int id) {
        return mapper.isIdExist(id);
    }

    public boolean isIdExistIncludeDeleted(int id) {
        return mapper.isIdExistIncludeDeleted(id);
    }

    public boolean isNameExist(int clubId, String name) {
        return mapper.isNameExist(clubId, name);
    }

    @Cacheable(value = "BULLETIN", key = "'BULLETIN'+#id")
    public Bulletin getBulletinById(int id) {
        Bulletin bulletin = mapper.getBulletinById(id);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletin;
    }

    @Cacheable(value = "BULLETIN", key = "'BULLETIN CLUB'+#clubId")
    public List<Bulletin> getBulletinsByClubId(int clubId) {
        List<Bulletin> bulletins = mapper.getBulletinsByClubId(clubId);
        if (bulletins.isEmpty()) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletins;
    }
}
