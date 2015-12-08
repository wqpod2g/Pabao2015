package nju.iip.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import nju.iip.dao.UserDao;
import nju.iip.dto.WeixinOauth2Token;
import nju.iip.dto.WeixinUser;

public class WeChatUtil {
	private static String accessToken = null;
	private static String jsapi_ticket = null;
	private static final Logger logger = LoggerFactory
			.getLogger(WeChatUtil.class);
	
	private static UserDao UD = new UserDao();

	public static void getUserInfo(HttpServletRequest request) {
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		WeixinUser snsUserInfo = (WeixinUser) request.getSession()
				.getAttribute("snsUserInfo");
		logger.info("code=" + code);

		boolean flag = false;// 用户是否绑定

		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 如果用户第一次进入或者accessToken已经无效则重新获取用户信息
			if (snsUserInfo == null) {
				logger.info("需要获取用户信息");
				// 获取网页授权access_token
				WeixinOauth2Token weixinOauth2Token = AdvancedUtil
						.getOauth2AccessToken(Config.getValue("appID"),
								Config.getValue("appsecret"), code + "");
				String accessToken = weixinOauth2Token.getAccessToken();
				// 用户标识
				String openId = weixinOauth2Token.getOpenId();
				request.getSession().setAttribute("openId", openId);
				// 获取用户信息
				snsUserInfo = AdvancedUtil.getWeixinUserInfo(accessToken,
						openId);
				request.getSession().setAttribute("snsUserInfo", snsUserInfo);

				// 检查用户是否已绑定
				flag = UD.checkBind(openId);
				if (!flag) {
					logger.info("欢迎来自" + snsUserInfo.getProvince() + " "
							+ snsUserInfo.getCity() + "新用户:"
							+ snsUserInfo.getNickname());
					UD.addUserInfo(snsUserInfo);
				}
			} else {
				logger.info("用户信息已经存在session中"+"欢迎来自" + snsUserInfo.getProvince() + " "
						+ snsUserInfo.getCity()+"的"+snsUserInfo.getNickname());
			}
		}
	}

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String token = "iip_group";
		String[] strArray = new String[] { token, timestamp, nonce };
		Arrays.sort(strArray);
		String str = strArray[0] + strArray[1] + strArray[2];
		String sha1String = null;
		try {
			sha1String = byte2Hex(MessageDigest.getInstance("SHA-1").digest(
					str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (sha1String.equals(signature)) {
			return true;
		} else {
			return false;
		}
	}

	public static String byte2Hex(byte[] b) {
		String hexStr = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexStr += hex;
		}
		return hexStr;

	}

	// 获取微信js签名
	public static String getJsSignature(String accessToken, String noncestr,
			String timestamp, String url) {
		String signature = null;
		String jsapi_ticket = getJsapiTicket(accessToken);
		String str1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr
				+ "&timestamp=" + timestamp + "&url=" + url;
		try {
			signature = byte2Hex(MessageDigest.getInstance("SHA-1").digest(
					str1.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return signature;
	}

	public static String getJsapiTicket(String accessToken) {
		if (jsapi_ticket != null) {
			return jsapi_ticket;
		}
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=jsapi";
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = JSONObject.fromObject(message);
			if (jsonObj.containsKey("ticket")) {
				jsapi_ticket = jsonObj.getString("ticket");
			} else {
				jsapi_ticket = null;
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsapi_ticket;
	}

	// 普通access_token
	public static String getAccessToken() {
		if (accessToken != null) {
			return accessToken;
		}
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ Config.getValue("appID")
				+ "&secret="
				+ Config.getValue("appsecret");
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = JSONObject.fromObject(message);
			if (jsonObj.containsKey("access_token")) {
				accessToken = jsonObj.getString("access_token");
			} else {
				accessToken = null;
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public static String[] getSpecialAccessToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid="
				+ Config.getValue("appID")
				+ "&secret="
				+ Config.getValue("appsecret") + "&code=" + code;
		String[] result = new String[2];
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = JSONObject.fromObject(message);
			if (jsonObj.containsKey("access_token")) {
				result[0] = jsonObj.getString("access_token");
				result[1] = jsonObj.getString("openid");
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
