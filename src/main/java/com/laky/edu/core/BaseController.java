package com.laky.edu.core;

import com.laky.edu.organization.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 95 on 2017/11/14.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    public static Map<String,User> userSession = new HashMap<>();

    /**
     * 正确返回数据
     * @param object
     * @return
     */
    public Map doWrappingData(Object object){
        Map map = new LinkedHashMap<>();
        map.put("code",200);
        map.put("message","成功");
        map.put("data",object);
        return map;
    }

    /**
     * 创建token
     * @return
     */
    public String createToken(){
        String token = UUID.randomUUID ().toString ().replace ("-", "");
        return token;
    }

    /**
     * 异常返回数据
     * @param e
     * @return
     */
    public Map doWrappingErrorData(Exception e){
        e.printStackTrace();
        logger.error("出错了",e);
        Map map = new LinkedHashMap<>();
        map.put("code",500);
        map.put("message","失败");
        map.put("data",e.getMessage());
        return map;
    }

    /**
     * 获取IP
     * @param request
     * @return
     */
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 封装表单数据
     * @param request
     * @param parameter
     * @return
     */
    public LinkedHashMap doWrappingFormParameter(HttpServletRequest request,LinkedHashMap parameter){
        Enumeration pNames=request.getParameterNames();
        while(pNames.hasMoreElements()){
            String name=(String)pNames.nextElement();
            String value=request.getParameter(name);
            if (!StringUtils.isEmpty(value)) {
                parameter.put(name,value);
            }
        }
        return parameter;
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public User getCurrentUser(HttpServletRequest request){
       return this.userSession.get(request.getHeader("token"));
    }
}
