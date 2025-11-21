package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Payload.AddressDto;
import com.example.App.Ecommerce.Services.AddressService;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class AddressController {
    private final AddressService addressService;

    AddressController(AddressService addressService) {this.addressService = addressService;}

    @PostMapping("address")
    ResponseEntity<AddressDto> addAddress(@RequestBody  AddressDto addressDto)
    {
        AddressDto addressDtoRes = addressService.addAddress(addressDto);
        return new ResponseEntity<>(addressDtoRes,HttpStatus.CREATED);
    }

    @DeleteMapping("address/{addressId}")
    ResponseEntity<String> removeAddress(@PathVariable Long addressId)
    {
        String removed = addressService.removeAddress(addressId);
        return new ResponseEntity<>("Address Removed", HttpStatus.OK);
    }

    @GetMapping("addresses")
    ResponseEntity<List<AddressDto>> getAddresses()
    {
        List<AddressDto> addressDtoList = addressService.getAddressesByUser();
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }

    @PutMapping("address/{addressId}")
    ResponseEntity<AddressDto> editAddress(@PathVariable Long addressId,
                                           @RequestBody  AddressDto addressDto)
    {
        AddressDto addressDtoRes = addressService.editAddress(addressId, addressDto);
        return new ResponseEntity<>(addressDtoRes,HttpStatus.OK);
    }

    @GetMapping("address/{addressId}")
    ResponseEntity<AddressDto> getAddress(@PathVariable Long addressId)
    {
        AddressDto addressDtoList = addressService.getAddress(addressId);
        return new ResponseEntity<>(addressDtoList, HttpStatus.OK);
    }
}
