package com.laky.edu.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laky.edu.log.bean.OperateLog;
import com.laky.edu.log.service.OperateLogService;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.SchoolZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 95 on 2017/11/14.
 */
@Component
@Controller
public class BaseController {
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private SchoolZoneService schoolZoneService;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private RedisUtil redisUtil;

    protected HttpServletRequest request;

    protected HttpServletResponse response;



//    public static Map<String,User> userSession = new HashMap<>();

    @InitBinder
    protected void initBinder(HttpServletRequest request,HttpServletResponse response,
                              ServletRequestDataBinder binder) throws Exception {
        this.request = request;
        this.response = response;
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//        binder.registerCustomEditor(Date.class,
//                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

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
            String [] value=request.getParameterValues(name);
            if (value!=null && value.length==1) {
                if(!StringUtils.isEmpty(value[0])){
                    parameter.put(name,value[0]);
                }
            }else{
                parameter.put(name,value);
            }
        }
        if(parameter.get("sort")!=null){ //自定义排序
            JSONObject sort= JSON.parseObject(parameter.get("sort").toString());
            if(sort!=null){
                String columnName=sort.get("prop").toString();
                /**
                 * ascending,descending
                 */
                String columnOrder = sort.get("order").toString();
                if(columnOrder.equals("ascending")){
                    columnOrder="asc";
                }
                if(columnOrder.equals("descending")){
                    columnOrder="desc";
                }
                parameter.put("columnName",columnName);
                parameter.put("columnOrder",columnOrder);
            }
        }
        return parameter;
    }

    /**
     * 获取当前登录用户
     * @param token
     * @param user
     */
    public void addCurrentUser(String token,User user){
        /**
         * 4小时
         */
        redisUtil.pullData(token,JSON.toJSONString(user),4L, TimeUnit.HOURS);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public User getCurrentUser(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){return null;}
        String str = redisUtil.getData(token);
        if(StringUtils.isEmpty(str)){return null;}
        User user =JSON.parseObject(str,User.class);
        return user;
    }

    /**
     * 获取当前登录用户
     * @param token
     * @return
     */
    public User getCurrentUser(String token){
        if(StringUtils.isEmpty(token)){return null;}
        return JSON.parseObject(redisUtil.getData(token),User.class);
    }

    public void removeCurrentUser(HttpServletRequest request){
        redisUtil.delData(request.getHeader("token"));
    }

    /**
     * 获取当前用户所在校区的下级校区和部门
     * @param request
     * @return
     * @throws Exception
     */
    public Integer [] getSchoolIds(HttpServletRequest request) throws Exception{
        return  getSchoolIds(request,0);
    }

    /**
     * 获取当前用户所在校区的下级校区或部门
     * @param request
     * @return 2 校区 3 部门
     * @throws Exception
     */
    public Integer [] getSchoolIds(HttpServletRequest request,Object theType) throws Exception{
        return  getSchoolIds(request,theType,null);
    }

    /**
     *  获取某校区的下级校区或部门
     * @param request
     * @param theType
     * @param parentSchoolId 校区id
     * @return
     * @throws Exception
     */
    public Integer [] getSchoolIds(HttpServletRequest request,Object theType,Object parentSchoolId) throws Exception{
        User user=this.getCurrentUser(request);
        SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),parentSchoolId == null ?user.getSchoolZoneId():Integer.parseInt(parentSchoolId.toString()),
                theType == null ?0:Integer.parseInt(theType.toString()));
        Integer [] ids ;
        if(schoolZone.getChildrenList() != null && schoolZone.getChildrenList().size()>0) {
            String idStr = getChildSchoolIds(schoolZone.getChildrenList());
            String [] idsStr = idStr.split(",");
            ids = new Integer[idsStr.length+1];
            ids[0] = schoolZone.getId();
            for(int i =0;i<idsStr.length;i++){
                ids[i+1]=new Integer(idsStr[i]);
            }
        } else {
            ids = new Integer[]{schoolZone.getId()};
        }
        return  ids;
    }

    /**
     * 递归获取子节点id
     * @param childList
     * @return
     */
    private String  getChildSchoolIds(List<SchoolZone> childList){
        StringBuilder id = new StringBuilder();
        for (SchoolZone schoolZone : childList){
            id.append(schoolZone.getId()+",");
            if(schoolZone.getChildrenList() !=null && schoolZone.getChildrenList().size()>0){
                id.append(getChildSchoolIds(schoolZone.getChildrenList()));
            }
        }
        return id.toString();
    }


    /**
     * 监听用户操作
     * @param title
     * @param type
     * @param content
     */
    public void handleOperate(String title,int type,String content,HttpServletRequest request){
        try {
            OperateLog operatelog = new OperateLog();
            operatelog.setContent(content);
            operatelog.setCreateTime(new Date());
            operatelog.setAccountId(getCurrentUser(request).getId());
            operatelog.setOperatePerson(getCurrentUser(request).getName());
            operatelog.setTheType(type);
            operatelog.setTitle(title);
            operatelog.setSchoolZoneId(getCurrentUser(request).getSchoolZoneId());
            //比如微信，邮箱，站内信提醒。
            operatelog.setBranchId(getCurrentUser(request).getBranchId());
            operateLogService.addOperateLog(operatelog);
        }  catch ( Exception e ){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }
}
