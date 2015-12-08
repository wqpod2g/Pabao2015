<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="nju.iip.dto.WeixinUser"%>
<%@ page import="nju.iip.dao.UserDao"%>
<html>
<head>
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/mui.min.css">
<title></title>
</head>
<body>
	<%
		UserDao UD = new UserDao();
		String openId = (String) request.getParameter("openId");
		WeixinUser user = UD.getUser(openId);
		int sex = user.getSex();
		String sexString = "";
		if (sex == 1) {
			sexString = "男";
		} else if (sex == 0) {
			sexString = "女";
		} else {
			sexString = "未知";
		}
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
					<td style="text-align: right"><font color="#337ab7" id="name"><%=user.getNickname()%></font></td>
				</tr>
			</table></li>
		<li class="mui-table-view-cell"><table width="100%">
				<tr>
					<td width="50%"><span class="glyphicon glyphicon-grain"
						aria-hidden="true"></span>&nbsp;&nbsp;性别</td>
					<td style="text-align: right"><font color="#337ab7" id="sex"><%=sexString%></font></td>
				</tr>
			</table></li>
		<li class="mui-table-view-cell"><table width="100%">
				<tr>
					<td width="50%"><span class="glyphicon glyphicon-globe"
						aria-hidden="true"></span>&nbsp;&nbsp;地区</td>
					<td style="text-align: right"><font color="#337ab7"
						id="district"><%=user.getProvince()%>&nbsp;&nbsp;<%=user.getCity()%></font></td>
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
	<hr>
	<button id="sendMessage" type="button" class="btn btn-success"
		style="width: 100%; height: 50px; border-radius: 0px;">
		<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;&nbsp;发送消息给他(她)
	</button>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						发送消息给:<font color="#337ab7"></font>
					</h4>
				</div>
				<div class="modal-body">

					<div class="form-group" id="form-content">
						<label for="message-text" class="control-label">内容:</label>
						<textarea class="form-control" id="message-text" name="message"></textarea>
					</div>
					<!--  
					<div>
					<input id="upload" type="file" style="display:none">
                    <button type="button" class="btn btn-info"  onclick="$('input[id=upload]').click();" >
                     <span class="glyphicon glyphicon-picture" aria-hidden="true"></span>添加图片
                     </button>
                    </div>
                    -->
                    <div style="margin: 5px"></div>
                    <div><img id="pic"  src="" width="40%"></div>
                     <div style="margin: 5px"></div>
                   <div id="progress" >
                   <div id="progress-bar" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100" style="width: 10%;">
                   </div>
                   </div>
                 

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" id="send">确认</button>
					</div>
				</div>

			</div>
		</div>
	</div>
	<!-- end -->

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
<script src="js/bootstrap.min.js"></script>
<script src="js/mui.min.js"></script>
<script src="js/lrz.mobile.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	 	var basePath = window.location.protocol+"//"+window.location.host;
       	var pic_server_url = basePath+"/Pictures/";
		var nickName = '<%=user.getNickname()%>';
		var OpenId = '<%=openId%>';
		$("title").html(nickName + "的主页");
		$("button#sendMessage").click(function() {
			$("#myModal").modal("show");
			$("h4.modal-title").children("font").html(nickName);
		});
		
		
		
		 var reader = new FileReader();
		    var picture = '';
		    var pictureUrl = '';
		    $('input#upload').change(function(){
		    	 if (this.files && this.files[0]) {
		             // reader.readAsDataURL(this.files[0]);
		    	// }
		        
		    	// 也可以传入图片路径：lrz('../demo.jpg', ...
		    	      lrz(this.files[0], {
		    	    	  
		    	    	  // 压缩率
		    	    	  // quality: 0.2,
		    	    	  width : 500,
		    	    	  
		    	        // 压缩开始
		    	        before: function() {
		    	        	$("img#pic").attr("src","images/wait.gif");
		    	            console.log('压缩开始');
		    	        },
		    	        // 压缩失败
		    	        fail: function(err) {
		    	            console.error(err);
		    	        },
		    	        // 压缩结束（不论成功失败）
		    	        always: function() {
		    	            console.log('压缩结束');
		    	        },
		    	        // 压缩成功
		    	        done: function (results) {
		    	              // 你需要的数据都在这里，可以以字符串的形式传送base64给服务端转存为图片。
		    	              console.log(results); 
		    	              picture =  results.base64;
		    	              $.ajax({
		    	            	  type: "POST",
		    	            	  url: pic_server_url+"SavePictureServlet",
		    	            	  data: {
		    	            		  "picture" : picture
		    	            	  },
		    	            	  success: function(msg) {
		    	            		  pictureUrl = msg;
		    	            		  $("img#pic").attr('src', results.base64).addClass("img-thumbnail");
		    	            	  }
		    	            	  
		    	              });
		    	        }
		    	    });  
		         }
		    });
		
		
		
		$("button#send").click(function() {
			var message = $("textarea#message-text").val();
			if(message == "") {
				$("textarea#message-text").attr("placeholder","消息内容不能为空!");
			}
			else {
				$("div#progress").addClass("progress");
				$("div#progress-bar").addClass("progress-bar");
				$("div#progress-bar").html("10%");
				$.ajax({
					type : 'POST',
					url : "ReceiveMessage",
					data : {
						"content" : message,
						"toOpenId" : OpenId,
						"toNickName" : nickName,
						"pictureUrl" : pictureUrl
					},
					success : function(msg) {
						$("div#progress-bar").html("100%").attr("aria-valuenow","100").attr("style","width:100%;");
						setTimeout(function() {
						$("#myModal").modal("hide");
						$("span.return_msg").html(msg); 
						$(".bs-example-modal-sm").modal('show');
						setTimeout(function() {
							$(".bs-example-modal-sm").modal('hide');
						}, 2000);
						},500);
					}
				});
			}
		});

	});
</script>

</html>