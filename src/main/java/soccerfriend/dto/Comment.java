package soccerfriend.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    private int id;

    @NotNull
    @Min(1)
    private int postId;

    @NotNull
    @Min(1)
    private int writer;

    @NotNull
    private String content;

    @Getter
    public static class ContentInput{
        private String content;
    }
}
