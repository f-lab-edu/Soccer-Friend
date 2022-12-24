package soccerfriend.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    private int id;

    private int postId;

    private String fileName;

    private String path;
}
