$(document)
		.ready(
				function() {

					var flag = 0;// 是否点击开始游戏
					var count = 1000;// 计时器(1000*10ms==10s)
					var total = 0;// 点击次数
					var msg = "";

					if (localStorage.max1) {
						$("div#best").html("最好成绩：" + localStorage.max1 + " 次");
					}

					function ticker() {
						if (count <= 0) {
							$('#result_panel').show();
							clearInterval(counter);
							if (!localStorage.max1
									|| parseInt(localStorage.max1) < total) {
								localStorage.max1 = total;
								$("#best").html(
										"最好成绩：" + localStorage.max1 + " 次");
							}
							$.ajax({
								type : "POST",
								url : "saveScore.do",
								data : {
									"score" : total,
									"game" : "CrazyFinger1"
								},
								success : function(msg) {
									var obj = JSON.parse(msg); // 由JSON字符串转换为JSON对象
									var data = obj.score;
									 $('#ranking table').html('');
										$('#beats').html("<font color='white' size='4'>当前排名:"+obj.rank+",击败了"+obj.beat+"的玩家!</font>");
									for ( var i = 0; i < data.length; i++) {
										$('#ranking table').append(
												"<tr><td><img src='"
														+ data[i].headImgUrl
														+ "'></img></td><td>"
														+ data[i].nickname
														+ "</td><td>"
														+ data[i].score
														+ "</td></tr>");
									}
								}
							});
							return;
						}
						count--;
						displayCount(count);
					}

					function displayCount(count) {
						var res = count / 100;
						$("span#timer").html(
								res.toPrecision(count.toString().length) + "秒");
					}

					$(document).on("touchmove", function(e) {
						e.preventDefault();
					});

					$("#button").on("touchstart", function() {
						if (!flag) {
							flag = 1;
							counter = setInterval(ticker, 10);
						}
						this.classList.add("active");
					});

					$("#button").on("touchend", function() {
						if (flag) {
							total++;
							$("#result").html(total + " 次");
						}
						this.classList.remove("active");
					});

					function reset() {
						count = 1000;
						total = 0;
						flag = 0;
						$('#result').html(total + ' 次');
						$('#timer').html(10 + ' 秒');
					}

					$('#reset').click(function() {
						reset();
						$('#result_panel').hide();
					});

				});