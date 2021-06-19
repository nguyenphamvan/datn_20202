package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.CartItem;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface CartService {

    boolean addItemCart(HttpSession session, String productUrl, int quantity);
    boolean editItemCart(HttpSession session, String productUrl, String changeMothod);
    boolean removeItemCart(HttpSession session, String productUrl);
    int totalSubCart(HashMap<Long, CartItem> cart);
    int numberOfProductsInCart(HashMap<Long, CartItem> cart);
}
