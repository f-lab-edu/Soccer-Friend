package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Positions;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.PositionsMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.POSITIONS_NOT_EXIST;

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
        List<Positions> all = mapper.getAll();
        if (all.isEmpty()) {
            throw new BadRequestException(POSITIONS_NOT_EXIST);
        }

        return all;
    }
}
