package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.repository.SupplierRepository;
import com.nguyenpham.oganicshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findSupplierByName(String supplierName) {
        return supplierRepository.findSupplierByName(supplierName);
    }

    @Override
    public List<Supplier> findSuppliersByCategory(String categoryUrl) {
        return supplierRepository.findAllByCategoryUrl(categoryUrl);
    }

    @Override
    public Supplier insertSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
}
