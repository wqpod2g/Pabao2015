<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link type="text/css" rel="stylesheet" href="css/base.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<title>游戏测评</title>
</head>
<body>
	<div class="wrap">
		<div class="bgfff form ov">
			<ul class="cb">
				<li>
					<div class="cb pt20">
						<input id="crazy1" type="submit" class="but2" value="疯狂的手指一"/>
					</div>
				</li>
				<li>
					<div class="cb pt20">
						<input id="crazy2" type="submit" class="but2" value="疯狂的手指二"/>
					</div>
				</li>
			</ul>
		</div>
	</div>
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("input#crazy1").click(function(){
		location='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+'${appID}'+'&redirect_uri='+'${ServerDomain}'+'/Game/CrazyFinger1.do&response_type=code&scope=snsapi_userinfo&state=CrazyFinger1#wechat_redirect';
		
	});
	
	$("input#crazy2").click(function(){
		location='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+'${appID}'+'&redirect_uri='+'${ServerDomain}'+'/Game/CrazyFinger2.do&response_type=code&scope=snsapi_userinfo&state=CrazyFinger2#wechat_redirect';
	});
});
</script>
</html>