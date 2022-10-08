package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    int id;

    String loginId;

    String password;

    String nickname;

    int point;

    int soccerInfoId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
