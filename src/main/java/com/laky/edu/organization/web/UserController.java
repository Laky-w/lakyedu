package com.laky.edu.organization.web;

import com.laky.edu.config.web.WebSecurityConfig;
import com.laky.edu.core.BaseController;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.organization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@RestController
@RequestMapping("/organization")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;


    @PostMapping(value = "login")
    public Map login(@RequestParam String userName,@RequestParam String pwd,@RequestParam String serial, HttpSession session){
        try {
            User user=userService.loginUser(userName,pwd,serial);
            user.setPassword("");
            String token=createToken();
            Map map = new HashMap<>();
            map.put("token",token);
            map.put("userInfo",user);
            map.put("branch",organizationService.findBranchBySerialOrId(null,user.getBranchId()));
            userSession.put(token,user);
           // session.setAttribute(WebSecurityConfig.SESSION_KEY,map);
            //token里面
            return super.doWrappingData(map);
        } catch (Exception e){
            return super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/loginout")
    public Map loginout(HttpServletRequest request) {
        try {
            String token=request.getHeader("token");
            BaseController.userSession.remove(token);
            // 移除session
            return super.doWrappingData("success");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }

    }

    @GetMapping("/getMenu")
    public Map getMenu(HttpServletRequest request) {
        try {
            /*String token=request.getHeader("token");
            BaseController.userSession.remove(token);

            Map menuMap = new HashMap<>();
            menuMap.put("icon","el-icon-setting");
            menuMap.put("index","el-icon-setting");
            menuMap.put("title","系统");
            List menuList = new ArrayList<>();
            menuList.add(menuMap);*/
            List<Menu> menuList =userService.findMenuAll();
            return super.doWrappingData(menuList);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
