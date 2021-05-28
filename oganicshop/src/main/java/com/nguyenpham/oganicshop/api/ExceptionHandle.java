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
public class ExceptionHandle {

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
}
