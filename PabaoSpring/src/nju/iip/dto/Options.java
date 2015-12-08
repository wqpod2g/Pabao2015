package nju.iip.dto;

/**
 * 量表题目选项对应的JavaBean
 * @author wangqiang
 *
 */
public class Options {
	
	String optionContent;//选项内容
	
	String optionValue;//选项对应的分值

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
}
