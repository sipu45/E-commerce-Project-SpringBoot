package com.EcommerceProject.Service;

import com.EcommerceProject.Model.User;
import com.EcommerceProject.Payload.AddressDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService
{
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddress();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddress(User user);

    AddressDTO updateAddress(Long addressId, @Valid AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}
