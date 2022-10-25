package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.mapper.StadiumOwnerMapper;

import java.util.Optional;

import static soccerfriend.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class StadiumOwnerService {

    private final StadiumOwnerMapper mapper;

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
                Optional.ofNullable(mapper.getStadiumOwner(stadiumOwnerId));

        if (BCrypt.checkpw(password, stadiumOwner.get().getPassword())) {
            return stadiumOwner;
        }

        return Optional.empty();
    }
}
