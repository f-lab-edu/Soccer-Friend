package soccerfriend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.ClubMonthlyFee;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.ClubMonthlyFeeMapper;

@Service
@RequiredArgsConstructor
public class ClubMonthlyFeeService {

    private final ClubMonthlyFeeMapper mapper;

    /**
     * club의 월회비 납부 기록을 추가합니다.
     *
     * @param clubId       club의 id
     * @param clubMemberId clubMember의 id
     * @param year         납부한 연도
     * @param month        납부한 월
     */
    public void add(int clubId, int clubMemberId, int price, int year, int month) {
        if (mapper.isClubMonthlyFeeExist(clubId, clubMemberId, year, month)) {
            throw new BadRequestException(ExceptionInfo.ALREADY_PAID_CLUB_MONTHLY_FEE);
        }

        mapper.insert(clubId, clubMemberId, price, year, month);
    }

    /**
     * club 월회비 납부 기록이 있는지 확인합니다.
     *
     * @param clubId       club의 id
     * @param clubMemberId clubMember의 id
     * @param year         납부한 연도
     * @param month        납부한 월
     * @return 납부 여부
     */
    public boolean isAlreadyPaid(int clubId, int clubMemberId, int year, int month) {
        return mapper.isClubMonthlyFeeExist(clubId, clubMemberId, year, month);
    }
}
