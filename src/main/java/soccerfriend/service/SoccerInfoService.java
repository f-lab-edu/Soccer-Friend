package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Address;
import soccerfriend.dto.Positions;
import soccerfriend.dto.SoccerInfo;
import soccerfriend.mapper.AddressMapper;
import soccerfriend.mapper.MemberMapper;
import soccerfriend.mapper.PositionsMapper;
import soccerfriend.mapper.SoccerInfoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoccerInfoService {

    private final AddressMapper addressMapper;
    private final PositionsMapper positionMapper;
    private final SoccerInfoMapper soccerInfoMapper;
    private final MemberMapper memberMapper;


    public List<Address> getAddressByCity(String city){
        return addressMapper.getAddressByCity(city);
    }

    public List<Positions> getAllPositions(){
        return positionMapper.getAll();
    }

    public int insert(SoccerInfo soccerInfo){
        soccerInfoMapper.insert(soccerInfo);
        return soccerInfo.getId();
    }
}
