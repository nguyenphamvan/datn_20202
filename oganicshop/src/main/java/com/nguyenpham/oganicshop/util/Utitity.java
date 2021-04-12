package com.nguyenpham.oganicshop.util;

import javax.servlet.http.HttpServletRequest;

public class Utitity {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
