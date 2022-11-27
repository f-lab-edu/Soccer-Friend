package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    public void insert(Comment comment);

    public void delete(int id);

    public void deletePermanently(int id);

    public Comment getCommentById(int id);

    public List<Comment> getCommentsByPostId(int postId);

    public boolean isCommentExistIncludeDeleted(int id);
}
