package com.nguyenpham.oganicshop.entity;

import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.CartItemDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
    private double discount = 0;

    public double calculateTotalItem() {
        return this.product.getFinalPrice() * this.quantity;
    }

    public CartItemDto convertDto() {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setProduct(new ProductConverter().entityToDtoNotReviews(product));
        cartItem.setQuantity(this.getQuantity());
        cartItem.setDiscount(this.getDiscount());
        cartItem.setTotalItem(this.calculateTotalItem());
        return cartItem;
    }
}
