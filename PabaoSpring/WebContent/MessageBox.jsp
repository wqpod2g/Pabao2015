<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="nju.iip.dto.Message"%>
<%@ page import="nju.iip.dao.MessageDao"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/common.css">
<title></title>
</head>
<body>

	<%
		List<Message> messages = (List<Message>)request.getAttribute("message_list");
		int i=0;
		for(Message message:messages) {
			int isRead = message.getIsRead();
	%>
	<div class="bgfff form ov" style="line-height: 1.2;width: 98%;border-radius:3px;margin-top:6px;" id=<%=message.getId()%>>
	<div id="content" style="display:none"><%=message.getContent()%></div>
	<div id="picture" style="display:none"><%=message.getPictureUrl()%></div>
	<input id="fromOpenId" type="text" style="display:none" value=<%=message.getFromOpenId()%>>
	
		<table width="100%">
			<tr>
				<td width="60%">
					<div class="sendMessageToHim">
						<img
							src=<%=message.getFromHeadImgUrl()%>
							alt="求真相" class="img-circle" width="30%">&nbsp;&nbsp; 
							<font id="nickname" color="#337ab7"><%=message.getFromNickName()%></font>
					</div>
				</td>
				<td>
					<div style="text-align: right;">
						<font><%=message.getSendTime().substring(5)%></font>&nbsp;&nbsp; <%if(isRead==0) { i++;%><span class="badge" style="background-color:#C35839;">1</span><%} %>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<%} %>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">From:&nbsp;<font color="#337ab7"></font></h4>
				</div>
				<div class="modal-body">

					<div class="form-group" id="form-content">
					</div>
					
					<img id="picture" src="" width="80%" />
					
					
					<hr>
					<div class="form-group" id="reply-content">
					<label for="message-text" class="control-label">回复内容:</label>
					<textarea class="form-control" id="message-text" name="message" ></textarea>
					</div>
					<!--  
					<div>
					<input id="upload" type="file" style="display:none">
                    <button type="button" class="btn btn-info"  onclick="$('input[id=upload]').click();" >添加图片</button>
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
						<input type="button" class="btn btn-default" data-dismiss="modal"
							value="取消"> <button type="button" class="btn btn-primary"
							id="send" value="确认"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>回复
							</button>
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
				<br>
				<span class="return_msg"></span><span class="glyphicon glyphicon-ok"></span><br>
				<br>
			</div>
		</div>
	</div>
	
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="js/lrz.mobile.min.js"></script>
<script type="text/javascript">
//将消息置为已读
function ReadMessage(id) {
	$.ajax({
		type : 'GET',
		url : "tagRead?id="+id,
		success : function(msg) {
		}
	});
}


$(document).ready(function() {
 	var basePath = window.location.protocol+"//"+window.location.host;
   	var pic_server_url = basePath+"/Pictures/";
	var unReadMessage = <%=i%>;
	$("title").html("消息盒子("+unReadMessage+"条未读消息！)");
	
	
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
	
	
	$("div.bgfff").click(function() {
		$(this).find("span.badge").remove();
		var id = $(this).attr("id");
		ReadMessage(id);
		var fromNickname = $(this).find("font#nickname").text();
		var content = $(this).find("div#content").text();
		/* var pictureUrl = $(this).find("div#picture").text();
		if(pictureUrl=='null') {
			$("img#picture").attr("src","");
		}
		else{
			$("img#picture").attr("src",pic_server_url+"images/upload/"+pictureUrl);
		} */
		var fromOpenId = $(this).find("input#fromOpenId").val();
		$("h4.modal-title").children("font").html(fromNickname);
		$("div#form-content").html(content);
		$("#myModal").modal("show");
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
						"toOpenId" : fromOpenId,
						"toNickName" : fromNickname,
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
	
});

</script>


</html>