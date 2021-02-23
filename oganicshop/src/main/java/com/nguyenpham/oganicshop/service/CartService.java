package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CartItem;

import java.util.HashMap;

public interface CartService {

    HashMap<Long, CartItem> addItemCart(HashMap<Long, CartItem> cart, String productUrl, int quantity);
    HashMap<Long, CartItem> editItemCart(HashMap<Long, CartItem> cart, String productUrl, String changeMothod);
    HashMap<Long, CartItem> removeItemCart(HashMap<Long, CartItem> cart, String productUrl);
    int totalSubCart(HashMap<Long, CartItem> cart);
    int numberOfProductsInCart(HashMap<Long, CartItem> cart);
}
