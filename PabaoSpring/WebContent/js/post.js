$(document).ready(function() {
			
	       	var basePath = window.location.protocol+"//"+window.location.host;
	       	var pic_server_url = basePath+"/Pictures/"
			var count = 1;
			
			var winHeight = $(window).height();
			 // alert(winHeight);
		        var nScrollTop = 0;   // 滚动到的当前位置
		        $(document).scroll(function(){
		          var docHeight = $(document).height();
		          nScrollTop = $(this).scrollTop();
		          if(nScrollTop>=docHeight-winHeight) {
		        	  $.ajax({
							type : 'POST',
							url : "GetMorePosts",
							data : {
								"count" : count
							},
							success : function(msg) {
								count++;
								var obj = JSON.parse(msg); // 由JSON字符串转换为JSON对象
								addPost(obj.post);
							}
		        	  });
		          }
		          

		           
		          });
		        
		        // 将新获取的帖子添加到页面中
		        function addPost(post_list) {
		        	var length = post_list.length;
		        	if(length===0) {
		        		$("div#refresh").text("帖子已全部加载");
		        	}
		        	var content = "";
		        	for(var i=0;i<length;i++) {
		        		var postHtml = '<div class="bgfff form ov" id='+post_list[i].id+'>';
		        		postHtml = postHtml+'<div class="fb"><font size="3.5px">'+post_list[i].title+'</font></div>';
		        		postHtml = postHtml+'<hr style="border: 0; height: 0.1px;" /><div>';
		        		if(post_list[i].pictureUrl.length!=0) {
		        			postHtml = postHtml+'<span class="glyphicon glyphicon-picture" aria-hidden="true"></span>';
		        		}
		        		postHtml = postHtml+'<font size="3px">'+post_list[i].content+'</font></div><hr/>';
		        		
		        		postHtml = postHtml+'<div><table width="100%"><tr>';
		        		
		        		postHtml = postHtml+'<td width="60%"><font size="2px" color="#337ab7">'+post_list[i].author+'</font>&nbsp;&nbsp;<fontsize="1.5px" color="#C8C6C6">'+post_list[i].postTime.substr(5)+'</font></td>'
		        		
		        		postHtml = postHtml+'<td style="text-align: right;"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>&nbsp;';
		        		
		        		postHtml = postHtml+'<font size="3px" color="#C8C6C6">'+post_list[i].love+'</font>&nbsp;&nbsp;';
		        		
		        		postHtml = postHtml+'<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>&nbsp;';
		        		
		        		postHtml = postHtml+'<font size="3px" color="#C8C6C6">'+post_list[i].reply+'</font>&nbsp;&nbsp;';
		        		postHtml = postHtml+'</td></tr></table></div></div>';
		        		content = content+postHtml;
		        	}
		        	$("div#contain").append(content);
		        	 $("div.bgfff").click(function() {
			  				var postId = $(this).attr("id");
			  				location.href = "ShowPost?id=" + postId;
			  		});
		        	
		        }
			

			$("div.bgfff").click(function() {
				var postId = $(this).attr("id");
				location.href = "ShowPost?id=" + postId;
			});
			
			
			 var reader = new FileReader();
			    var picture = '';
			    var pictureUrl = "";
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
			/*
			 * reader.onload = function(e){ $("img#pic").attr('src',
			 * e.target.result).addClass("img-thumbnail"); };
			 */
			    	 
			    	
			    
			    $("input#send").click(function() {
					var title = $("input#message-title").val();
					var content = $("textarea#message-text").val();
					if (title == "") {
						$("#form-title").addClass("has-error");
						$("#message-title").attr("placeholder", "请输入标题");
					}
					if (content == "") {
						$("#form-content").addClass("has-error");
						$("#message-text").attr("placeholder", "请输入内容");
					}
					if (title != "" && content != "") {
						
						$("div#progress").addClass("progress");
						$("div#progress-bar").addClass("progress-bar");
						$("div#progress-bar").html("10%");
						
						$.ajax({
							type : 'POST',
							url : "SavePost",
							data : {
								"title" : title,
								"content" : content,
								"pictureUrl" : pictureUrl
							},
							success : function(msg) {
								$("div#progress-bar").html("100%").attr("aria-valuenow","100").attr("style","width:100%;");
								setTimeout(function() {
								$("#myModal").modal('hide');
								$("span.return_msg").html("<br><br>" + msg);
								$(".bs-example-modal-sm").modal('show');
								setTimeout(function() {
									$(".bs-example-modal-sm").modal('hide');
									location.href = "post_list.jsp";
								}, 2000);
								},500);
							}
						});
					}
				});
			
			
		});