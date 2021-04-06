package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    ShippingAddress findByAddrDefaultIsTrue();

    @Modifying
    @Query(value = "update shipping_address as sp set sp.addr_default = 0 where id != :addressId", nativeQuery = true)
    void setAddressDefault(@Param("addressId") Long addressId);
}
