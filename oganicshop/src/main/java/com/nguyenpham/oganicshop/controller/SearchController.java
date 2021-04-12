package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.ProductService;
import com.nguyenpham.oganicshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    private CategoryService categoryService;
    private ProductService productService;
    private SupplierService supplierService;

    @Autowired
    public SearchController(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @GetMapping("/search.html")
    public String getViewSearch(
            @RequestParam("search") String search,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "sortBy", required = false, defaultValue = "productName") String filed,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "6") int pageSize,
            Model model) {

        Page<Product> page = productService.searchProductByKeyword(search, pageNum, pageSize, filed, sort);
        Set<Supplier> setSuppliers = new HashSet<>();
        Set<Category> setCategorySearch = new HashSet<>();
        for (Product p : page.getContent()) {
            setSuppliers.add(p.getSupplier());
            setCategorySearch.add(p.getCategory());
        }

        model.addAttribute("setCategorySearch", setCategorySearch);
        model.addAttribute("setSupplier", setSuppliers);
        model.addAttribute("search", search);
        model.addAttribute("listProduct", page.getContent());
        model.addAttribute("page", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortBy", filed);
        model.addAttribute("sort", sort);
        return "search";
    }
}
