package com.nguyenpham.oganicshop.controller.user;

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

    @GetMapping("/collections.html")
    public String getListProductByCategoryUrl(@RequestParam(value = "category", required = false, defaultValue = "") String categoryUrl,
                                              @RequestParam(value = "supplier", required = false, defaultValue = "") String supplier, Model model) {
        model.addAttribute("category", categoryUrl);
        model.addAttribute("supplier", supplier);
        return "user/collection1";
    }

    @GetMapping("/products/{productUrl}.html")
    public String getProductDetail(@PathVariable("productUrl") String productUrl, Model model) {
        model.addAttribute("productUrl", productUrl);
        return "user/product";
    }
}
