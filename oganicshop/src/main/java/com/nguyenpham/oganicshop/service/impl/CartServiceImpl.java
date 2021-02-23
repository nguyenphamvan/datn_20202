package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public HashMap<Long, CartItem> addItemCart(HashMap<Long, CartItem> cart, String productUrl, int quantity) {
        Product product = productRepository.findByProductUrl(productUrl).orElse(null);
        if (product != null) {
            CartItem item;
            if (cart.containsKey(product.getId())) {
                item = cart.get(product.getId());
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                item = new CartItem();
                item.setProduct(product);
                item.setQuantity(quantity);
            }
            cart.put(product.getId(), item);
        }
        return cart;
    }

    @Override
    public HashMap<Long, CartItem> editItemCart(HashMap<Long, CartItem> cart, String productUrl, String changeMothod) {
        Product product = productRepository.findByProductUrl(productUrl).orElse(null);
        if(product != null && cart.containsKey(product.getId())) {
            CartItem item = cart.get(product.getId());
            if(changeMothod.equals("plus")) {
                item.setQuantity(item.getQuantity() + 1);
            } else if (changeMothod.equals("minus")) {
                item.setQuantity(item.getQuantity() - 1);
            }
            cart.put(product.getId(), item);
        }
        return cart;
    }

    @Override
    public HashMap<Long, CartItem> removeItemCart(HashMap<Long, CartItem> cart, String productUrl) {
        Product product = productRepository.findByProductUrl(productUrl).orElse(null);
        if (cart.containsKey(product.getId())) {
            cart.remove(product.getId());
        }
        return cart;
    }

    @Override
    public int totalSubCart(HashMap<Long, CartItem> cart) {
        int total = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            total += item.getValue().caculateTotalItem();
        }
        return total;
    }

    @Override
    public int numberOfProductsInCart(HashMap<Long, CartItem> cart) {
        int numberOfProduct = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            numberOfProduct += item.getValue().getQuantity();
        }
        return numberOfProduct;
    }
}
