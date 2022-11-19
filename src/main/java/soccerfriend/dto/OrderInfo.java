package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo {

    private int id;

    private int memberId;

    private int amount;

    private String orderId;

    private String orderName;

    private boolean confirmed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public OrderInfo(int amount, String orderName) {
        this.amount = amount;
        this.orderName = orderName;
    }
}
