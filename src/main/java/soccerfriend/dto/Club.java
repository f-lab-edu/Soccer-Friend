package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    private int id;

    private String name;

    private int leader;

    private int addressId;

    private int point;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Club(String name, int leader, int addressId) {
        this.name = name;
        this.leader = leader;
        this.addressId = addressId;
    }
}
