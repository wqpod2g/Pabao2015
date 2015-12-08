package nju.iip.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;

/**
 * 图灵机器人类
 * @author mrpod2g
 *
 */
public class RobotService {
	
	/**
	  * 获取图灵机器人的应答消息
	  * @param sendMessage
	  * @return
	  */
	 public static String getRobotReply(String message) {
		 String reply = "";
		 String APIKEY = "a8e003f7d75f73a175ba2d17e87ad083"; 
		 try {
			 String INFO = URLEncoder.encode(message, "utf-8"); 
	         String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO; 
	         URL getUrl = new URL(getURL); 
	         HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
	         connection.connect(); 
	         // 取得输入流，并使用Reader读取 
	         BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8")); 
	         StringBuffer sb = new StringBuffer(); 
	         String line = ""; 
	         while ((line = reader.readLine()) != null) { 
	             sb.append(line); 
	         } 
	         reader.close(); 
	         // 断开连接 
	         connection.disconnect(); 
	         
	         reply = parseJson(sb.toString());
	         
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
        
		 return reply;
	 }
	 
	 
	 public static String parseJson(String jsonStr) {
		 String result = jsonStr;
		 try {
			 JSONObject json = new JSONObject(jsonStr);
			 int code = json.getInt("code");
			 if(code==100000) {
				 result = json.getString("text");
			 }
			 else if(code==200000) {
				 result = json.getString("text")+(":\n")+json.getString("url");
			 }
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 return result;
	 }

}
