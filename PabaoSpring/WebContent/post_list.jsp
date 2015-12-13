<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="nju.iip.dto.Post"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<title>微社区</title>
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
</head>
<body>
	<div id="contain">

		<%
			List<Post> post_list = (List<Post>) request.getAttribute("post_list");
			for (Post post : post_list) {
				String content = post.getContent();
				String title = post.getTitle();
		%>
		<div class="bgfff form ov" id=<%=post.getId()%>>
			<div class="fb">
				<font size="3.5px"><%=title%></font>
			</div>
			<hr style="border: 0; height: 0.1px;" />
			<div>
				<%
					if (post.getPictureUrl() != null) {
				%>
				<span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
				<%
					}
				%>
				<font size="3px"><%=content%></font>

			</div>
			<hr />
			<div>
				<table width="100%">
					<tr>
						<td width="60%"><font size="2px" color="#337ab7"><%=post.getAuthor()%></font>&nbsp;&nbsp;<font
							size="1.5px" color="#C8C6C6"><%=post.getPostTime().substring(5)%></font></td>

						<td style="text-align: right;"><span
							class="glyphicon glyphicon-heart" aria-hidden="true"></span>&nbsp;
							<font size="3px" color="#C8C6C6"><%=post.getLove()%></font>&nbsp;&nbsp;

							<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>&nbsp;
							<font size="3px" color="#C8C6C6"><%=post.getReply()%></font>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</div>


		<%
			}
		%>
	</div>
	<div id="refresh" style="margin: 5px auto 0 40%">
		<i class="fa fa-spinner fa-spin"></i>正在加载...
	</div>
	<div style="margin: 10px 0 60px 0"></div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">请输入标题和内容：</h4>
				</div>
				<div class="modal-body">

					<div class="form-group" id="form-title">
						<label for="recipient-name" class="control-label">标题:</label> <input
							type="text" class="form-control" name="username"
							id="message-title">

					</div>
					<div class="form-group" id="form-content">
						<label for="message-text" class="control-label">内容:</label>
						<textarea class="form-control" id="message-text" name="message"></textarea>
					</div>
					<div>
						<input id="upload" type="file" style="display: none"> <input
							type="button" class="btn btn-info"
							onclick="$('input[id=upload]').click();" value="添加图片">
					</div>
					<div style="margin: 5px"></div>
					<div>
						<img id="pic" src="" width="40%">
					</div>
					<div style="margin: 5px"></div>

					<div id="progress">
						<div id="progress-bar" role="progressbar" aria-valuenow="10"
							aria-valuemin="0" aria-valuemax="100" style="width: 10%;">
						</div>
					</div>

					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="取消"> <input type="button" class="btn btn-primary"
							id="send" value="确认">
					</div>
				</div>

			</div>
		</div>
	</div>



	<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content" id="show_reslut"
				style="text-align: center; margin: 5px auto 5px auto">
				<span class="return_msg"></span><span class="glyphicon glyphicon-ok"></span><br>
				<br> <br>
			</div>
		</div>
	</div>



	<nav class="navbar navbar-default navbar-fixed-bottom">
		<div class="container"
			style="padding-right: 0; padding-left: 0; width: 100%;">
			<!-- Button trigger modal -->
			<button type="button" class="btn btn-primary btn-lg"
				data-toggle="modal" data-target="#myModal"
				style="width: 100%; border-radius: 1px; line-height: 1.5;">
				<span class="glyphicon glyphicon-edit"></span>发帖
			</button>
		</div>
	</nav>
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/lrz.mobile.min.js"></script>
<script src="js/post.js"></script>
</html>