package nju.iip.dto;

/**
 * 私信消息类
 * 
 * @author mrpod2g
 */
public class Message extends BaseMessage {

	// 消息id
	private int id;

	// 消息内容
	private String content;

	// 消息所包含的图片
	private String pictureUrl;

	// 发送者的openid
	private String fromOpenId;

	// 接受者的opendid
	private String toOpenId;

	// 发送者的微信昵称
	private String fromNickName;

	// 接受者的微信昵称
	private String toNickName;

	// 发送者的微信头像
	private String fromHeadImgUrl;

	// 消息是否被读过 0:未读过 1：读过
	private int isRead;

	// 发送时间
	private String sendTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getFromOpenId() {
		return fromOpenId;
	}

	public void setFromOpenId(String fromOpenId) {
		this.fromOpenId = fromOpenId;
	}

	public String getToOpenId() {
		return toOpenId;
	}

	public void setToOpenId(String toOpenId) {
		this.toOpenId = toOpenId;
	}


	public String getFromNickName() {
		return fromNickName;
	}

	public void setFromNickName(String fromNickName) {
		this.fromNickName = fromNickName;
	}

	public String getToNickName() {
		return toNickName;
	}

	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	public String getFromHeadImgUrl() {
		return fromHeadImgUrl;
	}

	public void setFromHeadImgUrl(String fromHeadImgUrl) {
		this.fromHeadImgUrl = fromHeadImgUrl;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	

}
