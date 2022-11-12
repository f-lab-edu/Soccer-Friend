package soccerfriend.utility;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerator {

    /**
     * 이메일 인증코드 6자리를 생성합니다.
     *
     * @return 이메일 인증코드
     */
    public static String getEmailAuthorizationCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    /**
     * 주문번호를 생성하는 난수생성기입니다.
     *
     * @return 새롭게 생성된 주문번호
     */
    public static String getOrderIdRandomly() {
        int length = 32;
        String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuffer code = new StringBuffer();
        for (int i = 0; i < length; i++) {
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }
}
