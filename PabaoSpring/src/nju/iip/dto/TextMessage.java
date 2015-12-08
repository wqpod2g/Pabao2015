package nju.iip.dto;

/** 
 * 文本消息类 
 *  
 * @author mrpod2g 
 */  
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String Content;  
    
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  
