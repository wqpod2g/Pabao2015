package nju.iip.service;

import javax.servlet.http.HttpServletRequest;

import nju.iip.dao.UserDao;
import nju.iip.dto.WeixinOauth2Token;
import nju.iip.dto.WeixinUser;
import nju.iip.util.AdvancedUtil;
import nju.iip.util.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);
	
	private static UserDao UD = new UserDao();
	

	/**
	 * 获取用户信息
	 * @param request
	 */
	public static void getUerInfo(HttpServletRequest request) {
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		logger.info("code=" + code);
		
		boolean flag = false;//用户是否绑定
		
		// 用户同意授权
		if (!"authdeny".equals(code)) {
			//如果用户第一次进入或者accessToken已经无效则重新获取用户信息
			if(snsUserInfo==null) {
				logger.info("需要获取用户信息");
				// 获取网页授权access_token
				WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(Config.getValue("appID"), Config.getValue("appsecret"), code+"");
				String accessToken = weixinOauth2Token.getAccessToken();
				// 用户标识
				String openId = weixinOauth2Token.getOpenId();
				request.getSession().setAttribute("openId", openId);
				// 获取用户信息
				snsUserInfo = AdvancedUtil.getWeixinUserInfo(accessToken, openId);
				request.getSession().setAttribute("snsUserInfo", snsUserInfo);
				
				//检查用户是否已绑定
				flag = UD.checkBind(openId);
				if(!flag) {
					logger.info("欢迎来自"+snsUserInfo.getProvince()+" "+snsUserInfo.getCity()+"新用户:"+snsUserInfo.getNickname());
					UD.addUserInfo(snsUserInfo);
				}
			}
			else {
				logger.info("用户信息已经存在session中");
			}
		}

	}

}
