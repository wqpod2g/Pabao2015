package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.iip.dao.MessageDao;
import nju.iip.dto.Message;
import nju.iip.dto.WeixinUser;
import nju.iip.service.OAuthService;
import nju.iip.util.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 私信消息控制类
 * @author wangqiang
 *
 */
@Controller
public class MessageController {
	
	@Autowired
	private MessageDao messageDao;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@RequestMapping(value = "/ReceiveMessage")
	public void ReceiveMessage(Message message,HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("ReceiveMessage called");
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		message.setFromHeadImgUrl(user.getHeadImgUrl());
		message.setFromNickName(user.getNickname());
		message.setFromOpenId(user.getOpenId());
		message.setSendTime(CommonUtil.getTime());
		message.setIsRead(0);
		PrintWriter out = response.getWriter();
		if(messageDao.addMessage(message)) {
			out.write("发送消息成功!");
		}
		else {
			out.write("发送消息失败!");
		}
		out.flush();
		out.close();
	}
	

	@RequestMapping(value = "/show_messagebox")
	public String showMessageBox(HttpServletRequest request) {
		logger.info("show_messagebox called");
		OAuthService.getUerInfo(request);// 获取用户信息
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		String openId = user.getOpenId();
		List<Message> message_list = messageDao.getMessage(openId);
		request.setAttribute("message_list", message_list);
		return "MessageBox.jsp";
	}
	
	/**
	 * 标记消息为已读
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/tagRead")
	public void tagRead(HttpServletRequest request) {
		logger.info("tagRead called");
		String id = request.getParameter("id");
		logger.info("MessageId="+id);
		if(messageDao.updateIsRead(Integer.valueOf(id))) {
			logger.info("标记已读成功");
		}
		else {
			logger.info("标记已读失败");
		}
	}

}
