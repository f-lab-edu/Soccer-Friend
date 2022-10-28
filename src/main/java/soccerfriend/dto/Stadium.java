package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {
    int id;

    String name;

    int stadiumOwnerId;

    String address;

    String phoneNumber;

    int priceDay;

    int priceNight;

    int priceWeekend;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @Builder

    public Stadium(String name, int stadiumOwnerId, String address, String phoneNumber, int priceDay, int priceNight, int priceWeekend) {
        this.name = name;
        this.stadiumOwnerId = stadiumOwnerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.priceDay = priceDay;
        this.priceNight = priceNight;
        this.priceWeekend = priceWeekend;
    }
}
