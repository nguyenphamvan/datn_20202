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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProductController {

    private CategoryService categoryService;
    private ProductService productService;
    private SupplierService supplierService;

    @Autowired
    public ProductController(CategoryService categoryService, ProductService productService, SupplierService supplierService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.supplierService = supplierService;
    }

//    @GetMapping("/collections.html")
//    public String getListProductByCategoryUrl(
//            @RequestParam(value = "category", required = false, defaultValue = "") String categoryUrl,
//            @RequestParam(value = "supplier", required = false, defaultValue = "") String supplierName,
//            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
//            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String filed,
//            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "6") int pageSize,
//            @RequestParam(value = "minPrice", required = false, defaultValue = "0") int minPrice,
//            @RequestParam(value = "maxPrice", required = false, defaultValue = "0") int maxPrice,
//            Model model) {
//        Page<Product> page = null;
//        if (categoryUrl.equals("") && !supplierName.equals("")) {
//            Supplier supplier = supplierService.findSupplierByName(supplierName);
//            page = productService.getProductsBySupplier(supplierName, minPrice, maxPrice, pageNum, pageSize, filed, sort);
//            Set<Category> setCategory = new HashSet<>();
//            for (Product p : page.getContent()) {
//                setCategory.add(p.getCategory());
//            }
//            model.addAttribute("setCategory", setCategory);
//            model.addAttribute("supplier", supplier);
//        } else if (!categoryUrl.equals("") && supplierName.equals("")) {
//            Category category = categoryService.getByCategoryUrl(categoryUrl);
//            page = productService.getProductsByCategory(categoryUrl, minPrice, maxPrice, pageNum, pageSize, filed, sort);
//            Set<Supplier> setSuppliers = new HashSet<>();
//            for (Product p : page.getContent()) {
//                setSuppliers.add(p.getSupplier());
//            }
//            model.addAttribute("setSupplier", setSuppliers);
//            model.addAttribute("category", category);
//
//        } else {
//            page = productService.getProductsByCategoryAndSupplier(categoryUrl, supplierName, minPrice, maxPrice, pageNum, pageSize, filed, sort);
//            Supplier supplier = supplierService.findSupplierByName(supplierName);
//            Category category = categoryService.getByCategoryUrl(categoryUrl);
//
//            model.addAttribute("supplier", supplier);
//            model.addAttribute("category", category);
//
//        }
//
////        model.addAttribute("listCategory", listCategory);
//        model.addAttribute("listProduct", page.getContent());
//        model.addAttribute("page", pageNum);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("pageSize", pageSize);
//        model.addAttribute("sortBy", filed);
//        model.addAttribute("sort", sort);
//        model.addAttribute("minPrice", minPrice);
//        model.addAttribute("maxPrice", maxPrice);
//        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
//            model.addAttribute("filterByPrice", true);
//        } else {
//            model.addAttribute("filterByPrice", false);
//        }
//        return "collection";
//    }

    @GetMapping("/collections.html")
    public String getListProductByCategoryUrl(@RequestParam(value = "category") String categoryUrl,
                                              @RequestParam(value = "supplier", required = false, defaultValue = "") String supplier, Model model) {
        model.addAttribute("category", categoryUrl);
        model.addAttribute("supplier", supplier);
        return "collection1";
    }

    @GetMapping("/products/{productUrl}.html")
    public String getProductDetail(@PathVariable("productUrl") String productUrl, Model model) {
        model.addAttribute("productUrl", productUrl);
        return "product";
    }
}
