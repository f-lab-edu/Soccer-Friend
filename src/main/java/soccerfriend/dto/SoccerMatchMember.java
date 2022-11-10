package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerMatchMember {

    private int id;

    private int soccerMatchId;

    private int clubId;

    private int memberId;

    private boolean approved;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

    @Builder
    public SoccerMatchMember(int soccerMatchId, int clubId, int memberId, boolean approved) {
        this.soccerMatchId = soccerMatchId;
        this.clubId = clubId;
        this.memberId = memberId;
        this.approved = approved;
    }
}
