package nju.iip.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import sun.misc.BASE64Decoder;

public class CommonUtil {
	
    
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
    public static String savePicture(String picture,String path) {
    	if(picture.contains("data:image")) {
    		int index = picture.indexOf(",");
    		picture = picture.substring(index+1);
    	}
    	String photoPath = path+"/images/upload/";
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
	            int num=new Random().nextInt(100000);//产生随机数
	            String picNameString = num+"_"+getTime().replace(" ", "_").replace(":", "_")+".jpg";
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
    
    
    

}
