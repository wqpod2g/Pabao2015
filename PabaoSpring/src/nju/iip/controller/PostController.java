package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import nju.iip.dao.PostDao;
import nju.iip.dto.Comment;
import nju.iip.dto.Love;
import nju.iip.dto.Post;
import nju.iip.dto.WeixinUser;
import nju.iip.service.OAuthService;
import nju.iip.util.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微社区相关控制类
 * @author wangqiang
 *
 */
@Controller
public class PostController {

	@Autowired
	private PostDao postdao;

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@RequestMapping(value = "/post_list")
	public String showPostList(HttpServletRequest request) {
		logger.info("showPostList called");
		OAuthService.getUerInfo(request);// 获取用户信息
		return "post_list.jsp";
	}

	/**
	 * 下拉获取更多帖子
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetMorePosts")
	public void getMorePost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String count = request.getParameter("count");
		logger.info("count=" + count);
		List<Post> post_list = postdao.getAllPostLimit(Integer.valueOf(count) * 10);
		JSONObject json = new JSONObject();
		json.put("post", post_list);
		logger.info("json=" + json.toString());
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/ShowPost")
	public String showPost(HttpServletRequest request) {
		logger.info("showPost called");
		String postId = request.getParameter("id");
		logger.info("postId=" + postId);
		Post post = postdao.getPostById(Integer.valueOf(postId));
		logger.info("like="+post.getLove());
		request.setAttribute("post", post);
		request.getSession().setAttribute("postId", postId);
		return "post.jsp";
	}

	@RequestMapping(value = "/SavePost")
	public void savePost(HttpServletRequest request,HttpServletResponse response, Post post) throws IOException {
		logger.info("savePost called");
		logger.info("title=" + post.getTitle() + " content="+ post.getContent() + " pictureUrl=" + post.getPictureUrl());
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		if(post.getPictureUrl().length()==0) {
			post.setPictureUrl(null);
		}
		post.setAuthor(user.getNickname());
		post.setHeadImgUrl(user.getHeadImgUrl());
		post.setPostTime(CommonUtil.getTime());
		post.setOpenId(user.getOpenId());
		postdao.savePost(post);
		PrintWriter out = response.getWriter();
		out.write("发帖成功!");
	}

	@RequestMapping(value = "/AddLike")
	public void addLike(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("addLike called");
		String postId = request.getParameter("id");
		logger.info("postId=" + postId);
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		Love love = new Love();
		love.setPostId(Integer.valueOf(postId));
		love.setOpenId(user.getOpenId());
		love.setHeadImgUrl(user.getHeadImgUrl());
		love.setLoveTime(CommonUtil.getTime());
		love.setAuthor(user.getNickname());
		postdao.addLike(love);
		PrintWriter out = response.getWriter();
		out.write("success!");
	}
	
	@RequestMapping(value = "/AddComment")
	public void AddComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String comment = request.getParameter("comment");
		String postId = (String)request.getSession().getAttribute("postId");
		WeixinUser user = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		logger.info("来自"+user.getNickname()+"的评论"+"comment="+comment+ "   postId="+postId);
		Comment com= new Comment();
		com.setComment(comment);
		com.setAuthor(user.getNickname());
		com.setHeadImgUrl(user.getHeadImgUrl());
		com.setOpenId(user.getOpenId());
		com.setPostId(Integer.valueOf(postId));
		com.setCommentTime(CommonUtil.getTime());
		PrintWriter out = response.getWriter();
		if(postdao.addComment(com)) {
			out.write("success!");
		}
		else{
			out.write("falied!");
		}
	}

}
