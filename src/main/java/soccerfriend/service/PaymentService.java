package soccerfriend.service;

public interface PaymentService {

    public String pay(String paymentKey, String orderId) throws Exception;
}
