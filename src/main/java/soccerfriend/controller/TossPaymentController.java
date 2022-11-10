package soccerfriend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import soccerfriend.dto.OrderInfo;
import soccerfriend.service.OrderInfoService;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/toss-payments")
public class TossPaymentController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderInfoService orderInfoService;

    @Value("${toss-payments.secret-key}")
    private String SECRET_KEY;

    /**
     * 토스페이먼츠로 부터 결제승인을 받은 후 처리하는 로직입니다.
     * 클라이언트 측에서 설정한 success-url에 해당하는 url로 설정되어있습니다.
     * 결제 승인을 받은 후 토스페이먼츠 측으로 승인 API를 요청합니다.
     * 승인 결과를 응답받고 성공하면 포인트를 충전하고 그렇지 않으면 fail을 return 합니다.
     */
    @RequestMapping("/success")
    public String confirmPayment(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount) throws Exception {
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity("https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            orderInfoService.paymentSubmitted(orderInfo.getId());
            return "success";
        }
        else {
            JsonNode failNode = responseEntity.getBody();
            return "fail";
        }
    }

    /**
     * 토스페이먼츠로 부터 결제승인을 요청 후 실패했을 때 처리하는 로직입니다.
     */
    @RequestMapping("/fail")
    public String failPayment(@RequestParam String message, @RequestParam String code) {
        return "fail";
    }
}
