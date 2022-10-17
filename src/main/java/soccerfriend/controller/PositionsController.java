package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soccerfriend.dto.Positions;
import soccerfriend.service.PositionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionsController {

    private final PositionsService service;

    @GetMapping()
    public List<Positions> getAll() {
        return service.getAll();
    }
}

