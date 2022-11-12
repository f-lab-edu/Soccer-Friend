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

    String email;

    String nickname;

    int point;

    int positionsId;

    int addressId;

    @Builder
    public Member(String memberId, String password, String nickname, int positionsId, int addressId) {
        this.memberId = memberId;
        this.password = password;
        this.nickname = nickname;
        this.positionsId = positionsId;
        this.addressId = addressId;
    }
}
