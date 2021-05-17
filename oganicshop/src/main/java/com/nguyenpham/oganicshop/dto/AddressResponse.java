package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressResponse implements Comparable<AddressResponse>{

    private Long id;
    private String contactReceiver;
    private String contactPhone;
    private String contactAddress;
    private boolean isDefault;

    public AddressResponse(String contactReceiver, String contactPhone, String contactAddress) {
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
    }

    public AddressResponse(String contactReceiver, String contactPhone, String contactAddress, boolean isDefault) {
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
        this.isDefault = isDefault;
    }

    public AddressResponse(Long id, String contactReceiver, String contactPhone, String contactAddress, boolean addrDefault) {
        this.id = id;
        this.contactReceiver = contactReceiver;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
        this.isDefault = addrDefault;
    }

    @Override
    public int compareTo(AddressResponse o) {
        return o.getId().compareTo(this.getId());
    }
}
