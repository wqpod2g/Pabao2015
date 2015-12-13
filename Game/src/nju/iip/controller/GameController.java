package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import nju.iip.dao.ScoreDAO;
import nju.iip.dto.GameScore;
import nju.iip.dto.WeixinUser;
import nju.iip.redis.JedisPoolUtils;
import nju.iip.util.CommonUtil;
import nju.iip.util.Config;
import nju.iip.util.EmotionService;
import nju.iip.util.WeChatUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;

@Controller
public class GameController{
	

	private static final Logger logger = LoggerFactory
			.getLogger(GameController.class);

	@Autowired
	private ScoreDAO scoreDAO;

	@RequestMapping(value = "/CrazyFinger.do")
	public String CrazyFinger(HttpServletRequest request, Model model) {
		logger.info("CrazyFinger.do called");
		WeChatUtil.getUserInfo(request);
		model.addAttribute("ServerDomain", Config.getValue("ServerDomain"));
		model.addAttribute("appID", Config.getValue("appID"));
		return "index.jsp";
	}

	@RequestMapping(value = "/CrazyFinger1.do")
	public String CrazyFinger1(HttpServletRequest request, Model model) {
		logger.info("CrazyFinger1.do called");
		String code = request.getParameter("code");
		WeChatUtil.getUserInfo(request);
		// js签名
		String nonceStr_js = "iipGroup";
		String timestamp_js = (System.currentTimeMillis() / 1000) + "";
		String url = Config.getValue("ServerDomain")+"/Game/CrazyFinger1.do?code="
				+ code + "&state=CrazyFinger1";
		String signature_js = WeChatUtil.getJsSignature(
				WeChatUtil.getAccessToken(), nonceStr_js, timestamp_js, url);
		model.addAttribute("nonceStr_js", nonceStr_js);
		model.addAttribute("timestamp_js", timestamp_js);
		model.addAttribute("signature_js", signature_js);
		model.addAttribute("appID", Config.getValue("appID"));
		model.addAttribute("ServerDomain", Config.getValue("ServerDomain"));
		return "CrazyFinger/CrazyFinger.jsp";
	}

	@RequestMapping(value = "/CrazyFinger2.do")
	public String CrazyFinger2(HttpServletRequest request, Model model) {
		logger.info("CrazyFinger2.do called");
		String code = request.getParameter("code");
		WeChatUtil.getUserInfo(request);
		// js签名
		String nonceStr_js = "iipGroup";
		String timestamp_js = (System.currentTimeMillis() / 1000) + "";
		String url = Config.getValue("ServerDomain")+"/Game/CrazyFinger2.do?code="
				+ code + "&state=CrazyFinger2";
		String signature_js = WeChatUtil.getJsSignature(
				WeChatUtil.getAccessToken(), nonceStr_js, timestamp_js, url);
		model.addAttribute("nonceStr_js", nonceStr_js);
		model.addAttribute("timestamp_js", timestamp_js);
		model.addAttribute("signature_js", signature_js);
		model.addAttribute("appID", Config.getValue("appID"));
		model.addAttribute("ServerDomain", Config.getValue("ServerDomain"));
		return "CrazyFinger2/CrazyFinger2.jsp";
	}

	/**
	 * 保存用户游戏得分
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveScore.do")
	public void saveScore(GameScore gs, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		WeixinUser user = (WeixinUser) request.getSession().getAttribute(
				"snsUserInfo");
		String name = user.getNickname();
		String openId = user.getOpenId();
		gs.setNickname(name);
		gs.setHeadImgUrl(user.getHeadImgUrl());
		gs.setOpenId(openId);
		Jedis jedis = JedisPoolUtils.getInstance().getJedis();//jedis实例
		Double oldScore = null; //旧的分数
		if ((oldScore = jedis.zscore(gs.getGame(), openId)) == null) {
			if(!scoreDAO.checkIsExist(openId,gs.getGame())) {
				scoreDAO.addScore(gs);
			}
			jedis.zadd(gs.getGame(), gs.getScore(),gs.getOpenId());
			logger.info("新用户"+name+"分数"+gs.getScore()+"缓存redis成功");
		} else {
			if (gs.getScore() > oldScore) {
				logger.info(name + "分数增加为" + gs.getScore());
				scoreDAO.updateScore(gs);
				jedis.zadd(gs.getGame(), gs.getScore(),gs.getOpenId());
			}
		}
		Long rank = jedis.zrank(gs.getGame(), gs.getOpenId());//当前用户排名
		Long sum = jedis.zcard(gs.getGame());
		JedisPoolUtils.getInstance().returnRes(jedis);
		List<GameScore> list = scoreDAO.getAllScore(gs.getGame());
		JSONObject json = new JSONObject();
		DecimalFormat df1 = new DecimalFormat("0.0"); 
		json.put("rank", sum-rank);
		json.put("beat", df1.format(100.0*(rank)/(sum))+"%");
		json.put("score", list);
		logger.info("json=" + json.toString());
		PrintWriter pw = response.getWriter();
		pw.write(json.toString());
		pw.flush();
		pw.close();
	}

	@RequestMapping(value = "/ReceivePicture.do")
	public void receviePic(HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		String picture = request.getParameter("picture");
		//String filePath = "C:/Users/mrpod2g.IIP-VIDEO/Desktop/apache-tomcat-7.0.53/webapps/Game/";
		String filePath = System.getProperty("user.dir");
		logger.info("filePath=" + filePath);
		int Num = new Random().nextInt(100000);
		String picPath = CommonUtil.savePicture(picture, Num + "face_emotion",filePath);
		logger.info("picPath=" + picPath);
		String josonString = EmotionService.getEmotion(Config.getValue("ServerDomain")+"/Pictures/images/upload/"+ picPath);
		logger.info("josonString=" + josonString);
		PrintWriter out = response.getWriter();
		out.write(josonString);
		out.flush();
		out.close();
	}
}
