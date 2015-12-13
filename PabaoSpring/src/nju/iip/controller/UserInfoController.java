package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.iip.dao.UserDao;
import nju.iip.dto.WeixinUser;
import nju.iip.service.OAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户信息相关控制类
 * @author wangqiang
 *
 */
@Controller
public class UserInfoController {
	
	@Autowired
	private UserDao userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	/**
	 * 用户信息修改
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/alterUserInfo")
	public void alterUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		logger.info("update name=" + name);
		logger.info("update phone=" + phone);
		snsUserInfo.setName(name);
		snsUserInfo.setPhone(phone);
		PrintWriter out = response.getWriter();
		if(userDao.updateUserInfo(snsUserInfo)) {
			out.write("修改成功！");
			logger.info("update ok");
		}
		else{
			out.write("修改失败！");
			logger.info("update falied!");
		}
	}
	
	/**
	 * 显示用户主页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/show_homepage")
	public String showHomePage(HttpServletRequest request) {
		logger.info("showHomePage called");
		OAuthService.getUerInfo(request);// 获取用户信息
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		String openId = snsUserInfo.getOpenId();
		WeixinUser user = userDao.getUser(openId);
		request.getSession().setAttribute("user", user);
		return "MyHomePage.jsp";
	}

}
