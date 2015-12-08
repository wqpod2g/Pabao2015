package nju.iip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

public class CommonUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）  
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
	
    
    
    /**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("连接超时：{}", ce);
		} catch (Exception e) {
			log.error("https请求异常：{}", e);
		}
		return jsonObject;
	}
	
	 /** 
     * 将javaBean转换成Map 
     * 
     * @param javaBean javaBean 
     * @return Map对象 
     */ 
    public static Map<String, String> toMap(Object javaBean) 
    { 
        Map<String, String> result = new HashMap<String, String>(); 
        Method[] methods = javaBean.getClass().getDeclaredMethods(); 

        for (Method method : methods) 
        { 
            try 
            { 
                if (method.getName().startsWith("get")) 
                { 
                    String field = method.getName(); 
                    field = field.substring(field.indexOf("get") + 3); 
                    field = field.toLowerCase().charAt(0) + field.substring(1); 

                    Object value = method.invoke(javaBean, (Object[])null); 
                    result.put(field, null == value ? "" : value.toString()); 
                } 
            } 
            catch (Exception e) 
            { 
            } 
        } 

        return result; 
    } 
    
    
 
    
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static String getAccessToken(String appid, String appsecret) {  
    	String accessToken = null;  
      
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
            	accessToken = jsonObject.getString("access_token");
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static String getTime() {
    	Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
    	String time = dateFormat.format(now);
    	return time;
    }
    
    /**
     * 解码图片
     * @param picture
     * @return 图片所在路径
     */
    public static String savePicture(String picture,String openId,String path) {
    	if(picture.contains("data:image")) {
    		int index = picture.indexOf(",");
    		picture = picture.substring(index+1);
    	}
    	String photoPath = path+"../Pictures/images/upload/";
    	BASE64Decoder decoder = new BASE64Decoder();  
	        try   
	        {  
	            //Base64解码  
	            byte[] b = decoder.decodeBuffer(picture);  
	            for(int i=0;i< b.length;++i)  
	            {  
	                if(b[i]< 0)  
	                {//调整异常数据  
	                    b[i]+=256;  
	                }  
	            }  
	            String picNameString = openId+"_"+getTime().replace(" ", "_").replace(":", "_")+".jpg";
	            FileOutputStream out = new FileOutputStream(new File(photoPath,picNameString));      
	            out.write(b);  
	            out.flush();  
	            out.close();  
	            return picNameString;  
	        }   
	        catch (Exception e)   
	        {  
	           e.printStackTrace();  
	           return "";
	        }  
	    }  
    
    
    
    public static void main(String[] args) {
    	
    	String line = "";
    	//从json.txt中把json内容取出来
    	try {
    		FileInputStream fs = new FileInputStream( "json.txt");
        	InputStreamReader is= new InputStreamReader(fs,"utf8" );
        	BufferedReader br= new BufferedReader(is);
        	line=br.readLine();
        	br.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    	//String ACCESS_TOKEN = getAccessToken("wxb4bb093136ffd648","0a513930a9e98380ec1b338f5cd47390");
    	url = url.replace("ACCESS_TOKEN", "1fYiNdw8BI-Kb95sfO3Ldsxnv5WwUGUa8HmlLvV0tVG7_qHZf7SZn7aOSj_VQwsqc7SXpTF5dSUwQxe_9bQXQoJw0bf5EnCf7fftVmN9VCs");
    	System.out.println(line);
    	//System.out.println(ACCESS_TOKEN);
    	JSONObject jsonObject = httpsRequest(url,"POST",line);
    	System.out.println("errmsg="+jsonObject.get("errmsg"));
    	System.out.println("errcode="+jsonObject.get("errcode"));
    }

}
