package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soccerfriend.dto.Address;
import soccerfriend.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService service;

    @GetMapping("/{city}")
    public List<Address> getAddressByCity(@PathVariable String city) {
        return service.getAddressByCity(city);
    }
}

