package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Stadium;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.StadiumService;
import soccerfriend.utility.InputForm.UpdateStadiumRequest;

import java.util.List;


import static soccerfriend.exception.ExceptionInfo.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadiums")
public class StadiumController {

    private final StadiumService stadiumService;
    private final AuthorizeService authorizeService;

    @PostMapping
    public void create(@RequestBody Stadium stadium) {
        int stadiumOwnerId = authorizeService.getStadiumOwnerId();
        stadiumService.create(stadiumOwnerId, stadium);
    }

    @GetMapping("/{id}")
    public Stadium getStadiumById(@PathVariable int id) {
        return stadiumService.getStadiumById(id);
    }

    @GetMapping("/stadium-owner")
    public List<Stadium> getStadiumByStadiumOwnerId() {
        int stadiumOwnerId = authorizeService.getStadiumOwnerId();
        return stadiumService.getStadiumByStadiumOwnerId(stadiumOwnerId);
    }

    @PatchMapping("{id}")
    public void updateStadium(@PathVariable int id, @RequestBody UpdateStadiumRequest stadium) {
        int stadiumOwnerId = authorizeService.getStadiumOwnerId();
        if (!stadiumService.isStadiumOwner(id, stadiumOwnerId)) {
            throw new NoPermissionException(NOT_STADIUM_OWNER);
        }

        stadiumService.updateStadium(id, stadium);
    }
}