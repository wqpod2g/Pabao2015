package nju.iip.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nju.iip.dao.LocationDao;
import nju.iip.dto.TextMessage;
import nju.iip.dto.UserLocation;
import nju.iip.util.MessageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 核心服务类 
 *  
 * @author mrpod2g
 */  
public class CoreService {  
	
	private static final Logger logger = LoggerFactory.getLogger(CoreService.class);
	
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // 默认返回的文本消息内容  
            String respContent = "";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            logger.info("fromUserName="+fromUserName);
            
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            logger.info("toUserName="+toUserName);
            
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
            logger.info("msgType="+msgType);
            
  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
            	String Content = requestMap.get("Content");
            	logger.info("Content="+Content);
            	if(Content.contains("你是谁")) {
            		respContent = "我是帕宝机器人~非常高兴认识您/::)";
            	}
            	else {
            		 respContent = RobotService.getRobotReply(Content);
            	}
                logger.info("respContent="+respContent);
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
            	// 取得图片地址  
                String picUrl = requestMap.get("PicUrl");  
                logger.info("picUrl="+picUrl);
                // 人脸检测  
                String detectResult = FaceService.detect(picUrl);  
                respContent = detectResult; 
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "谢谢您的关注"+"/::)"+"\n"+"1.直接输入文字与我对话~\n2.发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey"); 
                    // 自定义菜单点击事件  
                    if(eventKey.equals("menu_3_3")) {
                    	respContent = "谢谢您的关注"+"/::)"+"\n"+"1.直接输入文字与我对话~\n2.发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息"; 
                    	//fromUserName就是openid
//                    	UserDaoImpl UDI = new UserDaoImpl();
//                    	if(UDI.deleteUser(fromUserName)) {
//                    		respContent = "解绑成功！"; 
//                    	}
//                    	else {
//                    		respContent = "解绑失败！"; 
//                    	}
                    }
                    else if(eventKey.equals("menu_1_3")) {
                    	respContent = "历史文章";                    }
                }  
                
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){
                	String Latitude = requestMap.get("Latitude");
                	String Longitude = requestMap.get("Longitude");
                	UserLocation location = new UserLocation();
                	location.setLatitude(Latitude);
                	location.setLongitude(Longitude);
                	location.setOpenId(fromUserName);
                	LocationDao LD = new LocationDao();
                	LD.updateUserLocation(location);
                	logger.info(fromUserName+"进入了公众号坐标为: "+Latitude+" "+Longitude);
                }
            }  
            textMessage.setContent(respContent);  
            respMessage = MessageUtil.textMessageToXml(textMessage);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage;  
    }  
}  
