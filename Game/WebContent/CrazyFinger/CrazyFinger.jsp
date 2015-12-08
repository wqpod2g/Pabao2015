<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>疯狂手指一</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<script src="CrazyFinger/js/jquery-1.10.1.min.js"></script>
<script src="CrazyFinger/js/CrazyFinger.js"></script>
<script src=" https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link rel="stylesheet" href="CrazyFinger/css/CrazyFinger.css">
</head>
<body>
	<div
		style="display: inline-block; width: 100%; height: 100%; margin: 0 auto; background: black; position: relative;"
		id="gameDiv">
		<div style="text-align: center; margin: 30px;">
			<span id="timer" style="color: #fff; font-size: 20px;">10 秒 </span>
		</div>
		<button id="button" class="button red"
			style="margin-top: 30px; width: 160px; height: 160px; border-radius: 80px; font-size: 18px; font-weight: bold;">点我点我</button>
		<div style="margin: 20px; text-align: center; margin-bottom: 0">
			<div id="result" style="color: #fff; font-size: 30px;">0 次</div>
			<div id="best"
				style="margin-top: 10px; color: #fff; font-size: 20px;"></div>
		</div>
	</div>

	<div id="result_panel"
		style="display: none; text-align: center; padding-top: 20px; position: absolute; top: 0; right: 0; bottom: 0; left: 0; background-color: rgba(0, 0, 0, 0.8)">
		<div>
			<button id="reset" style="font-size: 25px">再玩一次</button>
		</div>

		<div id="ranking" style="display: block; padding: 15px">
			<table>
			</table>
		</div>
		<div id="scoreMsg" style="padding: 15px; color: #aaa;"></div>
	</div>


	<div id="sbg" class="sbg">
		<div class="arron"></div>
		<p id="msg">
			请点击右上角<br />点击【分享到朋友圈】<br />测测好友的反应能力吧！
		</p>
	</div>
	<script type="text/javascript">
		window.shareData = {
			"imgUrl" : '${ServerDomain}'+'/Game/CrazyFinger/img/DGxrr.jpg',
			"timeLineLink" : 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+'${appID}'+'&redirect_uri='+'${ServerDomain}'+'/Game/CrazyFinger1.do&response_type=code&scope=snsapi_userinfo&state=CrazyFinger1#wechat_redirect',
			"tTitle" : "疯狂手指-根本停不下来，玩过之后我的手指已经不是我的了！",
			"tContent" : "在10秒内疯狂的点按钮，玩过之后我的手指已经不是我的了！想赢？不容易。"
		};

		// 微信配置
		wx.config({
			debug : false,
			appId : '${appID}',
			timestamp : '${timestamp_js}',
			nonceStr : '${nonceStr_js}',
			signature : '${signature_js}',
			jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage' ]
		// 功能列表，我们要使用js-SDK的什么功能
		});

		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在 页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready 函数中。
		wx.ready(function() {
			// 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
			wx.onMenuShareTimeline({
				title : window.shareData.tTitle, // 分享标题
				link : window.shareData.timeLineLink,
				imgUrl : window.shareData.imgUrl, // 分享图标
				success : function() {
					// 用户确认分享后执行的回调函数
					alert("分享成功！");
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
				}
			});
			// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			wx.onMenuShareAppMessage({
				title : window.shareData.tTitle, // 分享标题
				desc : window.shareData.tContent, // 分享描述
				link : window.shareData.timeLineLink,
				imgUrl : window.shareData.imgUrl, // 分享图标
				type : 'link', // 分享类型,music、video或link，不填默认为link
			});
		});
	</script>
</body>
</html>