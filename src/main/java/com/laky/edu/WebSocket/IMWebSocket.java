package com.laky.edu.WebSocket;

import com.alibaba.fastjson.JSON;
import com.laky.edu.WebSocket.dto.MessageDto;
import com.laky.edu.core.RedisUtil;
import com.laky.edu.organization.bean.User;
import com.laky.edu.util.HttpUtil;
import com.laky.edu.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@ServerEndpoint(value = "/lakyIm/{token}")
@Component
public class IMWebSocket {

    private static Logger logger = LoggerFactory.getLogger(IMWebSocket.class);

    private static ApplicationContext applicationContext;

    private RedisUtil redisUtil;

    private final String ONLINE_USER_SET_KEY ="onlineUser_";

    /**
     * 用本地线程保存session
     */
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();

    /**
     *  保存所有连接上的session
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public IMWebSocket(){
        this.initService();
    }

    private void initService(){
        if(applicationContext!=null){
            this.redisUtil = applicationContext.getBean(RedisUtil.class);
        }
    }

    /**
     * 获取当前登录用户
     * @param token
     * @return
     */
    private User getCurrentUser(String token){
        if(StringUtils.isEmpty(token)){return null;}
        return JSON.parseObject(redisUtil.getData(token),User.class);
    }

    /**
     * 增加在线人员
     * @param user
     * @return
     */
    private Set<User> addOnlineUser(User user){
        return redisUtil.addSetData(ONLINE_USER_SET_KEY+user.getBranchId(),user);
    }

    /**
     * 移除在线人员
     * @param user
     * @return
     */
    private Set<User> removeOnlineUser(User user){
        return redisUtil.delSetData(ONLINE_USER_SET_KEY+user.getBranchId(),user);
    }

    /**
     * 打开连接
     * @param session
     */
    @OnOpen
    public void onOpen( @PathParam(value = "token") String token,Session session) {
        try {
            User user = this.getCurrentUser(token);
            sessionMap.put(session.getId(), session);
            if(user == null){
                sendMsg(session.getId(), "当前用户没有登录,请登录！");
                session.close();
            } else {
                Set<User> onlineUsers= addOnlineUser(user);
                InetAddress localHost = InetAddress.getLocalHost();
                //获取本机IP
                String localIP = localHost.getHostAddress().toString();
                Map webSocket = new HashMap(50);
                webSocket.put("localIP",localIP);
                webSocket.put("sessionId",session.getId());
                redisUtil.pullData("webSocketSession_"+user.getId(),JSON.toJSONString(webSocket),4L, TimeUnit.HOURS);

                //给客户端发现在线列表
                Map map = new HashMap(50);
                map.put("messageType","100");
                map.put("messageContent",onlineUsers);

                System.out.println("【" + user.getName() + "】连接上服务器======当前在线人数【" + onlineUsers.size() + "】");
                //连接上后给客户端发送在线用户列表
//                sendMsg(session.getId(), user.getName()+"连接服务器成功！");
                MessageDto messageDto = new MessageDto();
                messageDto.setMessageType("100");
                messageDto.setMessageContent(JSON.toJSONString(onlineUsers));
                sendMsg(session.getId(),JSON.toJSONString(messageDto));
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 关闭连接
     * @param session
     */
    @OnClose
    public void onClose(@PathParam(value = "token") String token, Session session) {
        if(!StringUtil.isEmpty(token)){
            User user = this.getCurrentUser(token);
            if(user!=null){
                Set<User> users=removeOnlineUser(user);
                redisUtil.delData("webSocketSession_"+user.getId());
                System.out.println("【" + user.getName() + "】退出了连接======当前在线人数【" + users.size() + "】");
            }
        }
        sessionMap.remove(session.getId());
    }

    /**
     * 接收消息   客户端发送过来的消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(@PathParam(value = "token") String token,String message, Session session) {
        MessageDto messageDto = JSON.parseObject(message,MessageDto.class);
        User user = this.getCurrentUser(token);
        Integer userId = Integer.valueOf(messageDto.getUserId());
        //获取发送用户的ip和sessionId
        String webSocketStr = redisUtil.getData("webSocketSession_"+userId);

        if(!StringUtil.isEmpty(webSocketStr)){
            Map webSocket = JSON.parseObject(webSocketStr,Map.class);

            Map paramMap = new HashMap(50);
            paramMap.put("sessionId",webSocket.get("sessionId"));
//            MessageDto messageDto = new MessageDto();
//            messageDto.setMessageContent(message);
//            messageDto.setMessageType("101");
            paramMap.put("message",JSON.toJSONString(messageDto));
            try {
                HttpUtil.doPostKey( "http://"+webSocket.get("localIP")+"/laky/IM/sendMessage",paramMap,token);
            } catch (Exception e){
                e.printStackTrace();
            }

        }

//        System.out.println("【" + session.getId() + "】客户端的发送消息======内容【" + message + "】");
//        String[] split = message.split(",");
//        String sessionId = split[0];
//        Session ss = sessionMap.get(sessionId);
//        if (ss != null) {
//            String msgTo = "【" + session.getId() + "】发送给【您】的消息:\n【" + split[1] + "】";
//            String msgMe = "【我】发送消息给【"+ss.getId()+"】:\n"+split[1];
//            sendMsg(ss, msgTo);
//            sendMsg(session,msgMe);
//        }else {
//            for (Session s : sessionMap.values()) {
//                if (!s.getId().equals(session.getId())) {
//                    sendMsg(s, "【" + session.getId() + "】发送给【您】的广播消息:\n【" + message + "】");
//                } else {
//                    sendMsg(session,"【我】发送广播消息给大家\n"+message);
//                }
//            }
//        }
    }

    /**
     * 异常
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("发生异常!");
        throwable.printStackTrace();
    }


    /**
     *  统一的发送消息方法
     */

    public static synchronized void sendMsg(String sessionId, String msg) {
        try {
            Session session = sessionMap.get(sessionId);
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        IMWebSocket.applicationContext = applicationContext;
    }
}
