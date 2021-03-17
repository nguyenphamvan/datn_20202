package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.CartItem;
import com.nguyenpham.oganicshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartControllerApi {

    private CartService cartService;

    @Autowired
    public CartControllerApi(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getListItemCart(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute("myCart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        List<CartItem> listItem = new ArrayList<>(cart.values());
        return new ResponseEntity<Object>(listItem, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemCart(HttpSession session, @RequestBody ObjectNode object) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        String productUrl = object.get("productUrl").asText();
        int quantity = object.get("quantity").asInt();
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartService.addItemCart(cart, productUrl, quantity);
        session.setAttribute(Constant.CART_SESSION_NAME, cart);
        session.setAttribute("subCart", cartService.totalSubCart(cart));
        List<CartItem> listItem = new ArrayList<>(cart.values());
        return new ResponseEntity<Object>(listItem, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editItemCart(HttpSession session, @RequestBody ObjectNode object) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        String productUrl = object.get("productUrl").asText();
        String changeMethod = object.get("changeMethod").asText();
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart = cartService.editItemCart(cart, productUrl, changeMethod);
        session.setAttribute(Constant.CART_SESSION_NAME, cart);
        session.setAttribute("subCart", cartService.totalSubCart(cart));
        List<CartItem> listItem = new ArrayList<>(cart.values());
        return new ResponseEntity<Object>(listItem, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{productUrl}")
    public ResponseEntity<?> removeItemCart(HttpSession session, @PathVariable("productUrl") String productUrl) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            cart = new HashMap<>();
        }
        try {
            cart = cartService.removeItemCart(cart, productUrl);
            session.setAttribute(Constant.CART_SESSION_NAME, cart);
            session.setAttribute("subCart", cartService.totalSubCart(cart));
            return new ResponseEntity<Object>(true, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/total-money-cart")
    public int getTotalPrice(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            return 0;
        }
        int totalPriceCart = cartService.totalSubCart(cart);
        return totalPriceCart;
    }

    @GetMapping("/size-cart")
    public int getCartSize(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart == null) {
            return 0;
        }
        int sizeCart = cartService.numberOfProductsInCart(cart);
        return sizeCart;
    }

    @GetMapping("/load-info-cart")
    public ResponseEntity<?> getNumberOfProductsInCart(HttpSession session) {
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        Map<String, Object> infoCart = new HashMap<>();
        if (cart == null) {
            infoCart.put("totalPriceCart", 0);
            infoCart.put("numberOfProducts", 0);
            infoCart.put("listItemCart", null);
        } else {
            int totalPriceCart = cartService.totalSubCart(cart);
            int numberOfProducts = cartService.numberOfProductsInCart(cart);
            List<CartItem> listItem = new ArrayList<>(cart.values());
            infoCart.put("totalPriceCart", totalPriceCart);
            infoCart.put("numberOfProducts", numberOfProducts);
            infoCart.put("listItemCart", listItem);
        }
        return new ResponseEntity<Object>(infoCart, HttpStatus.OK);
    }
}
