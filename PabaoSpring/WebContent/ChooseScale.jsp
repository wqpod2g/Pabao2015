<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="nju.iip.dto.Scale"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link type="text/css" rel="stylesheet" href="css/base.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<title>请选择一张量表</title>
</head>
<body>
	<div class="wrap">
		<div class="bgfff form ov">
			<div class="fb">请选择一张量表：</div>
			<ul class="cb">
				<%
					List<Scale> ScaleList = (List<Scale>) request.getAttribute("ScaleList");
					if (ScaleList != null) {
						int i = 1;
						for (Scale scale : ScaleList) {
				%>
				<li>
					<form action="GetScale" method="GET">
						<%
							String name = i + "." + scale.getScaleName();
						%>
						<input name="totalScaleId" value=<%=scale.getId()%> type="hidden" />
						<div class="cb pt20">
							<input type="submit" value=<%=name%> class="but2" />
						</div>
					</form>
				</li>

				<%
					i++;
						}
					} else {
						out.print("获取量表信息失败！");
					}
				%>

			</ul>
		</div>
	</div>
</body>
</html>