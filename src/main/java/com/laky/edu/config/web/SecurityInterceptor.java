package com.laky.edu.config.web;

import com.laky.edu.core.BaseController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截监听
 * Created by 湖之教育工作室·laky on 2017/11/22.
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        HttpSession session = request.getSession();
//        String uri =  request.getRequestURI();
        //第一步获取token
        System.out.println(request.getMethod());
        if( "OPTIONS".equals(request.getMethod())) { //跨域检测方法
            return true;
        }
        String token =  request.getHeader("token");
        if (BaseController.userSession.get(token) != null) { //登录
            return true;
        }
        String res ="{\"code\":420,\"message\":\"登录过期\",\"data\":\"登录过期，请重新登录！\"}";
        // 跳转登录
//        String url = request.getContextPath()+"/login.html";
//        response.sendRedirect(url); .AppendHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(420);
        response.getWriter().print(res);
        return false;
    }
}

