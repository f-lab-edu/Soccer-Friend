package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Address;
import soccerfriend.mapper.AddressMapper;

import java.util.List;

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
        return mapper.getAddressByCity(city);
    }
}
