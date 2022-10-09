package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soccerfriend.dto.SoccerInfo;
import soccerfriend.service.SoccerInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/soccerinfo")
public class SoccerInfoController {

    private final SoccerInfoService service;

    @PostMapping("")
    public int signUp(@RequestBody SoccerInfo soccerInfo) {
        return service.insert(soccerInfo);
    }
}

