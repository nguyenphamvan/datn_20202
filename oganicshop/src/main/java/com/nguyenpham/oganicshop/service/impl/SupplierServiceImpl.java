package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.repository.SupplierRepository;
import com.nguyenpham.oganicshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier findSupplierByName(String supplierName) {
        return supplierRepository.findSupplierByName(supplierName);
    }
}
