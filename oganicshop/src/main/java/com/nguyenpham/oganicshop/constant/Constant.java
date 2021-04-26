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
        tmp.put(0, "Đang xử lý");
        tmp.put(1, "Đang vận chuyển");
        tmp.put(2, "Giao hàng thành công");
        tmp.put(3, "Đã hủy");
        tmp.put(4, "Giao hàng không thành công");
        MAP_ORDER_TRACKING_STATUS = Collections.unmodifiableMap(tmp);
    }

}
