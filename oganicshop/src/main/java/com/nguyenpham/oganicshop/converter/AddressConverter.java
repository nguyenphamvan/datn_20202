package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.AddressRequest;
import com.nguyenpham.oganicshop.dto.AddressResponse;
import com.nguyenpham.oganicshop.entity.Address;

public class AddressConverter implements GeneralConverter<Address, AddressRequest, AddressResponse>{
    @Override
    public AddressResponse entityToDto(Address address) {
        return new AddressResponse(
                address.getId() ,
                address.getContactReceiver(),
                address.getContactPhone(),
                address.getContactAddress(),
                address.isAddrDefault()
        );
    }

    @Override
    public Address dtoToEntity(AddressRequest d) {
        return null;
    }
}
