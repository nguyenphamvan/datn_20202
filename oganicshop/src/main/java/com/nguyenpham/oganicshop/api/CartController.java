package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.BaseResponse;
import com.nguyenpham.oganicshop.dto.CartItemDto;
import com.nguyenpham.oganicshop.entity.CartItem;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getListProductInCart(HttpSession session) {
        BaseResponse br = new BaseResponse();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        if (cart != null && cart.size() > 0) {
            List<CartItemDto> listItem = new ArrayList<>(cart.values().stream().map(item -> item.convertDto()).collect(Collectors.toList()));
            br.setStatus(true);
            br.setData(listItem);
        } else {
            br.setData(null);
            br.setErrMessage("Không có sản phẩm trong giỏ hàng");
            br.setStatus(false);
        }

        return new ResponseEntity<Object>(br, HttpStatus.OK);
    }

    @PostMapping("/quick-addToCart/{productId}")
    public ResponseEntity<?> quickAddProductToCart(HttpSession session, @PathVariable("productUrl") String productUrl) {
        BaseResponse br = new BaseResponse();
        boolean result = cartService.addItemCart(session, productUrl, 1);
        if (!result) {
            br.setErrMessage("Không đủ số lượng cung cấp");
        } else {
            br.setStatus(result);
            br.setData("/cart.html");
        }
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(HttpSession session, @RequestBody ObjectNode object) {
        BaseResponse br = new BaseResponse();
        String productUrl = object.get("productUrl").asText();
        int quantity = object.get("quantity").asInt();
        boolean result = cartService.addItemCart(session, productUrl, quantity);
        br.setStatus(result);
        if (!result) {
            br.setErrMessage("Không đủ số lượng cung cấp");
        }
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editProductInCart(HttpSession session, @RequestBody ObjectNode object) {
        BaseResponse br = new BaseResponse();
        String productUrl = object.get("url").asText();
        String changeMethod = object.get("changeMethod").asText();
        boolean result = cartService.editItemCart(session, productUrl, changeMethod);
        br.setStatus(result);
        if (!result) {
            br.setErrMessage("Không đủ số lượng cung cấp");
        }
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductOutCart(HttpSession session, @PathVariable("productUrl") String productUrl) {
        BaseResponse br = new BaseResponse();
        boolean result = cartService.removeItemCart(session, productUrl);
        int numberItemInCart = getTotalPrice((HttpSession) session);
        br.setStatus(result);
        br.setData(Integer.valueOf(numberItemInCart));
        if (!result) {
            br.setErrMessage("Có lỗi xảy ra");
        }
        return new ResponseEntity<>(br, HttpStatus.OK);
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

    @GetMapping("/load-info-cart")
    public ResponseEntity<?> getNumberOfProductsInCart(HttpSession session) {
        BaseResponse br = new BaseResponse();
        HashMap<Long, CartItem> cart = (HashMap<Long, CartItem>) session.getAttribute(Constant.CART_SESSION_NAME);
        Map<String, Object> infoCart = new HashMap<>();
        if (cart == null) {
            br.setData(null);
        } else {
            List<CartItem> listItem = new ArrayList<>(cart.values());
            if (listItem.size() == 0) {
                br.setData(null);
            } else {
                infoCart.put("totalPriceCart", cartService.totalSubCart(cart));
                infoCart.put("numberOfProducts", cartService.numberOfProductsInCart(cart));
                infoCart.put("listItemCart", listItem);
                br.setData(infoCart);
            }
        }
        br.setStatus(true);
        return new ResponseEntity<Object>(br, HttpStatus.OK);
    }
}
