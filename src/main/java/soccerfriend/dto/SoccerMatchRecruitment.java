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

    int setNumber;

    int setTime;

    int stadiumId;

    int clubAId;

    Integer clubBId;

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
