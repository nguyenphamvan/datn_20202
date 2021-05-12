package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "SELECT * FROM address AS sp WHERE sp.addr_default = 1 AND users_id = :usersId", nativeQuery = true)
    Address findByAddrDefaultIsTrue(@Param("usersId") long usersId);
    List<Address> findAllByUserId(long userId);
    @Modifying
    @Query(value = "update address as sp set sp.addr_default = 0 where id != :addressId", nativeQuery = true)
    void setAddressDefault(@Param("addressId") Long addressId);
}
