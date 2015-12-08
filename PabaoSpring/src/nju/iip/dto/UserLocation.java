package nju.iip.dto;

/**
 * 用户位置信息
 * @author mrpod2g
 *
 */
public class UserLocation {
	
	private String Latitude;//维度
	
	private String Longitude;//经度
	
	private String openId;//用户openId
	
	private String time;//最后记录时间

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
