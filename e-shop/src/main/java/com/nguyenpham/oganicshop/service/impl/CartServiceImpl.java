package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    public boolean addItemCart(HttpSession session, String productUrl, int quantity) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            cart = new HashMap<>();
        }
        Product product = productRepository.findByProductUrl(productUrl);
        if (product != null) {
            CartItem item;
            if (cart.containsKey(product.getId())) {
                if (!checkQuantityAvailable(quantity + cart.get(product.getId()).getQuantity(), product.getAmount())) {
                    return false;
                }
                item = cart.get(product.getId());
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                if (!checkQuantityAvailable(quantity , product.getAmount())) {
                    return false;
                }
                item = new CartItem();
                item.setProduct(product);
                item.setQuantity(quantity);
            }
            cart.put(product.getId(), item);
            session.setAttribute(Constant.CART_SESSION_NAME, cart);
            session.setAttribute("subCart", totalSubCart(cart));
            return true;
        }
        return false;
    }

    @Override
    public boolean editItemCart(HttpSession session, String productUrl, String changeMethod) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            return false;
        } else {
            Product product = productRepository.findByProductUrl(productUrl);
            if(product != null && cart.containsKey(product.getId())) {
                CartItem item = cart.get(product.getId());
                if(changeMethod.equals("plus")) {
                    if (!checkQuantityAvailable((item.getQuantity() + 1) + cart.get(product.getId()).getQuantity(), product.getAmount())) {
                        return false;
                    }
                    item.setQuantity(item.getQuantity() + 1);
                } else if (changeMethod.equals("minus")) {
                    if (item.getQuantity() - 1 > 0) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else {
                        return false;
                    }
                }
                cart.put(product.getId(), item);
                session.setAttribute(Constant.CART_SESSION_NAME, cart);
                session.setAttribute("subCart", totalSubCart(cart));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeItemCart(HttpSession session, String productUrl) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            return false;
        } else {
            Product product = productRepository.findByProductUrl(productUrl);
            if (cart.containsKey(product.getId())) {
                cart.remove(product.getId());
            }
            session.setAttribute(Constant.CART_SESSION_NAME, cart);
            session.setAttribute("subCart", totalSubCart(cart));
            return true;
        }
    }

    @Override
    public int totalSubCart(HashMap<Long, CartItem> cart) {
        int total = 0;
        for (HashMap.Entry<Long, CartItem> item : cart.entrySet()) {
            total += item.getValue().calculateTotalItem();
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

    public boolean checkQuantityAvailable(int quantity, int quantityAvailable) {
        if (quantity <= quantityAvailable) {
            return true;
        }
        return false;
    };
}
