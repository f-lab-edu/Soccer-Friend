package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Address;
import soccerfriend.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService service;

    @GetMapping("/{city}")
    public List<Address> getAddressByCity(@PathVariable String city) {
        System.out.println("city = " + city);
        return service.getAddressByCity(city);
    }
}

