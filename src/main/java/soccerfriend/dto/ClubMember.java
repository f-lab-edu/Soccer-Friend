package soccerfriend.dto;

import lombok.*;
import soccerfriend.utility.validator.ValidEnum;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember {

    private int id;

    @NonNull
    @Min(1)
    private int clubId;

    @NonNull
    @Min(1)
    private int memberId;

    @NonNull
    @ValidEnum(enumClass = ClubMemberGrade.class)
    private ClubMemberGrade grade;

    private boolean approved;

    private boolean paymentStatus;

    private LocalDateTime paidAt;

    @Builder
    public ClubMember(int clubId, int memberId, ClubMemberGrade grade) {
        this.clubId = clubId;
        this.memberId = memberId;
        this.grade = grade;
    }

    public static enum ClubMemberGrade {
        LEADER,
        STAFF,
        MEMBER
    }
}
