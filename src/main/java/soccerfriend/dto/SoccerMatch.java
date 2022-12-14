package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerMatch {

    private int id;

    private int hostClubScore;

    private int participationClubScore;

    private int soccerMatchRecruitmentId;

    private boolean submitted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
