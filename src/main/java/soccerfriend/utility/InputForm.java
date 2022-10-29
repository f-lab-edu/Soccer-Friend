package soccerfriend.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InputForm {
    /**
     * password를 변경하기 위해 입력해야 하는 값들
     */
    @Getter
    @NoArgsConstructor
    public static class UpdatePasswordRequest {
        private String before;
        private String after;
    }

    /**
     * login 하기위해 입력해야 하는 값들
     */
    @Getter
    @NoArgsConstructor
    public static class LoginRequest {
        String id;
        String password;
    }

    /**
     * stadiumOwner의 정보를 변경하기위해 입력해야 하는 값들
     */
    @Getter
    @NoArgsConstructor
    public static class UpdateStadiumOwnerRequest {
        String representative;
        String companyName;
        String address;
        String taxpayerId;
        int accountBankId;
        String accountNumber;
    }
}
