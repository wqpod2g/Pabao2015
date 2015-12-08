package nju.iip.dto;

/**
 * 用户所填量表记录类
 * @author wangqiang
 * @since 2015-6-4
 *
 */
public class ScaleRecord {
	
	String openId;//填写量表用户的openId
	
	String scaleName;//所填量表名称
	
	int scaleId;//量表ID
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}

	public int getScaleId() {
		return scaleId;
	}

	public void setScaleId(int scaleId) {
		this.scaleId = scaleId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	String score;//得分
	
	String time;//填写时间
	
	
	

}
