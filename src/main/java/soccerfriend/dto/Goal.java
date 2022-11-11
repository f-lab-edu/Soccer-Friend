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

    private int numSet;

    private int timeSet;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Goal(int soccerMatchMemberId, int numSet, int timeSet) {
        this.soccerMatchMemberId = soccerMatchMemberId;
        this.numSet = numSet;
        this.timeSet = timeSet;
    }
}
