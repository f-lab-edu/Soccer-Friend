package soccerfriend.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    public boolean checkPassword(String plain, String hashed){
        return BCrypt.checkpw(plain, hashed);
    }

    public String encrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
