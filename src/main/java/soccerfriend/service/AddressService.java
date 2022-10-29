package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Address;
import soccerfriend.exception.ExceptionInfo;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.AddressMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.ADDRESS_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressMapper mapper;

    /**
     * 특정 city에 존재하는 모든 Address를 제공합니다.
     *
     * @param city
     * @return city에 존재하는 모든 Address
     */
    public List<Address> getAddressByCity(String city) {
        List<Address> addressByCity = mapper.getAddressByCity(city);
        if(addressByCity.isEmpty()){
            throw new BadRequestException(ADDRESS_NOT_EXIST);
        }

        return addressByCity;
    }
}
