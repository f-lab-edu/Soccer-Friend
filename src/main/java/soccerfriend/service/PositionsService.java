package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Positions;
import soccerfriend.mapper.PositionsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionsService {

    private final PositionsMapper mapper;

    /**
     * 모든 postions를 반환합니다.
     *
     * @return 모든 positions
     */
    public List<Positions> getAll() {
        return mapper.getAll();
    }
}
