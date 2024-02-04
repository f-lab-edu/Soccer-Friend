package soccerfriend.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    private int id;

    private int bulletinId;

    private int writer;

    private String title;

    private String content;

    private int views;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
