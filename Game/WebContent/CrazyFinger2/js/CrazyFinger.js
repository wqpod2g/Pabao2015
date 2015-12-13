$(document).ready(function() {

	var flag = 0;// 是否点击开始游戏
	var count = 1000;// 计时器(1000*10ms==10s)
	var total = 0;// 点击次数
	var left_hit = 0;
	var right_hit = 0;
	var msg = "";

	if (localStorage.max2) {
		$("div#best").html("最好成绩：" + localStorage.max2 + " 次");
	}

	function ticker() {
		if (count <= 0) {
			total = left_hit+right_hit;
			$('#result_panel').show();
			clearInterval(counter);
			if (!localStorage.max2 || parseInt(localStorage.max2) < total) {
				localStorage.max2 = total;
				$("#best").html("最好成绩：" + localStorage.max2 + " 次");
			}
			$.ajax({
				type : "POST",
				url : "saveScore.do",
				data : {
					"score" : total,
					"game" : "CrazyFinger2"
				},
				success : function(msg) {
					var obj = JSON.parse(msg); // 由JSON字符串转换为JSON对象
					var data = obj.score;
					 $('#ranking table').html('');
						$('#beats').html("<font color='white' size='4'>当前排名:"+obj.rank+",击败了"+obj.beat+"的玩家!</font>");
					for(var i = 0;i<data.length;i++){
          	        	 $('#ranking table').append("<tr><td><img src='"+ data[i].headImgUrl +"'></img></td><td>"+ data[i].nickname +"</td><td>"+ data[i].score +"</td></tr>");
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
		$("span#timer").html(res.toPrecision(count.toString().length) + "秒");
	}

	$(document).on("touchmove", function(e) {
		e.preventDefault();
	});

	$("#button1").on("touchstart", function() {
		if (!flag) {
			flag = 1;
			counter = setInterval(ticker, 10);
		}
		this.classList.add("active");
	});

	$("#button1").on("touchend", function() {
		if (flag) {
			left_hit++;
			$("#result_left").html(left_hit);
		}
		this.classList.remove("active");
	});
	
	$("#button2").on("touchstart", function() {
		if (!flag) {
			flag = 1;
			counter = setInterval(ticker, 10);
		}
		this.classList.add("active");
	});

	$("#button2").on("touchend", function() {
		if (flag) {
			right_hit++;
			$("#result_right").html(right_hit);
		}
		this.classList.remove("active");
	});

	function reset() {
		count = 1000;
		total = 0;
		flag = 0;
		right_hit = 0;
		left_hit = 0;
		$('#result_left').html(0 + ' 次');
		$('#result_right').html(0 + ' 次');
		$('#timer').html(10 + ' 秒');
	}

	$('#reset').click(function() {
		reset();
		$('#result_panel').hide();
	});

});