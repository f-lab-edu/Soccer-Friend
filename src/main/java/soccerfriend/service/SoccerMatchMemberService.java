package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerMatchMember;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.SoccerMatchMemberMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;


@Service
@RequiredArgsConstructor
public class SoccerMatchMemberService {

    private final SoccerMatchMemberMapper mapper;
    private final SoccerMatchService soccerMatchService;

    /**
     * 회원들이 축구경기에 참여신청을 합니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @param clubId        club의 id
     * @param memberId      member의 id
     */
    public void apply(int soccerMatchId, int clubId, int memberId) {
        if (isSoccerMatchMemberExist(soccerMatchId, memberId)) {
            throw new BadRequestException(ALREADY_JOINED_SOCCER_MATCH);
        }
        if (!soccerMatchService.isClubExist(soccerMatchId, clubId)) {
            throw new BadRequestException(NOT_CLUB_OF_SOCCER_MATCH);
        }
        SoccerMatchMember soccerMatchMember = new SoccerMatchMember(soccerMatchId, clubId, memberId, false);
        mapper.insert(soccerMatchMember);
    }

    /**
     * 특정 id의 soccerMatchMember를 반환합니다.
     *
     * @param id soccerMatchMember의 id
     * @return 특정 id의 soccerMatchMember 객체
     */
    public SoccerMatchMember getSoccerMatchMemberById(int id) {
        SoccerMatchMember soccerMatchMember = mapper.getSoccerMatchMemberById(id);
        if (soccerMatchMember == null) {
            throw new BadRequestException(ExceptionInfo.NOT_CLUB_MEMBER);
        }

        return soccerMatchMember;
    }

    /**
     * 해당 soccerMatch에 특정 member가 이미 참여신청했는지 여부
     *
     * @param soccerMatchId soccerMatch의 id
     * @param memberId      member의 id
     * @return
     */
    public boolean isSoccerMatchMemberExist(int soccerMatchId, int memberId) {
        return mapper.isSoccerMatchMemberExist(soccerMatchId, memberId);
    }

    /**
     * 경기에 참가신청한 선수를 승인합니다.
     *
     * @param id soccerMatchMember의 id
     */
    public void approve(int id) {
        mapper.approve(id);
    }

    /**
     * 해당 경기에 특정 club으로 참가신청한 선수 중 승인된 선수들을 반환합니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @param clubId        club의 id
     * @return 참가신청이 승인된 선수들
     */
    public List<SoccerMatchMember> getApprovedSoccerMatchMember(int soccerMatchId, int clubId) {
        if (!soccerMatchService.isClubExist(soccerMatchId, clubId)) {
            throw new BadRequestException(NOT_CLUB_OF_SOCCER_MATCH);
        }

        return mapper.getApprovedSoccerMatchMember(soccerMatchId, clubId);
    }

    /**
     * 해당 경기에 특정 club으로 참가신청한 선수 중 승인대기중인 선수들을 반환합니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @param clubId        club의 id
     * @return 참가신청 후 승인대기중인 선수들
     */
    public List<SoccerMatchMember> getNotApprovedSoccerMatchMember(int soccerMatchId, int clubId) {
        if (!soccerMatchService.isClubExist(soccerMatchId, clubId)) {
            throw new BadRequestException(NOT_CLUB_OF_SOCCER_MATCH);
        }

        return mapper.getNotApprovedSoccerMatchMember(soccerMatchId, clubId);
    }
}
