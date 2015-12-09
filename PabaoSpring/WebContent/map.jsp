<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#golist {
	display: none;
}

@media ( max-device-width : 780px) {
	#golist {
		display: block !important;
	}
}
</style>
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
<title>查看附近的人</title>

<%
	String str = (String) request.getAttribute("location_json");
	String openId = (String) request.getSession().getAttribute("openId");
%>
</head>
<body>
	<div id="allmap"></div>
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="js/mui.min.js"></script>
<script src="http://api.map.baidu.com/api?type=quick&ak=M8xS2NSEYa4amEnURlA8icFg&v=1.0"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var init =<%=str%>;
						var userOpenId = '<%=openId%>';
						var center = init.center;
						// 百度地图API功能
						var map = new BMap.Map("allmap");
						if(jQuery.isEmptyObject(center)) {
							alert("您未提供位置信息！要查看附近的人请返回点击公众号右上角->提供位置信息");
						}
						else {
							
						
						map.centerAndZoom(new BMap.Point(
								center.Longitude,
								center.Latitude), 11);
						}
						map.addControl(new BMap.ZoomControl()); //添加地图缩放控件

						//var Point = [], marker = [], infoWindow = [];
						//var i = 0;

						for (var i = 0; i < init.location.length; i++) {
							var headImgUrl = init.location[i].headImgUrl;
							var nickname = init.location[i].nickname;
							var openId = init.location[i].openId;
							if (nickname == null)
								continue;
							(function(x) {
								var url;
								if(userOpenId!=openId) {
									url = '<a href="HomePage.jsp?openId='+openId;
								}
								else {
									url = '<a href="MyHomePage.jsp';
								}
								var Point = new BMap.Point(
										init.location[i].Longitude,
										init.location[i].Latitude);
								var marker = new BMap.Marker(Point);
								var opts = {
									width : 100, // 信息窗口宽度
									height : 65, // 信息窗口高度
									title : url+'"><img id="'
											+ openId
											+ '" alt="求真相" class="img-circle" style="width:30%;" src="'
											+ headImgUrl
											+ '"><font id="name">&nbsp;&nbsp;'
											+ nickname + '</font></a>', // 信息窗口标题
									enableAutoPan : true
								//自动平移
								}
								var infoWindow = new BMap.InfoWindow("", opts);
								if(userOpenId==openId) {
									map.openInfoWindow(infoWindow, Point);
								}
								marker.addEventListener("click", function() {
									map.openInfoWindow(infoWindow, Point);
								});
								
								map.addOverlay(marker);
								})(i);
							}
					});
</script>
</html>

