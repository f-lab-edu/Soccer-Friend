package soccerfriend.service;

import java.util.Map;

public interface PaymentService {
    public String success(Map<String, Object> req);

    public String fail(Map<String, Object> req);
}
