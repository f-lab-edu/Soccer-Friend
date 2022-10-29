package soccerfriend.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMonthlyFee {

    private int id;

    private int clubId;

    private int clubMemberId;

    private int price;

    private int year;

    private int month;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public ClubMonthlyFee(int clubId, int clubMemberId, int price, int year, int month) {
        this.clubId = clubId;
        this.clubMemberId = clubMemberId;
        this.price = price;
        this.year = year;
        this.month = month;
    }
}
