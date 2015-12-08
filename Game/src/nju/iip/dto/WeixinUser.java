package nju.iip.dto;

import java.util.List;

/**
 * 通过网页授权获取的用户信息
 * 
 * @author mrpod2g
 * @date 2013-11-09
 */
public class WeixinUser {
	
	//用户姓名
	private String name;
	
	//用户病历号
	private String cardID;
	
	//用户手机号
	private String phone;
	
	// 用户标识
	private String openId;
	// 用户昵称
	private String nickname;
	// 性别（1是男性，2是女性，0是未知）
	private int sex;
	// 国家
	private String country;
	// 省份
	private String province;

	// 城市
	private String city;
	// 用户头像链接
	private String headImgUrl;
	// 用户特权信息
	private List<String> privilegeList;
	
	private String registdate;
	

	public String getRegistdate() {
		return registdate;
	}

	public void setRegistdate(String registdate) {
		this.registdate = registdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public List<String> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
}
