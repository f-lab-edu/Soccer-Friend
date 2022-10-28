package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerMatchRecruitment {

    int id;

    LocalDateTime startTime;

    LocalDateTime endTime;

    int setNumber;

    int setTime;

    int stadiumId;

    int club1Id;

    int club2Id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @Builder
    public SoccerMatchRecruitment(LocalDateTime startTime, LocalDateTime endTime, int setNumber, int setTime, int stadiumId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.setNumber = setNumber;
        this.setTime = setTime;
        this.stadiumId = stadiumId;
    }
}
