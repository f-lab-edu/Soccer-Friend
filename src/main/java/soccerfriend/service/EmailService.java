package soccerfriend.service;

public interface EmailService {
    public void sendAuthorizationCode(String code, String destination);
}
