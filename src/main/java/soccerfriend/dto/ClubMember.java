package soccerfriend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember {

    private int id;

    private int clubId;

    private int memberId;

    private ClubMemberGrade grade;

    private boolean approved;

    private boolean paymentStatus;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
