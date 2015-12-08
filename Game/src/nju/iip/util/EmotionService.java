package nju.iip.util;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EmotionService {
	
	
	
	public static String getEmotion(String picUrl) {
		String result = "";
		HttpClient httpclient = HttpClients.createDefault();
		 try{
		        URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/emotion/v1.0/recognize");

		        //builder.setParameter("faceRectangles", "{string}");

		        URI uri = builder.build();
		        HttpPost request = new HttpPost(uri);
		        request.addHeader("Content-Type", "application/json");
		        request.addHeader("Ocp-Apim-Subscription-Key", "b266b468af0d48359f3f2ae3be3cb546");

		        // Request body
		        StringEntity reqEntity = new StringEntity("{\"url\": \""+picUrl+"\"}");
		        request.setEntity(reqEntity);

		        HttpResponse response = httpclient.execute(request);
		        HttpEntity entity = response.getEntity();

		        if (entity != null) 
		        {
		            result = EntityUtils.toString(entity);
		        }
		    }
		    catch (Exception e)
		    {
		        System.out.println(e.getMessage());
		    }
		 return result;
	}
	
	public static void main(String[] args) {
		System.out.println(EmotionService.getEmotion("http://114.212.80.14/Pictures/images/upload/65002face_emotion_2015-11-17_01_34_53.jpg"));
	}

   

}
