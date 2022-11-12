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

    public void sendAuthorizationCode(String code, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(destination);
        message.setSubject("SoccerFriend 이메일 인증번호");
        message.setText("인증번호는 " + code + "입니다.");

        mailSender.send(message);
    }

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
}