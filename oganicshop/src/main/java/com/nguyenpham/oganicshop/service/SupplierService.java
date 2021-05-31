package com.nguyenpham.oganicshop.service;

import java.util.List;

public interface SupplierService {

    List<Supplier> getAll();
    Supplier findSupplierByName(String supplierName);
    List<Supplier> findSuppliersByCategory(String categoryUrl);
    Supplier insertSupplier(Supplier supplier);
}
