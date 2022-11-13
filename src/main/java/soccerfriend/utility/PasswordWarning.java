package soccerfriend.utility;

public enum PasswordWarning {
    NO_WARNING(0),
    PASSWORD_FIND(1),
    WRONG_PASSWORD(2),
    OLD_PASSWORD(3);

    PasswordWarning(int code) {
        this.code = code;
    }

    int code;

    public int getCode() {
        return code;
    }
}
