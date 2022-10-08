package soccerfriend.service;

import org.springframework.http.ResponseEntity;
import soccerfriend.dto.LoginForm;

public interface LoginService {
    public static final String LOGIN_MEMBER = "loginMember";

    public ResponseEntity<Void> login(LoginForm loginForm);

    public void logout();
}
