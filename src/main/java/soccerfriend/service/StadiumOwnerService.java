package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Member;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NotMatchException;
import soccerfriend.mapper.StadiumOwnerMapper;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;
import soccerfriend.utility.InputForm.UpdateStadiumOwnerRequest;

import java.util.Optional;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class StadiumOwnerService {

    private final StadiumOwnerMapper mapper;

    /**
     * stadiumOwnerRequest의 정보가 완전한지 확인합니다.
     *
     * @param stadiumOwnerRequest
     * @return stadiumOwner의 정보가 완전하면 true 불완전하면 false
     */
    private boolean isValidStadiumOwnerRequest(UpdateStadiumOwnerRequest stadiumOwnerRequest) {
        return stadiumOwnerRequest.getRepresentative() == null
                || stadiumOwnerRequest.getCompanyName() == null
                || stadiumOwnerRequest.getAddress() == null
                || stadiumOwnerRequest.getTaxpayerId() == null
                || stadiumOwnerRequest.getAccountNumber() == null;
    }

    /**
     * 회원가입을 수행합니다.
     *
     * @param stadiumOwner stadiumOwnerId, password, representative, companyName, address, taxpayerId, accountBankId, accountNumber를 가진 StadiumOwner 객체
     * @return 회원가입한 StadiumOwner의 stadiumOwnerId
     */
    public void signUp(StadiumOwner stadiumOwner) {
        if (isStadiumOwnerExist(stadiumOwner.getStadiumOwnerId())) {
            throw new DuplicatedException(ID_DUPLICATED);
        }
        StadiumOwner encryptedStadiumOwner = StadiumOwner.builder()
                                                         .stadiumOwnerId(stadiumOwner.getStadiumOwnerId())
                                                         .password(BCrypt.hashpw(stadiumOwner.getPassword(), BCrypt.gensalt()))
                                                         .representative(stadiumOwner.getRepresentative())
                                                         .companyName(stadiumOwner.getCompanyName())
                                                         .address(stadiumOwner.getAddress())
                                                         .taxpayerId(stadiumOwner.getTaxpayerId())
                                                         .accountBankId(stadiumOwner.getAccountBankId())
                                                         .accountNumber(stadiumOwner.getAccountNumber())
                                                         .point(0)
                                                         .build();

        mapper.insert(encryptedStadiumOwner);
    }

    /**
     * StadiumOwner 정보를 삭제합니다.
     */
    public void deleteAccount(int id) {
        mapper.delete(id);
    }

    /**
     * 해당 stadiumOwnerId를 사용중인 stadiumOwner가 있는지 확인합니다.
     *
     * @param stadiumOwnerId 존재 유무를 확인하려는 stadiumOwnerId
     * @return stadiumOwnerId 존재 유무(true: 있음, false: 없음)
     */
    public boolean isStadiumOwnerExist(String stadiumOwnerId) {
        return mapper.isStadiumOwnerIdExist(stadiumOwnerId);
    }

    /**
     * stadiumOwnerId와 password를 입력받아 해당 stadiumOwner를 반환합니다.
     *
     * @param stadiumOwnerId
     * @param password
     * @return stadiumOwner의 Optional 객체
     */
    public Optional<StadiumOwner> getStadiumOwnerByStadiumOwnerIdAndPassword(String stadiumOwnerId, String password) {

        if (!isStadiumOwnerExist(stadiumOwnerId)) return Optional.empty();

        Optional<StadiumOwner> stadiumOwner =
                Optional.ofNullable(mapper.getStadiumOwnerByStadiumOwnerId(stadiumOwnerId));

        if (BCrypt.checkpw(password, stadiumOwner.get().getPassword())) {
            return stadiumOwner;
        }

        return Optional.empty();
    }

    public StadiumOwner getStadiumOwnerById(int id) {
        StadiumOwner stadiumOwner = mapper.getStadiumOwnerById(id);
        if (stadiumOwner == null) {
            throw new BadRequestException(STADIUM_OWNER_NOT_EXIST);
        }

        return stadiumOwner;
    }

    /**
     * stadiumOwner의 정보를 변경합니다.
     *
     * @param stadiumOwnerRequest
     */
    public void updateStadiumOwner(int id, UpdateStadiumOwnerRequest stadiumOwnerRequest) {
        if (!isValidStadiumOwnerRequest(stadiumOwnerRequest)) {
            throw new BadRequestException(FORM_NOT_FULL);
        }

        mapper.updateStadiumOwner(id, stadiumOwnerRequest);
    }

    /**
     * member의 password를 변경합니다.
     *
     * @param passwordRequest before(현재 password), after(새로운 password)를 가지는 객체
     */
    public void updatePassword(int id, UpdatePasswordRequest passwordRequest) {
        String before = passwordRequest.getBefore();
        String after = passwordRequest.getAfter();
        String encryptedCurrent = mapper.getStadiumOwnerById(id).getPassword();

        if (BCrypt.checkpw(after, encryptedCurrent)) {
            throw new NotMatchException(PASSWORD_SAME);
        }

        after = BCrypt.hashpw(after, BCrypt.gensalt());
        mapper.updatePassword(id, after);
    }

    /**
     * stadiumOwner의 point를 증가시킵니다.
     *
     * @param id    stadiumOwner의 id
     * @param point 증가시키고자 하는 point의 양
     */
    public void increasePoint(int id, int point) {
        mapper.increasePoint(id, point);
    }

    /**
     * stadiumOwner의 point를 감소시킵니다.
     *
     * @param id    stadiumOwner의 id
     * @param point 감소시키고자 하는 point의 양
     */
    public void decreasePoint(int id, int point) {
        StadiumOwner stadiumOwner = getStadiumOwnerById(id);
        if (stadiumOwner.getPoint() < point) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }
        mapper.decreasePoint(id, point);
    }
}
