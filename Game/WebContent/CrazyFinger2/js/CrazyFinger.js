$(document).ready(function() {

	var flag = 0;// 是否点击开始游戏
	var count = 1000;// 计时器(1000*10ms==10s)
	var total = 0;// 点击次数
	var msg = "";

	if (localStorage.max2) {
		$("div#best").html("最好成绩：" + localStorage.max2 + " 次");
	}

	function ticker() {
		if (count <= 0) {
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
			total++;
			$("#result").html(total + " 次");
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