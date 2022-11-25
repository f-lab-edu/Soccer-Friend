package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import soccerfriend.dto.Bulletin;

import java.util.List;

@Mapper
public interface BulletinMapper {
    public void insert(Bulletin bulletin);

    public void delete(int id);

    public void deletePermanently(int id);

    public boolean isIdExist(int id);

    public boolean isIdExistIncludeDeleted(int id);

    public boolean isNameExist(@Param("clubId") int clubId, @Param("name") String name);

    public Bulletin getBulletinById(int id);

    public List<Bulletin> getBulletinsByClubId(int clubId);
}
