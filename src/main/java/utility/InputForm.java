package utility;

import lombok.Getter;
import lombok.Setter;

public class InputForm {
    /**
     * password를 변경하기 위해 입력해야 하는 값들
     */
    @Getter
    @Setter
    public static class UpdatePasswordRequest {
        private String before;
        private String after;
    }

    /**
     * login 하기위해 입력해야 하는 값들
     */
    @Getter
    @Setter
    public static class LoginRequest {
        String id;
        String password;
    }
}
