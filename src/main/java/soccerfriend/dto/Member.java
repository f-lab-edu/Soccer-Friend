package soccerfriend.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    int id;

    @NotNull
    @Size(min = 6, max = 18)
    String memberId;

    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")
    String password;

    @NotNull
    @Email
    String email;

    @NotNull
    @Size(min = 2, max = 8)
    String nickname;

    int point;

    int positionsId;

    int addressId;

    @Builder
    public Member(String memberId, String password, String email, String nickname, int positionsId, int addressId) {
        this.memberId = memberId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.positionsId = positionsId;
        this.addressId = addressId;
    }
}
