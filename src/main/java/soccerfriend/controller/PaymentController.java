package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.service.PaymentService;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService tossPaymentService;

    /**
     * 결제대행사로 부터 결제승인을 받은 후 처리하는 로직입니다.
     */
    @RequestMapping("/success")
    public String confirmPayment(@RequestParam Map<String, Object> req) throws Exception {
        String paymentKey = (String) req.get("paymentKey");
        String orderId = (String) req.get("orderId");
        return tossPaymentService.pay(paymentKey, orderId);
    }

    /**
     * 결제대행사로 부터 결제승인을 요청 후 실패했을 때 처리하는 로직입니다.
     */
    @RequestMapping("/fail")
    public String failPayment(@RequestParam Map<String, Object> req) {
        return "결제에 실패했습니다.";
    }
}
