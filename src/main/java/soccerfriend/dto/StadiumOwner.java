package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StadiumOwner {

    private int id;

    private String stadiumOwnerId;

    private String password;

    private String representative;

    private String companyName;

    private String address;

    private String taxpayerId;

    private int accountBankId;

    private String accountNumber;

    private int point;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public StadiumOwner(String stadiumOwnerId, String password, String representative, String companyName, String address, String taxpayerId, int accountBankId, String accountNumber) {
        this.stadiumOwnerId = stadiumOwnerId;
        this.password = password;
        this.representative = representative;
        this.companyName = companyName;
        this.address = address;
        this.taxpayerId = taxpayerId;
        this.accountBankId = accountBankId;
        this.accountNumber = accountNumber;
    }
}
