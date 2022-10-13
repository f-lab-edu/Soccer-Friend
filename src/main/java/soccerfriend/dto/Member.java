package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    int id;

    String memberId;

    String password;

    String nickname;

    int point;

    int soccerInfoId;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @Builder
    public Member(String memberId, String password, String nickname) {
        this.memberId = memberId;
        this.password = password;
        this.nickname = nickname;
    }
}
