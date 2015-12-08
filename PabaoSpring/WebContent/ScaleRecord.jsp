<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="nju.iip.dto.WeixinUser"%>
<%@ page import="nju.iip.dto.ScaleRecord"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link type="text/css" rel="stylesheet" href="css/base.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<title>已测量表记录</title>
<script type="text/javascript" src="js/common.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0
}

table {
	border: 1px dashed #B9B9DD;
	font-size: 12pt
}

td {
	border: 1px dashed #B9B9DD;
	word-break: break-all;
	word-wrap: break-word;
}
</style>
</head>
<body>
	<%
		// 获取由OAuthServlet中传入的参数
		List<ScaleRecord> record_list = (List<ScaleRecord>) request.getAttribute("record_list");
		if (null != record_list) {
			int i = 1;
			for (ScaleRecord record : record_list) {
	%>
	<div class="bgfff form ov">
		<div class="fb">
			NO.<%=i%></div>
		<ul class="cb">
			<li>
				<div class="fl la_bg tc">
					<label for="name" class="lable">量表名称</label>
				</div>
				<div class="fl l_r">
					&nbsp;&nbsp;<%=record.getScaleName()%></div>
			</li>
			<li>
				<div class="fl la_bg tc">
					<label for="name" class="lable">得分</label>
				</div>
				<div class="fl l_r">
					&nbsp;&nbsp;<%=record.getScore()%></div>
			</li>
			<li>
				<div class="fl la_bg tc">
					<label for="name" class="lable">填写时间</label>
				</div>
				<div class="fl l_r">
					&nbsp;&nbsp;<%=record.getTime()%></div>
			</li>
		</ul>
	</div>
	<%
		i++;
			}
		} else
			out.print("获取量表信息失败！");
	%>
</body>
</html>