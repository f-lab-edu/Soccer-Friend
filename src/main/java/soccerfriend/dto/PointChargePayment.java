package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointChargePayment {

    private int id;

    private PayerType payerType;

    private int payerId;

    private int price;

    private Pg pg;

    private PaymentType paymentType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public PointChargePayment(int price, Pg pg, PaymentType paymentType) {
        this.price = price;
        this.pg = pg;
        this.paymentType = paymentType;
    }

    public enum PayerType {
        MEMBER, STADIUM_OWNER
    }

    public enum Pg {
        KG_INICIS, LG_UPLUS, NICE_IT
    }

    public enum PaymentType {
        CREDIT_CARD, TOSS, KAKAO_PAY, NAVER_PAY,
    }
}
