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

    private Category category;

    @Builder
    public Bulletin(int clubId, String name, Category category) {
        this.clubId = clubId;
        this.name = name;
        this.category = category;
    }

    public enum Category {
        GENERAL(0),
        PHOTO(1);

        Category(int code) {
            this.code = code;
        }

        int code;

        public int getCode() {
            return code;
        }
    }
}
