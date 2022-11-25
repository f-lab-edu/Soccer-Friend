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

    /**
     * 클럽에 게시판을 생성합니다.
     *
     * @param clubId   게시판을 추가할 클럽의 id
     * @param bulletin 새로 추가할 게시판의 정보
     */
    @PostMapping("/club/{clubId}")
    @IsClubLeaderOrManager
    public void create(@PathVariable int clubId, @Validated @RequestBody Bulletin bulletin) {
        bulletinService.create(clubId, bulletin);
    }

    /**
     * 클럽에 있는 게시판을 삭제합니다.
     *
     * @param clubId 게시판을 삭제할 클럽의 id
     * @param id     삭제할 게시판의 id
     */
    @DeleteMapping("/club/{clubId}/{id}")
    @IsClubLeaderOrManager
    public void delete(@PathVariable int clubId, @PathVariable int id) {
        bulletinService.delete(id);
    }

    /**
     * 특정 클럽에 존재하는 모든 게시판을 반환합니다.
     *
     * @param clubId 클럽의 id
     * @return 특정 클럽에 존재하는 모든 게시판의 정보
     */
    @GetMapping("/club/{clubId}")
    @IsClubMember
    public List<Bulletin> getBulletinsByClubId(@PathVariable int clubId) {
        return bulletinService.getBulletinsByClubId(clubId);
    }

    /**
     * 클럽에 존재하는 특정 id의 게시판을 반환합니다.
     *
     * @param clubId 클럽의 id
     * @param id 게시판의 id
     * @return 클럽에 존재하는 특정 id의 게시판
     */
    @GetMapping("/club/{clubId}/{id}")
    @IsClubMember
    public Bulletin getBulletinById(@PathVariable int clubId, @PathVariable int id) {
        return bulletinService.getBulletinById(id);
    }
}
