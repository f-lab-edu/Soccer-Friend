package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal {

    private int id;

    private int soccerMatchMemberId;

    private int setNumber;

    private int setTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Goal(int soccerMatchMemberId, int setNumber, int setTime) {
        this.soccerMatchMemberId = soccerMatchMemberId;
        this.setNumber = setNumber;
        this.setTime = setTime;
    }
}
