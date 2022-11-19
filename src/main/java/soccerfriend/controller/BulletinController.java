package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soccerfriend.authentication.IsClubLeaderOrManager;
import soccerfriend.authentication.IsClubMember;
import soccerfriend.dto.Bulletin;
import soccerfriend.service.BulletinService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping("/club/{clubId}")
    @IsClubLeaderOrManager
    public void create(@Validated @RequestBody Bulletin bulletin, @PathVariable int clubId) {
        bulletinService.create(clubId, bulletin);
    }

    @DeleteMapping("/club/{clubId}/{id}")
    @IsClubLeaderOrManager
    public void delete(@PathVariable int clubId, @PathVariable int id) {
        bulletinService.delete(id);
    }

    @GetMapping("/club/{clubId}")
    @IsClubMember
    public List<Bulletin> getBulletinsByClubId(@PathVariable int clubId) {
        return bulletinService.getBulletinsByClubId(clubId);
    }

    @GetMapping("/club/{clubId}/{id}")
    @IsClubMember
    public Bulletin getBulletinById(@PathVariable int clubId, @PathVariable int id) {
        return bulletinService.getBulletinById(id);
    }
}
