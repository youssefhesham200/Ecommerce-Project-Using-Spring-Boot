package com.example.App.Ecommerce.Services;

import com.example.App.Ecommerce.Payload.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    AddressDto addAddress(AddressDto addressDto);
    AddressDto getAddress(Long addressId);
    String removeAddress(Long addressId);
    List<AddressDto> getAddressesByUser();
    AddressDto editAddress(Long addressId, AddressDto addressDto);
}
