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
        return tossPaymentService.success(req);
    }

    /**
     * 결제대행사로 부터 결제승인을 요청 후 실패했을 때 처리하는 로직입니다.
     */
    @RequestMapping("/fail")
    public String failPayment(@RequestParam Map<String, Object> req) {
        return tossPaymentService.fail(req);
    }
}
