package com.laky.edu.organization.web;

import com.laky.edu.config.web.WebSecurityConfig;
import com.laky.edu.core.BaseController;
import com.laky.edu.core.PageBean;
import com.laky.edu.log.bean.LoginLog;
import com.laky.edu.log.service.LoginLogService;
import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.organization.service.RoleService;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.organization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@RestController
@RequestMapping("/organization")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RoleService roleService;



    @PostMapping(value = "login")
    public Map login(@RequestParam String userName,@RequestParam String pwd,@RequestParam String serial, HttpServletRequest request){
        try {
            User user=userService.loginUser(userName,pwd,serial);
            user.setPassword("");
            String token=createToken();
            Map map = new HashMap<>();
            map.put("token",token);
            map.put("userInfo",user);
            map.put("branch",organizationService.findBranchBySerialOrId(null,user.getBranchId()));
            userSession.put(token,user);
            loginLogService.insert(user,1,super.getRemortIP(request));
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
            loginLogService.insert((User) userSession.get(token),2,super.getRemortIP(request));
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

    @PostMapping("/getUserList/{pageNum}/{pageSize}")
    public Map getUserList(HttpServletRequest request,@PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));//获取当前用户所在校区的id和下级校区的数组
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            return super.doWrappingData(userService.findUserBySchoolId(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getRoleList/{pageNum}/{pageSize}")
    public Map getRoleList(HttpServletRequest request,@PathVariable int pageNum, @PathVariable int pageSize){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",super.getSchoolIds(request));//获取当前用户所在校区的id和下级校区的数组
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            return super.doWrappingData(roleService.findRoleBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }


    @PostMapping("/createUser")
    public Map createUser(HttpServletRequest request,User user){
        try {
            user.setBranchId(getCurrentUser(request).getBranchId());
            return super.doWrappingData(userService.createUser(user));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
}
