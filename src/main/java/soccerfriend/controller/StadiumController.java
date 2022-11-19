package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Stadium;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.LoginService;
import soccerfriend.service.StadiumService;
import soccerfriend.utility.InputForm.UpdateStadiumRequest;

import java.util.List;


import static soccerfriend.exception.ExceptionInfo.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadiums")
public class StadiumController {

    private final StadiumService stadiumService;
    private final LoginService loginService;

    /**
     * stadium을 생성합니다.
     *
     * @param stadium stadium의 정보
     */
    @PostMapping
    public void create(@RequestBody Stadium stadium) {
        int stadiumOwnerId = loginService.getStadiumOwnerId();
        stadiumService.create(stadiumOwnerId, stadium);
    }

    /**
     * 특정 id의 stadium을 반환합니다.
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Stadium getStadiumById(@PathVariable int id) {
        return stadiumService.getStadiumById(id);
    }

    /**
     * stadiumOwner 자신이 소유한 stadium들을 반환합니다.
     *
     * @return 본인이 소유한 stadium들
     */
    @GetMapping("/stadium-owner")
    public List<Stadium> getStadiumByStadiumOwnerId() {
        int stadiumOwnerId = loginService.getStadiumOwnerId();
        return stadiumService.getStadiumByStadiumOwnerId(stadiumOwnerId);
    }

    /**
     * stadium의 정보를 수정합니다.
     *
     * @param id      stadium의 id
     * @param stadium 새로 변경할 stadium의 정보
     */
    @PatchMapping("{id}")
    public void updateStadium(@PathVariable int id, @RequestBody UpdateStadiumRequest stadium) {
        int stadiumOwnerId = loginService.getStadiumOwnerId();
        if (!stadiumService.isStadiumOwner(id, stadiumOwnerId)) {
            throw new NoPermissionException(NOT_STADIUM_OWNER);
        }

        stadiumService.updateStadium(id, stadium);
    }
}