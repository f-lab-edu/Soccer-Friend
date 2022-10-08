package soccerfriend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoccerInfo {

    int id;

    int positionsId;

    int addressId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}

