package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Bulletin;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.BulletinService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.LoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;
    private final LoginService loginService;
    private final ClubMemberService clubMemberService;

    @PostMapping
    public void create(@Validated @RequestBody Bulletin bulletin) {
        int memberId = loginService.getMemberId();
        int clubId = bulletin.getClubId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(ExceptionInfo.NO_CLUB_PERMISSION);
        }

        bulletinService.create(bulletin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        int memberId = loginService.getMemberId();

        bulletinService.delete(memberId, id);
    }
}
