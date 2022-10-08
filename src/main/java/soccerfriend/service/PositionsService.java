package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Positions;
import soccerfriend.mapper.PositionsMapper;

import java.sql.SQLOutput;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsService {

    private final PositionsMapper mapper;

    public List<Positions> getAll(){
        return mapper.getAll();
    }
}
