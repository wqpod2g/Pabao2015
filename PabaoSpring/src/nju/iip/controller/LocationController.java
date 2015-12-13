package nju.iip.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import nju.iip.dao.LocationDao;
import nju.iip.dto.WeixinUser;
import nju.iip.service.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 用户地理位置坐标处理
 * @author wangqiang
 *
 */
@Controller
public class LocationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private LocationDao locationDao;
	
	@RequestMapping(value = "/nearby_show")
	public String showNearby(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("showNearby called");
		OAuthService.getUerInfo(request);// 获取用户信息
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		String openId = snsUserInfo.getOpenId();
		List<HashMap<String,String>> list = locationDao.getAllUserLocation();
		JSONObject outjson = new JSONObject();
		HashMap<String,String> center = null;
		for(HashMap<String,String> location:list) {
			if(location.get("openId").equals(openId)) {
				center = location;
			}
		}
		outjson.put("location", list);
		outjson.put("center", center);
		logger.info(outjson.toString());
		request.setAttribute("location_json",outjson.toString());
		return "map.jsp";
	}
	
	public static void main(String[] args) {
	}

}
