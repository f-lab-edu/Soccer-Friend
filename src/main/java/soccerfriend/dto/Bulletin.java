package soccerfriend.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bulletin {

    private int id;

    private int clubId;

    @Size(min = 1, max = 16)
    private String name;

    @Builder
    public Bulletin(int clubId, String name) {
        this.clubId = clubId;
        this.name = name;
    }
}
