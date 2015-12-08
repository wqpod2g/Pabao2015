<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title></title>

<link rel="stylesheet" type="text/css" href="css/style.css" />

<style type="text/css">
.demo {
	width: 100%;
	margin: 10px auto 10px auto
}
</style>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/quiz.js"></script>
<link rel="stylesheet" type="text/css" href="css/base.css">
<link rel="stylesheet" type="text/css" href="css/common.css">

<%
	String str = (String) request.getAttribute("questions");
%>


<script type="text/javascript">

    var init =<%=str%>;
	//var init = {"scale":{"id":"109","scaleName":"简短精神状态量表(MMSE)","scaleDescription":"<p>本检查要求在10分钟内完成 ；第5题和第3题应间隔3分钟。<\/p>\r\n<p>评定总分共30分，划分痴呆标准：文盲≤17分，小学程度≤20分，中学程度（包括中专）≤22分，大学程度（包括大专）≤23分。<\/p>\r\n<p><\/p>","shortname":"MMSE"},"questions":[{"questionId":1104,"questionContent":"今年是哪一年？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1105,"questionContent":"现在是什么季节？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1106,"questionContent":"现在是几月份？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1107,"questionContent":"今天是几号？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1108,"questionContent":"今天是星期几？","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1110,"questionContent":"咱们现在是在哪个城市？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1111,"questionContent":"咱们现在是在哪个区？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1112,"questionContent":"咱们现在是在什么街？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1113,"questionContent":"现在是在那个医院？\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1114,"questionContent":"这里是第几层楼？","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1116,"questionContent":"树\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1117,"questionContent":"钟\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1118,"questionContent":"汽车","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1120,"questionContent":"第一次\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1121,"questionContent":"第二次\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1122,"questionContent":"第三次\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1123,"questionContent":"第四次\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1124,"questionContent":"第五次","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1126,"questionContent":"树\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1127,"questionContent":"钟\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1128,"questionContent":"汽车","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1130,"questionContent":"手表\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1131,"questionContent":"钢笔","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1132,"questionContent":"请你跟我说\u201c瑞雪兆丰年\u201d","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"radio"},{"questionId":1134,"questionContent":"用右手拿着这张纸\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1135,"questionContent":"用两只手把它对折起来\r","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1136,"questionContent":"放在您的左腿上","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"matrix"},{"questionId":1137,"questionContent":"请您念念这句话，并按上面的意思去做<br />\r\n\u201c闭上您的眼睛\u201d","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"radio"},{"questionId":1138,"questionContent":"请您给我写一个完整的句子(不可以写名字)","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"radio"},{"questionId":1139,"questionContent":"（出示图案）请您照着这个样子画下来<br />\r\n<br />\r\n","answers":[{"optionContent":"0","optionValue":"0"},{"optionContent":"1","optionValue":"1"}],"showType":"radio"}]}

	if (init != null) {
		$("title").html(init.scale.scaleName);
	}
	$(function() {
		$('#quiz-container').jquizzy(init);
	});
</script>



</head>
<body>

	<div class="demo">
		<div id='quiz-container'></div>
	</div>

	<div class="fix_footer" id="fix_footer"
		style="left: 0px; color: rgb(255, 255, 255); height: 55px; width: 100%; position: fixed; bottom: 0%; font-size: 14px; display: block; background-color:rgb(51, 55, 57);">
		<table width="100%" height="100%" border="0"
			style="border-collapse: collapse;">
			<tbody>
				<tr>
					<td id="fix_footer_menu" align="center"><span id="now_an"></span><br>
						已答题目</td>
					<td id="fix_footer_menu" align="center"><span id="all_an"></span><br>
						题目总数</td>
					<td id="fix_footer_menu" align="center"><span id="now_time"
						data-src="21"></span><br> 答题用时</td>
				</tr>
			</tbody>
		</table>
	</div>


</body>
</html>
