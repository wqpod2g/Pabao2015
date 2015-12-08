<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="nju.iip.dto.WeixinUser"%>
<%@ page import="nju.iip.dao.UserDao"%>
<html>
<head>
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/mui.min.css">
<title>我的主页</title>
</head>
<body>
	<%
		WeixinUser user = (WeixinUser)request.getSession().getAttribute("user");
	%>
	<div class="bgfff form ov" style="width: 100%; margin-top: 1px;">
		<div style="margin: 0 auto 0 40%">
			<img src=<%=user.getHeadImgUrl()%> alt="求真相" class="img-circle"
				width="40%">
		</div>
	</div>

	<ul class="mui-table-view">
		<li class="mui-table-view-cell" style="background-color: #efeff4;">
			<a> <font color="#818184">基本信息</font>
		</a>
		</li>
		<li class="mui-table-view-cell"><table width="100%">
				<tr>
					<td width="50%"><span class="glyphicon glyphicon-user"
						aria-hidden="true"></span>&nbsp;&nbsp;姓名</td>
					<td style="text-align: right"><font color="#337ab7" id="name"><%=user.getName()%></font></td>
				</tr>
			</table></li>
		<li class="mui-table-view-cell"><table width="100%">
				<tr>
					<td width="50%"><span class="glyphicon glyphicon-phone"
						aria-hidden="true"></span>&nbsp;&nbsp;手机号</td>
					<td style="text-align: right"><font color="#337ab7" id="phone"><%=user.getPhone()%></font></td>
				</tr>
			</table></li>
	</ul>
	<ul class="mui-table-view">
		<li class="mui-table-view-cell" style="background-color: #efeff4;">
			<a> <font color="#818184">功能</font>
		</a>
		</li>
		<li class="mui-table-view-cell"><a href="#modal"
			class="mui-navigate-right"> <span
				class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;&nbsp;修改信息
		</a></li>
	</ul>

	<div id="modal" class="mui-modal">
		<header class="mui-bar mui-bar-nav">
			<a class="mui-icon mui-icon-close mui-pull-right" href="#modal"></a>
			<h1 class="mui-title">修改信息</h1>
		</header>
		<div class="mui-content" style="height: 100%;">
			<form class="mui-input-group">
				<div class="mui-input-row">
					<label>姓名</label> <input id="name" type="text"
						class="mui-input-clear" placeholder="请输入姓名">
				</div>
				<div class="mui-input-row">
					<label>手机号</label> <input id="phone" type="text"
						class="mui-input-clear" placeholder="请输入手机号">
				</div>
				<div class="mui-button-row">
					<button id="submit" type="button" class="mui-btn mui-btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/mui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("button#submit").click(function() {
			var name = $("input#name").val();
			var phone = $("input#phone").val();
			if (name.length == 0) {
				$("input#name").attr("placeholder", "姓名不能为空！");
			}
			if (phone.length == 0) {
				$("input#phone").attr("placeholder", "电话不能为空！");
			}
			if (name.length != 0 && phone.length != 0) {
				$("div#modal").removeClass("mui-active");
				$.ajax({
					type : 'GET',
					url : "alterUserInfo?name=" + name + "&phone=" + phone,
					success : function(msg) {
						alert(msg);
						$("font#name").html(name);
						$("font#phone").html(phone);
					}
				});
			}
		});
	});
</script>

</html>