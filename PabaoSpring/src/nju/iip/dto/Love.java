package nju.iip.dto;


/**
 * 赞
 * @author qiang
 *
 */
public class Love {
	
	private int id;
	

	//对应的postId
	private int postId;
	
	//点赞人对应的微信昵称
	private String author;
	
	//头像url
	private String headImgUrl;
	
	//点赞人的openId
	private String openId;
	
	//点赞时间
	private String loveTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLoveTime() {
		return loveTime;
	}

	public void setLoveTime(String loveTime) {
		this.loveTime = loveTime;
	}
	

}
