package com.laky.edu.core;

import com.laky.edu.log.bean.OperateLog;
import com.laky.edu.log.service.OperateLogService;
import com.laky.edu.organization.OrganizationConst;
import com.laky.edu.organization.bean.SchoolZone;
import com.laky.edu.organization.bean.User;
import com.laky.edu.organization.service.SchoolZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 95 on 2017/11/14.
 */
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private SchoolZoneService schoolZoneService;
    @Autowired
    private OperateLogService operateLogService;


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
            String [] value=request.getParameterValues(name);
            if (value!=null && value.length==1) {
                if(!StringUtils.isEmpty(value[0])){
                    parameter.put(name,value[0]);
                }
            }else{
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

    /**
     * 获取当前用户所在校区的id和下级校区的数组
     * @param request
     * @return
     * @throws Exception
     */
    public Integer [] getSchoolIds(HttpServletRequest request) throws Exception{
        User user=this.getCurrentUser(request);
        user.getSchoolZoneId();
        SchoolZone schoolZone = schoolZoneService.querySchoolZoneAllBySchoolZoneId(user.getBranchId(),user.getSchoolZoneId(),0);
        Integer [] ids ;
        if(schoolZone.getChildrenList() != null && schoolZone.getChildrenList().size()>0) {
            ids = new Integer[schoolZone.getChildrenList().size()+1];
            ids[0] = schoolZone.getId();
            for (int i=0;i<schoolZone.getChildrenList().size();i++){
                SchoolZone tempSchoolZone = schoolZone.getChildrenList().get(i);
                ids[i+1]=tempSchoolZone.getId();
            }
        } else {
            ids = new Integer[]{schoolZone.getId()};
        }
        return  ids;
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
            operatelog.setBranchId(getCurrentUser(request).getBranchId());
            operateLogService.addOperateLog(operatelog);
        }  catch ( Exception e ){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }
}
