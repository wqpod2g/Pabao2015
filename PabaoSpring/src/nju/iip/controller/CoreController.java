package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.iip.service.CoreService;
import nju.iip.util.SignUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 服务器配置以及微信会话相关控制类
 * @author wangqiang
 *
 */
@Controller
public class CoreController {

	private static final Logger logger = LoggerFactory.getLogger(CoreController.class);

	/**
	 * 确认请求来自微信服务器
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/Validate.do",method = RequestMethod.GET)
	public void validateServer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.flush();
		out.close();
	}

	/**
	 * 响应用户的消息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/Validate.do",method = RequestMethod.POST)
	public void MessageService(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("receive message or envnet");
		Long start = System.currentTimeMillis();
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(request);
		Long end = System.currentTimeMillis();
		logger.info("message process cost:" + (end - start) + "ms");

		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.flush();
		out.close();
	}

}
