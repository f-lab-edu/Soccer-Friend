package soccerfriend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soccerfriend.dto.OrderInfo;
import soccerfriend.exception.exception.BadRequestException;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static soccerfriend.exception.ExceptionInfo.TOSS_PAYMENT_FAIL;

@Service
public class TossPaymentService implements PaymentService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OrderInfoService orderInfoService;
    private final String SECRET_KEY;

    @Autowired
    public TossPaymentService(RestTemplate restTemplate, ObjectMapper objectMapper, OrderInfoService orderInfoService, @Value("${toss-payments.secret-key}") String SECRET_KEY) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.orderInfoService = orderInfoService;
        this.SECRET_KEY = SECRET_KEY;
    }

    /**
     * 토스페이먼츠로 부터 결제승인을 받은 후 처리하는 로직입니다.
     * 클라이언트 측에서 설정한 success-url에 해당하는 url로 설정되어있습니다.
     * 결제 승인을 받은 후 토스페이먼츠 측으로 승인 API를 요청합니다.
     * 승인 결과를 응답받고 성공하면 포인트를 충전하고 그렇지 않으면 fail을 return 합니다.
     */
    @Override
    public String success(Map<String, Object> req) throws Exception {

        String paymentKey = (String) req.get("paymentKey");
        String orderId = (String) req.get("orderId");
        Long amount = Long.parseLong((String) req.get("amount"));

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
            String code = failNode.get("code").asText();
            String message = failNode.get("message").asText();
            return code + " " + message;
        }
    }

    /**
     * 토스페이먼츠로 부터 결제과정에서 실패했을 때 처리하는 로직입니다.
     */
    @Override
    public String fail(Map<String, Object> req) {
        return "fail";
    }
}
