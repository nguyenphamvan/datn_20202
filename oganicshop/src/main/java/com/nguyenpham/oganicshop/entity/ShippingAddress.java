package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.AddressRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shipping_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactReceiver;
    private String contactPhone;
    private String contactAddress;
    private boolean addrDefault;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

    public AddressRequestDto convertToDto() {
        return new AddressRequestDto(
                this.getId() ,
                this.getContactReceiver(),
                this.getContactPhone(),
                this.getContactAddress(),
                this.isAddrDefault()
        );
    }

}
