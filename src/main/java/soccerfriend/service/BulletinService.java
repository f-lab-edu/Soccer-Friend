package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Bulletin;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.mapper.BulletinMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class BulletinService {
    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    private final BulletinMapper mapper;

    public void create(Bulletin bulletin) {
        int clubId = bulletin.getClubId();
        String name = bulletin.getName();
        if (!clubService.isIdExist(clubId)) {
            throw new BadRequestException(CLUB_ID_NOT_EXIST);
        }
        if (isNameExist(clubId, name)) {
            throw new DuplicatedException(BULLETIN_NAME_DUPLICATED);
        }
        mapper.insert(bulletin);
    }

    public void delete(int memberId, int id) {
        Bulletin bulletin = getBulletinById(id);
        int clubId = bulletin.getClubId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        mapper.delete(id);
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

    public Bulletin getBulletinById(int id) {
        Bulletin bulletin = mapper.getBulletinById(id);
        if (bulletin == null) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletin;
    }

    public List<Bulletin> getBulletinByClubId(int clubId) {
        List<Bulletin> bulletins = mapper.getBulletinsByClubId(clubId);
        if (bulletins.isEmpty()) {
            throw new BadRequestException(BULLETIN_NOT_EXIST);
        }

        return bulletins;
    }
}
