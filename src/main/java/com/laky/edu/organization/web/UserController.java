package com.laky.edu.organization.web;

import com.laky.edu.config.web.WebSecurityConfig;
import com.laky.edu.core.BaseController;
import com.laky.edu.core.PageBean;
import com.laky.edu.log.bean.LoginLog;
import com.laky.edu.log.service.LoginLogService;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.Branch;
import com.laky.edu.organization.bean.Menu;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.OrganizationService;
import com.laky.edu.organization.service.RoleService;
import com.laky.edu.organization.service.SchoolZoneService;
import com.laky.edu.organization.service.UserService;
import com.laky.edu.util.MD5;
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
            super.addCurrentUser(request,user);
            loginLogService.insert(user,1,super.getRemortIP(request));
           // session.setAttribute(WebSecurityConfig.SESSION_KEY,map);
            //token里面
            return super.doWrappingData(map);
        } catch (Exception e){
            return super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/loginOut")
    public Map loginOut(HttpServletRequest request) {
        try {
            String token=request.getHeader("token");
            loginLogService.insert(getCurrentUser(request),2,super.getRemortIP(request));
            super.removeCurrentUser(request);
//            BaseController.userSession.remove(token);
            // 移除session
            return super.doWrappingData("success");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }

    }

    @GetMapping("/getUserMenu")
    public Map getUserMenu(HttpServletRequest request){
        try {
            return super.doWrappingData(userService.findUserMenuAll(getCurrentUser(request).getId()));
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
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,parameterMap.get("theType")==null?0:parameterMap.get("theType"),parameterMap.get("parentSchoolId")));//获取当前用户所在校区的id和下级校区的数组
            parameterMap.put("pageNum",pageNum);
            parameterMap.put("pageSize",pageSize);
            return super.doWrappingData(userService.findUserBySchoolId(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/getUserView/{id}")
    public Map getUserView(HttpServletRequest request,@PathVariable int id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap = super.doWrappingFormParameter(request,parameterMap);
            parameterMap.put("schoolZoneId",super.getSchoolIds(request,0));//获取当前用户所在校区的id和下级校区的数组
            parameterMap.put("id",id);
            return super.doWrappingData(userService.findUserById(parameterMap));
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
            parameterMap=super.doWrappingFormParameter(request,parameterMap);
            return super.doWrappingData(roleService.findRoleBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    @GetMapping("/getRoleListBySchoolZoneId/{schoolZoneId}")
    public Map getRoleListBySchoolZoneId(HttpServletRequest request,@PathVariable Integer schoolZoneId){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("schoolIds",new Integer[]{schoolZoneId});//获取当前用户所在校区的id和下级校区的数组
            parameterMap.put("branchId",getCurrentUser(request).getBranchId());
            return super.doWrappingData(roleService.findRoleBySchool(parameterMap));
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    /**
     * 创建用户
     * @param request
     * @param user 用户基础信息
     * @param roles 用户权限
     * @return
     */
    @PostMapping("/createUser")
    public Map createUser(HttpServletRequest request,User user,Integer [] roles){
        try {
            if(user.getId()==null){//保存
                user.setBranchId(getCurrentUser(request).getBranchId());
                user =userService.createUser(user,roles);
                super.handleOperate("创建员工", OrganizationConst.OPERATE_ADD,"员工姓名【"+user.getName()+"】",request);
            } else {
                user = userService.updateUser(user,roles);
                super.handleOperate("修改员工", OrganizationConst.OPERATE_UPDATE,"员工姓名【"+user.getName()+"】",request);
            }
            return super.doWrappingData(user);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    /**
     * 删除用户
     * @param request
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    public Map deleteUser(HttpServletRequest request,@PathVariable(required = true) Integer id){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",id);
            Map map = userService.findUserById(parameterMap);
            userService.doDeleteUser(id);
            super.handleOperate("删除用户",OrganizationConst.OPERATE_DELETE,"删除用户【"+map.get("name")+"】",request);
            return super.doWrappingData("操作成功");
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }

    /**
     * 修改用户在职离职状态
     * @param request
     * @return
     */
    @PutMapping("/updateUserQuitStatus")
    public Map updateUserQuitStatus(HttpServletRequest request,User user){
        try {
            String logTitle = "恢复入职";
            String logContent = "员工【"+user.getName()+"】恢复入职";
            if(user.getQuitStatus() == 2){//离职
                user.setQuitDate(new Date());
                logTitle = "离职";
                logContent = "员工【"+user.getName()+"】离职";
            }
            user=userService.updateUser(user,null);
            super.handleOperate(logTitle,OrganizationConst.OPERATE_UPDATE,logContent,request);
            return super.doWrappingData(user);
        } catch (Exception e) {
            return  super.doWrappingErrorData(e);
        }
    }
    @PutMapping("updateUserPassword")
    public Map updateUserPassword(HttpServletRequest request,@RequestParam String newpwd){
        try {
            User user = new User();
            user.setId(super.getCurrentUser(request).getId());
            user.setPassword(MD5.getMd5(newpwd));
            userService.updateUser(user,null);
            return super.doWrappingData("修改密码成功!");
        }catch (Exception e){
            return super.doWrappingErrorData(e);
        }
    }

    @PostMapping("/validateUserPassword")
    public Map validateUserPassword(HttpServletRequest request,@RequestParam String password){
        try {
            LinkedHashMap parameterMap = new LinkedHashMap();
            parameterMap.put("id",getCurrentUser(request).getId());
            parameterMap.put("password",MD5.getMd5(password));
            Map userMap = userService.findUserById(parameterMap);
            if (userMap==null){
                throw new Exception("密码错误!");
            }
            return super.doWrappingData("密码正确!");
        }catch (Exception e){
            return  super.doWrappingErrorData(e);
        }
    }

}
