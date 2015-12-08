package nju.iip.dto;


/**
 * 帖子类
 * 
 * @author wangqiang
 * 
 */
public class Post {

	// 帖子id
	private int id;

	// 标题
	private String title;

	// 内容
	private String content;

	// 作者
	private String author;

	// 发帖人的openId
	private String openId;

	// 用户头像链接
	private String headImgUrl;

	// 发帖时间
	private String postTime;

	// 回复数
	private int reply;
	
	//点赞数
	private int love;
	
	//帖子所包含的图片
	private String pictureUrl;
	
	//是否是置顶帖
	private int isUp;

	public int getIsUp() {
		return isUp;
	}

	public void setIsUp(int isUp) {
		this.isUp = isUp;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}
	
	

	// 最后回复时间
	private String lastReplyTime;

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public String getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(String lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

}
