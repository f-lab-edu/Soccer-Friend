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

    /**
     * 클럽 내에 게시판을 생성합니다.
     *
     * @param clubId   클럽의 id
     * @param bulletin 새로 생성할 게시판의 정보
     */
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

    /**
     * 게시판을 삭제합니다.
     *
     * @param id 삭제할 게시판의 id
     */
    @Transactional
    public void delete(int id) {
        Bulletin bulletin = getBulletinById(id);
        int clubId = bulletin.getClubId();

        mapper.delete(id);
        clubService.decreaseBulletinNum(clubId);
        redisTemplate.delete("BULLETIN::BULLETIN" + String.valueOf(id));
        redisTemplate.delete("BULLETIN::BULLETIN CLUB" + String.valueOf(clubId));
    }

    /**
     * 게시판을 영구적으로 삭제합니다.(관리자용)
     *
     * @param id 삭제할 게시판의 id
     */
    public void deletePermanently(int id) {
        if (!isIdExistIncludeDeleted(id)) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }
        mapper.deletePermanently(id);
    }

    /**
     * 해당 id의 게시판이 삭제여부와 관계없이 db내에 존재하는지 확인합니다.
     *
     * @param id
     * @return
     */
    public boolean isIdExistIncludeDeleted(int id) {
        return mapper.isIdExistIncludeDeleted(id);
    }

    /**
     * 해당 이름의 게시판이 클럽내에 존재하는지 확인합니다.
     *
     * @param clubId 클럽의 id
     * @param name   게시판의 이름
     * @return 해당 클럽 내 게시판 이름 존재 유무
     */
    public boolean isNameExist(int clubId, String name) {
        return mapper.isNameExist(clubId, name);
    }

    /**
     * 특정 id의 게시판을 반환합니다.
     *
     * @param id 게시판의 id
     * @return 특정 id의 게시판
     */
    @Cacheable(value = "BULLETIN", key = "'BULLETIN'+#id")
    public Bulletin getBulletinById(int id) {
        Bulletin bulletin = mapper.getBulletinById(id);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletin;
    }

    /**
     * 특정 club에 있는 모든 게시판을 반환합니다.
     *
     * @param clubId 클럽의 id
     * @return 클럽에 존재하는 모든 게시판
     */
    @Cacheable(value = "BULLETIN", key = "'BULLETIN CLUB'+#clubId")
    public List<Bulletin> getBulletinsByClubId(int clubId) {
        List<Bulletin> bulletins = mapper.getBulletinsByClubId(clubId);
        if (bulletins.isEmpty()) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletins;
    }
}
