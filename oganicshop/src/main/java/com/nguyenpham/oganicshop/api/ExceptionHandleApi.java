package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.CategoryConverter;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.dto.ErrorMessage;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandleApi {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handeAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        ex.printStackTrace();
        return new ErrorMessage(10000, ex.getMessage());
    }

    /**
     * Tất cả các Validate được khai báo ở entity sẽ được xử lý ở đây
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @RestController
    public static class SearchControllerApi {

        private ProductService productService;

        @Autowired
        public SearchControllerApi(ProductService productService) {
            this.productService = productService;
        }

        @PostMapping("/api/search")
        public Object getProductsByKeyword(@RequestBody ObjectNode object) {

            String search = "";
            String sort = "asc";
            String filed = "finalPrice";
            int pageNum = 1;
            int pageSize = 6;
            if (object.has("keyword")) {
                search = object.get("keyword").asText();
            }
            if (object.has("sort")) {
                sort = object.get("sort").asText();
            }
            if (object.has("sortBy")) {
                filed = object.get("sortBy").asText();
            }
            if (object.has("page")) {
                pageNum = object.get("page").asInt();
            }
            if (object.has("pageSize")) {
                pageSize = object.get("pageSize").asInt();
            }

            CategoryConverter converter = new CategoryConverter();
            Map<String, Object> result = new HashMap<>();
            Page<Product> page = productService.getProductsByKeyword(search, pageNum, pageSize, filed, sort);
            Set<Supplier> setSuppliers = new HashSet<>();
            Set<CategoryDto> setCategorySearch = new HashSet<>();
            for (Product p : page.getContent()) {
//                setSuppliers.add(p.getSupplier());
                setCategorySearch.add(converter.entityToDto(p.getCategory()));
            }
            ProductConverter productConverter = new ProductConverter();
            List<ProductResponse> products = page.getContent().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());
            result.put("suppliers", setSuppliers);
            result.put("categories", setCategorySearch);
            result.put("products", products);
            result.put("page", pageNum);
            result.put("totalPages", page.getTotalPages());
            result.put("pageSize", pageSize);
            result.put("sortBy", filed);
            result.put("sort", sort);
            result.put("keyword", search);

            return result;
        }
    }
}
