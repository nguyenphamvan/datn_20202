package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    ShippingAddress findByAddrDefaultIsTrue();
}
