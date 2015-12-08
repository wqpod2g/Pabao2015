(function($) {
    $.fn.jquizzy = function(settings) {
        var defaults = {
            questions: null,
            startImg: 'images/start.gif',
            endText: '已结束!',
            shortURL: null,
            sendResultsURL: "SaveEvaluateResult",
            resultComments: {
                perfect: '你是爱因斯坦么?',
                excellent: '非常优秀!',
                good: '很好，发挥不错!',
                average: '一般般了。',
                bad: '太可怜了！',
                poor: '好可怕啊！',
                worst: '悲痛欲绝！'
            }
        };
        
        
        
        if (settings === null) {
            $(this).html('<div class="intro-container slide-container"><h2 class="qTitle">Failed to parse questions.</h2></div>');
            return;
        }
        
        var config = $.extend(defaults, settings);
        
        $("#all_an").html(config.questions.length);
        $("#now_an").html(0);
        
        var superContainer = $(this),
        answers = [],
        introFob = '<div class="intro-container slide-container">'+config.scale.scaleDescription+'<br/><br/><a class="nav-start" href="#"><span><img src="'+config.startImg+'"></span></a></div>	',
        exitFob = '<div class="results-container slide-container"><div class="question-number">' + config.endText + '</div><div class="result-keeper"></div></div><div class="notice">请选择一个选项！</div>',
        contentFob = '',
        questionsIteratorIndex,
        answersIteratorIndex;
        superContainer.addClass('main-quiz-holder');
     
       
        
        for (questionsIteratorIndex = 0; questionsIteratorIndex < config.questions.length; questionsIteratorIndex++) {
        	if(config.questions[questionsIteratorIndex].showType==='pic') {
       		 contentFob += '<div class="slide-container"><div class="question">' + '<p class="a_img"><img src="'+config.questions[questionsIteratorIndex].questionContent+'" style="width:100%"></p>'+'</div><ul class="answers">';
          	}
        	else {
       		 contentFob += '<div class="slide-container"><div class="question">' + (questionsIteratorIndex+1)+'.'+config.questions[questionsIteratorIndex].questionContent + '</div><ul class="answers">';
        	}
        	for (answersIteratorIndex = 0; answersIteratorIndex < config.questions[questionsIteratorIndex].answers.length; answersIteratorIndex++) {
                contentFob += '<li>' + config.questions[questionsIteratorIndex].answers[answersIteratorIndex].optionContent + '</li>';
            }
            contentFob += '</ul><div class="nav-container">';
            if (questionsIteratorIndex !== 0) {
                contentFob += '<div class="prev"><a class="nav-previous" href="#">&lt; 上一题</a></div>';
            }
            if (questionsIteratorIndex < config.questions.length - 1) {
                contentFob += '<div class="next"><a class="nav-next" href="#">下一题 &gt;</a></div>';
            } else {
                contentFob += '<div class="next final"><a class="nav-show-result" href="#">完成</a></div>';
            }
            contentFob += '</div></div>';
            answers.push(config.questions[questionsIteratorIndex].correctAnswer);
        }
        
        
        superContainer.html(introFob + contentFob + exitFob);
        var progress = superContainer.find('.progress'),
        progressKeeper = superContainer.find('.progress-keeper'),
        notice = superContainer.find('.notice'),
        progressWidth = progressKeeper.width(),
        userAnswers = [],
        questionLength = config.questions.length,
        slidesList = superContainer.find('.slide-container'),
        score;
        
        //获取用户最终得分
        function getScore() {
        	var score = 0;
        	for(var i=0;i< userAnswers.length;i++) {
        		score = score+Number(userAnswers[i]);
        	}
        	return score;
        }
        
        
        
        function checkAnswers() {
            var resultArr = [],
            flag = false;
            for (var i = 0; i < answers.length; i++) {
                if (answers[i] == userAnswers[i]) {
                    flag = true;
                } else {
                    flag = false;
                }
                resultArr.push(flag);
            }
            return resultArr;
        }
        
        
        function roundReloaded(num, dec) {
            var result = Math.round(num * Math.pow(10, dec)) / Math.pow(10, dec);
            return result;
        }
        
        
        function judgeSkills(score) {
            //var returnString;
            if (score === 100) return config.resultComments.perfect;
            else if (score > 90) return config.resultComments.excellent;
            else if (score > 70) return config.resultComments.good;
            else if (score > 50) return config.resultComments.average;
            else if (score > 35) return config.resultComments.bad;
            else if (score > 20) return config.resultComments.poor;
            else return config.resultComments.worst;
        }
        
        progressKeeper.hide();
        notice.hide();
        slidesList.hide().first().fadeIn(200);
       
        superContainer.find('li').click(function() {
            var thisLi = $(this);
            if (thisLi.hasClass('selected')) {
                thisLi.removeClass('selected');
            } else {
                thisLi.parents('.answers').children('li').removeClass('selected');
                thisLi.addClass('selected');
            }
        });
        
        var s=0;
        function startTime()
        {
        $("#now_time").html(s+"秒");
        s = s+1;
        setTimeout(startTime,1000);
        }
        
        superContainer.find('.nav-start').click(function() {
            $(this).parents('.slide-container').fadeOut(200,
            function() {
                $(this).next().fadeIn(200);
                startTime();
                progressKeeper.fadeIn(200);
                
            });
            return false;
        });
       
        var count = 0;
        superContainer.find('.next').click(function() {
            if ($(this).parents('.slide-container').find('li.selected').length === 0) {
                notice.fadeIn(200);
                return false;
            }
            $("#now_an").html(count+1);
        	count = count+1;
            notice.hide();
            $(this).parents('.slide-container').fadeOut(200,
            function() {
                $(this).next().fadeIn(500);
            });
            progress.animate({
                width: progress.width() + Math.round(progressWidth / questionLength)
            },
            500);
            return false;
        });
    
        
        
        superContainer.find('.prev').click(function() {
            notice.hide();
            $(this).parents('.slide-container').fadeOut(200,
            function() {
                $(this).prev().fadeIn(200);
            });
            progress.animate({
                width: progress.width() - Math.round(progressWidth / questionLength)
            },
            500);
            return false;
        });
      
        
        superContainer.find('.final').click(function() {
            if ($(this).parents('.slide-container').find('li.selected').length === 0) {
                notice.fadeIn(200);
                return false;
            }
            superContainer.find('li.selected').each(function(index) {
            	var optionindex = $(this).parents('.answers').children('li').index($(this).parents('.answers').find('li.selected'));
                userAnswers.push(config.questions[index].answers[optionindex].optionValue);
            });
            if (config.sendResultsURL !== null) {
                var collate = [];
                for (var r = 0; r < userAnswers.length; r++) {
                    collate.push('{"questionNumber":"' + parseInt(r + 1, 10) + '", "userAnswer":"' + userAnswers[r] + '"}');
                }
                score = getScore();
                $.ajax({
                    type: 'POST',
                    url: config.sendResultsURL,
                    data: {"answers":collate.join(","),"score":score},
                    success: function(msg){
                        alert(msg+"!");
                    }
                });
            }
            progressKeeper.hide();
            var results = checkAnswers(),
            resultSet = '',
            trueCount = 0,
            shareButton = '';
            if (config.shortURL === null) {
                config.shortURL = window.location;
            };
            for (var i = 0, toLoopTill = results.length; i < toLoopTill; i++) {
                if (results[i] === true) {
                    trueCount++;
                    isCorrect = true;
                }
                resultSet += '<div class="result-row">' + (Number(userAnswers[i])<2 ? "<div class='correct'>#"+(i + 1)+"<span></span></div>": "<div class='wrong'>#"+(i + 1)+"<span></span></div>");
                resultSet += '<div class="resultsview-qhover">' + config.questions[i].questionContent;
                resultSet += "<ul>";
                for (answersIteratorIndex = 0; answersIteratorIndex < config.questions[i].answers.length; answersIteratorIndex++) {
                    var classestoAdd = '';
                    if (config.questions[i].correctAnswer == answersIteratorIndex + 1) {
                        classestoAdd += 'right';
                    }
                    if (userAnswers[i] == answersIteratorIndex + 1) {
                        classestoAdd += ' selected';
                    }
                    resultSet += '<li class="' + classestoAdd + '">' + config.questions[i].answers[answersIteratorIndex].optionContent + '</li>';
                }
                resultSet += '</ul></div></div>';
            }
        //    score = roundReloaded(trueCount / questionLength * 100, 2);
            
            resultSet = '<h2 class="qTitle">' + 'finish!' + '<br/> 您的分数： ' + score + '</h2>' + shareButton + '<div class="jquizzy-clear"></div>' + resultSet + '<div class="jquizzy-clear"></div>';
            var $params="username="+"wq"+"&password="+"123";
            /*$.ajax({
            	 type : "POST",
                 url: "GetAnswerServlet",
                 data: {"score":score,"username":"wq","password":123},
                 success: function(msg){
                     alert(msg+"ok");
                 }
            });*/
            
            superContainer.find('.result-keeper').html(resultSet).show(500);
            superContainer.find('.resultsview-qhover').hide();
            superContainer.find('.result-row').hover(function() {
                $(this).find('.resultsview-qhover').show();
            },
            function() {
                $(this).find('.resultsview-qhover').hide();
            });
            
            $(this).parents('.slide-container').fadeOut(200,
            function() {
                $(this).next().fadeIn(200);
            });
            
            return false;
        });
        
    };
})(jQuery);