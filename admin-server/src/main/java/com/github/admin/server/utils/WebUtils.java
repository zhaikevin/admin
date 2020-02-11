package com.github.admin.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/27 15:04
 */
public final class WebUtils {

    private WebUtils() {

    }

    /**
     * 添加cookie
     * @param name
     * @param value
     * @param response
     */
    public static void addCookie(String name, String value, HttpServletResponse response) throws UnsupportedEncodingException {
        String encodeValue = URLEncoder.encode(value,"utf-8");
        Cookie cookie = new Cookie(name, encodeValue);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 清除cookie信息
     * @param request
     * @param response
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        // 清除cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }
}
