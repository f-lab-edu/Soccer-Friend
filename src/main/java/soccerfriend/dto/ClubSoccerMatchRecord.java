package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubSoccerMatchRecord {

    private int id;

    private int clubId;

    private int win;

    private int draw;

    private int lose;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public ClubSoccerMatchRecord(int clubId, int win, int draw, int lose) {
        this.clubId = clubId;
        this.win = win;
        this.draw = draw;
        this.lose = lose;
    }
}
