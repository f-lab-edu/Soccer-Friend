package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerMatch {

    private int id;

    private int clubAScore;

    private int clubBScore;

    private int soccerMatchRecruitmentId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
