package com.nguyenpham.oganicshop.dto;

import com.nguyenpham.oganicshop.entity.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
    private int discount = 0;

    public int caculateTotalItem() {
        return this.product.getFinalPrice() * this.quantity;
    }
}
