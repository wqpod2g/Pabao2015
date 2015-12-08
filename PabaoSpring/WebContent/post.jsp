<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="nju.iip.dto.Post"%>
<%@ page import="nju.iip.dto.Comment"%>
<%@ page import="nju.iip.dao.PostDao"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>帖子详情</title>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
</head>
<body>

	<%
	 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		Post post = (Post) request.getAttribute("post");
	    PostDao postdao = new PostDao();
		List<Comment> comment_list = postdao.getAllComment(post.getId());
		String openId = (String)request.getSession().getAttribute("openId");
		int postId = post.getId();
		boolean isLoved = postdao.isLove(openId, postId);
	%>

	<div class="bgfff form ov">
		<div class="sendMessageToHim" id=<%=post.getOpenId()%>>
			<img src=<%=post.getHeadImgUrl()%> alt="求真相" class="img-circle"
				width="15%">&nbsp;&nbsp; <font size="3px" color="#337ab7"><%=post.getAuthor()%></font>
		</div>
		<hr style="border: 0; height: 0.1px;" />
		<div class="fb">
			<font size="3.5px" style="font-weight: bold;"><%=post.getTitle()%></font>
		</div>
		<hr style="border: 0; height: 0.1px;" />
		<div>
			<font size="3px"><%=post.getContent()%></font><br>
			<%if(post.getPictureUrl()!=null) {
				String url = basePath+"/Pictures/images/upload/"+post.getPictureUrl();
			%>
			<img src=<%=url%> width="80%" />
			<% }%>
		</div>
		<hr />
		<div>
			<table width="100%">
				<tr>
					<td width="60%"><font size="2px" color="#337ab7"><%=post.getAuthor()%></font>&nbsp;&nbsp;<font
						size="1.5px" color="#C8C6C6"><%=post.getPostTime().substring(5)%></font></td>

					<td style="text-align: right;"><span
						class="glyphicon love" aria-hidden="true"></span>&nbsp;
						<font id="love" size="3px" color="#C8C6C6"><%=post.getLove()%></font>&nbsp;&nbsp;

						<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>&nbsp;
						<font size="3px" color="#C8C6C6"><%=post.getReply()%></font>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</div>
	<hr />

	<%
		int i=1;
		for(Comment comment:comment_list) {
	%>
	<div class="bgfff form ov" style="line-height: 1.2">

		<table width="100%">
			<tr>
				<td width="70%">
					<div class="sendMessageToHim" id=<%=comment.getOpenId()%>>
						<img src=<%=comment.getHeadImgUrl()%> alt="求真相" class="img-circle"
							width="13%">&nbsp;&nbsp; <font size="3px" color="#337ab7"><%=comment.getAuthor()%></font>
					</div>
				</td>
				<td style="text-align: center;"><%=i%>楼</td>
			</tr>
		</table>
		<hr style="border: 0; height: 0.1px;" />
		<div>
			<font size="3px"><%=comment.getComment()%></font>
		</div>
		<hr />
		<div>
			&nbsp;&nbsp; <font size="1.5px" color="#C8C6C6"><%=comment.getCommentTime()%></font>
		</div>

	</div>

	<%
		i++;
		}
	%>
	
	


	<div style="margin: 10px 0 60px 0"></div>

	<div class="fix_footer" id="fix_footer"
		style="padding: 5px; left: 0px; color: rgb(255, 255, 255); height: 45px; width: 100%; position: fixed; bottom: 0%; font-size: 14px; display: block; background-color: #337ab7;">
		<table width="100%">
			<tr>
				<td><input type="text" class="form-control"
					placeholder="评论点什么吧"></td>
				<td style="text-align: center;"><span id="send">发送</span><span
					class="glyphicon glyphicon-send" aria-hidden="true"></span></td>
			</tr>
		</table>
	</div>
	
	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content" id="show_reslut"
				style="text-align: center; margin: 5px auto 5px auto">
				<br> <span class="return_msg"></span><span
					class="glyphicon glyphicon-ok"></span><br> <br>
			</div>
		</div>
	</div>

</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript"> 
	$(document).ready(function() { 
		var loved = <%=isLoved%>;
		if(loved==false) {
			$("span.love").addClass("glyphicon-heart-empty");
		}
		else {
			$("span.love").addClass("glyphicon-heart");
		}
		
		var ToOpenId,ToNickname;
	
		$("div.sendMessageToHim").click(function() {
			var userOpenId = "<%=openId%>";
			ToOpenId =  $(this).attr("id");
			if(ToOpenId!=userOpenId) {
				location.href = "HomePage.jsp?openId=" + ToOpenId;
			}
			
		});
		
		//点赞处理逻辑 
		$("span.glyphicon").click(function() {
			if($("span.glyphicon").hasClass("glyphicon-heart-empty")) {
				$("span.glyphicon").removeClass("glyphicon-heart-empty");
				$("span.glyphicon").addClass("glyphicon-heart");
				$.get("AddLike?id="+<%=post.getId()%>,function(data,status){
				      //alert("状态：" + status);
			    });
				var n=$("font#love").text();
				$("font#love").text(Number(n)+1);
			}
		});
		
		
		//回复帖子处理 
		$("span#send").click(function() { 
			var comment = $("input.form-control").val(); 
			if (comment == "") {
				$("input.form-control").attr( "placeholder", "评论内容不能为空！"); 
				} 
			else { 
				$( ".bs-example-modal-sm").modal('show'); 
				$( "span.return_msg").html('<i class="fa fa-circle-o-notch fa-spin"></i>&nbsp;'+'发送中...'); 
				$.ajax({ 
					type : 'POST',
					url : "AddComment",
					data : { "comment" : comment }, 
					success : function( msg) { 
						$( "span.return_msg").html( "<br>" + msg); 
						
						setTimeout( function() { 
							$( ".bs-example-modal-sm").modal( 'hide');
							location.href = "ShowPost?id=" + <%=post.getId()%> ; 
							}, 2000); 
						} 
					}); 
				} 
			}); 
		
		
		}); 
	</script>

</html>