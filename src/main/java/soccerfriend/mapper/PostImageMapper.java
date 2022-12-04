package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.PostImage;

@Mapper
public interface PostImageMapper {

    public void insert(PostImage postImage);

    public PostImage getPostImageById(int id);

    public void delete(int id);
}
