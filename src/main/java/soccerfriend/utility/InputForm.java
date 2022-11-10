package soccerfriend.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    /**
     * stadium의 정보를 변경하기 위해 입력해야 하는 값들
     */
    @Getter
    @NoArgsConstructor
    public static class UpdateStadiumRequest{
        String name;
        String address;
        String phoneNumber;
        int priceDay;
        int priceNight;
        int priceWeekend;
    }

    /**
     * soccerMatchRecruitment의 정보를 변경하기 위해 입력해야 하는 값들
     */
    @Getter
    @NoArgsConstructor
    public static class UpdateSoccerMatchRecruitmentRequest{
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startTime;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endTime;
        int setNumber;
        int setTime;
        int stadiumId;
    }
}
