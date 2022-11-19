package soccerfriend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String FROM_ADDRESS;

    @Autowired
    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String FROM_ADDRESS) {
        this.mailSender = mailSender;
        this.FROM_ADDRESS = FROM_ADDRESS;
    }

    /**
     * 이메일 인증을 위한 인증코드를 전송합니다.
     *
     * @param code        인증코드
     * @param destination 수신 email
     */
    public void sendAuthorizationCode(String code, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(destination);
        message.setSubject("SoccerFriend 이메일 인증번호");
        message.setText("인증번호는 " + code + "입니다.");

        mailSender.send(message);
    }

    /**
     * 아이디 찾기 과정에서 memberId를 이메일로 전송합니다.
     * 이메일로 전송되는 아이디 뒤 4자리는 *로 대체됩니다.
     *
     * @param memberId    아이디를 찾고자 하는 memberId
     * @param destination 수신 email
     */
    public void sendMemberId(String memberId, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();
        String secretId = memberId;
        int len = secretId.length();

        String prefix = secretId.substring(0, len - 4);
        secretId = prefix + "****";

        message.setFrom(FROM_ADDRESS);
        message.setTo(destination);
        message.setSubject("SoccerFriend 아이디 찾기");
        message.setText("아이디는 " + secretId + "입니다.");

        mailSender.send(message);
    }

    /**
     * 임시 비밀번호를 전송합니다.
     *
     * @param password    임시 비밀번호
     * @param destination 수신 email
     */
    public void sendTemporaryPassword(String password, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(destination);
        message.setSubject("SoccerFriend 임시 비밀번호");
        message.setText("임시비밀번호는 " + password + "입니다.");

        mailSender.send(message);
    }
}