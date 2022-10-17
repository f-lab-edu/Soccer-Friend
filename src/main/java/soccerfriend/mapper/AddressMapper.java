package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.Address;

import java.util.List;

@Mapper
public interface AddressMapper {

    public List<Address> getAddressByCity(String city);
}
