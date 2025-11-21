package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Exceptions.ApiException;
import com.example.App.Ecommerce.Model.Address;
import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Payload.AddressDto;
import com.example.App.Ecommerce.Repos.AddressRepo;
import com.example.App.Ecommerce.Repos.UserRepo;
import com.example.App.Ecommerce.Services.AddressService;
import com.example.App.Ecommerce.security.services.AuthUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressImpl implements AddressService
{
    private final AddressRepo addressRepo;
    private final AuthUtils authUtils;
    private final ModelMapper modelMapper;

    public AddressImpl(AddressRepo addressRepo, AuthUtils authUtils, ModelMapper modelMapper) {
        this.addressRepo = addressRepo;
        this.authUtils = authUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        User user = authUtils.getUser();
        Address address = modelMapper.map(addressDto, Address.class);

        address.setUser(user);
        Address addressRes = addressRepo.save(address);

        return modelMapper.map(addressRes, AddressDto.class);
    }

    @Override
    public AddressDto getAddress(Long addressId) {
        return modelMapper.map(addressRepo.findById(addressId), AddressDto.class);
    }

    @Override
    public String removeAddress(Long addressId) {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ApiException("Address Not Found"));

        addressRepo.delete(address);
        return "Address Deleted Successfully";
    }

    @Override
    public List<AddressDto> getAddressesByUser() {
        User user = authUtils.getUser();
        return addressRepo.findAllByUser(user)
                .stream().map(
                        address -> modelMapper.map(address, AddressDto.class)
                ).toList();
    }

    @Override
    public AddressDto editAddress(Long addressId, AddressDto addressDto) {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ApiException("Address Not Found"));

        // Map dto --> existing entity
        modelMapper.map(addressDto, address);

        Address saved = addressRepo.save(address);

        return modelMapper.map(saved, AddressDto.class);
    }
}
