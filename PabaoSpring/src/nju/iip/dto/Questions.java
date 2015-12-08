package nju.iip.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 量表题目对应的JavaBean
 * @author wangqiang
 *
 */
public class Questions {
	
	String questionContent;//题目内容
	
	String showType;//题目类型
	
	int questionId;//题目id
	
	int totalScaleId;//所属量表id
	
	List<Options> answers = new ArrayList<Options>();//题目的选项
	
	public int getTotalScaleId() {
		return totalScaleId;
	}

	public void setTotalScaleId(int totalScaleId) {
		this.totalScaleId = totalScaleId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public List<Options> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Options> options) {
		this.answers.addAll(options);
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	

}
