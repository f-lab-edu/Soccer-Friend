package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    public int insert(Post post);

    public Post getPostById(int id);

    public List<Post> getPostByBulletinPage(@Param("bulletinId") int bulletinId, @Param("start") int start);

    public int getPostCountFromBulletin(int bulletinId);

    public void increaseViews(int id);
}
