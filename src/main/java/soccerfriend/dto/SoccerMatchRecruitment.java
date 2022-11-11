package soccerfriend.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoccerMatchRecruitment {

    int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime endTime;

    int numSet;

    int timeSet;

    int stadiumId;

    int hostClubId;

    Integer participationClubId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @Builder
    public SoccerMatchRecruitment(LocalDateTime startTime, LocalDateTime endTime, int numSet, int timeSet, int stadiumId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.numSet = numSet;
        this.timeSet = timeSet;
        this.stadiumId = stadiumId;
    }
}
