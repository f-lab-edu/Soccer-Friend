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

    public List<Address> getAddressByCity(String city){
        return mapper.getAddressByCity(city);
    }
}
