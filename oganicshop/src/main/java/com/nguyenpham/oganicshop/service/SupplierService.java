package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier findSupplierByName(String supplierName);
}
