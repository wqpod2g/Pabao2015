package nju.iip.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nju.iip.dao.LocationDao;
import nju.iip.dao.UserDao;
import nju.iip.dto.UserLocation;
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
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/nearby_show")
	public String showNearby(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("showNearby called");
		OAuthService.getUerInfo(request);// 获取用户信息
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		String openId = snsUserInfo.getOpenId();
		List<UserLocation> list = locationDao.getAllUserLocation();
		JSONObject outjson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject center = null;
		for(UserLocation location:list) {
			WeixinUser user = userDao.getUser(location.getOpenId());
			if(user==null) {
				continue;
			}
			JSONObject json = JSONObject.fromObject(location);
			json.put("nickname", user.getNickname());
			json.put("headImgUrl",user.getHeadImgUrl());
	        if(openId.equals(location.getOpenId())) {
	        	center = json;
			}
			jsonArray.add(json);
		}
		outjson.put("location", jsonArray);
		outjson.put("center", center);
		logger.info(outjson.toString());
		request.setAttribute("location_json",outjson.toString());
		return "map.jsp";
	}
	
	public static void main(String[] args) {
	}

}
