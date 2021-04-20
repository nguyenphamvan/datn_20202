package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressRequestDto implements Comparable<AddressRequestDto>{

    private Long id;
    private String contactReceiver;
    private String contactPhone;
    private String contactAddress;
    private boolean isDefault;

    public AddressRequestDto(String contactReceiver, String contactPhone, String contactAddress) {
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
    }

    public AddressRequestDto(String contactReceiver, String contactPhone, String contactAddress, boolean isDefault) {
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
        this.isDefault = isDefault;
    }

    public AddressRequestDto(Long id, String contactReceiver, String contactPhone, String contactAddress, boolean addrDefault) {
        this.id = id;
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
        this.isDefault = addrDefault;
    }

    @Override
    public int compareTo(AddressRequestDto o) {
        return o.getId().compareTo(this.getId());
    }
}
