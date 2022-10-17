package soccerfriend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Positions {

    int id;

    String formation;

    String detail;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
