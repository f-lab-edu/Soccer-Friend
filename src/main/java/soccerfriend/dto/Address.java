package soccerfriend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Address {

    int id;

    String city;

    String town;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
