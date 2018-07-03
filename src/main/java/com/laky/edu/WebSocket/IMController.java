package com.laky.edu.WebSocket;

import com.laky.edu.core.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/IM")
public class IMController extends BaseController{

    @RequestMapping("/sendMessage")
    public Map sendWebSocketMessage(String message,String sessionId){
        try {
            IMWebSocket.sendMsg(sessionId,message);
            return doWrappingData("发送成功");
        } catch (Exception e){
            return doWrappingErrorData(e);
        }
    }
}
