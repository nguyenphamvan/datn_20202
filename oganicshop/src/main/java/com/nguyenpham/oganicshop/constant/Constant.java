package com.nguyenpham.oganicshop.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final String  CART_SESSION_NAME = "myCart";
    public static final int SHIP_FEE_STANDARD = 100;
    public static final String PAYMENT_METHOD_DEFAULT = "cod";
    public static final String PAYMENT_METHOD_STANDARD = "standard";
    public static final String MESSAGE_ORDER_SUCCESS = "Đặt hàng thành công, đơn hàng đang được xử lý!";

    public static final Map<Integer,String> MAP_ORDER_TRACKING_STATUS;
    static{
        HashMap<Integer,String> tmp = new HashMap<>();
        tmp.put(1, "Đặt hàng thành công");
        tmp.put(2, "Đã đóng gói xong");
        tmp.put(3, "Bàn giao vận chuyển");
        tmp.put(4, "Đang vận chuyển");
        tmp.put(5, "Giao hàng thành công");
        MAP_ORDER_TRACKING_STATUS = Collections.unmodifiableMap(tmp);
    }

}
