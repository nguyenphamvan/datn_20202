package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.AddressRequestDto;
import com.nguyenpham.oganicshop.dto.AddressResponseDto;
import com.nguyenpham.oganicshop.entity.Address;

public class AddressConverter implements GeneralConverter<Address, AddressRequestDto, AddressResponseDto>{
    @Override
    public AddressResponseDto entityToDto(Address address) {
        return new AddressResponseDto(
                address.getId() ,
                address.getContactReceiver(),
                address.getContactPhone(),
                address.getContactAddress(),
                address.isAddrDefault()
        );
    }

    @Override
    public Address dtoToEntity(AddressRequestDto d) {
        return null;
    }
}
