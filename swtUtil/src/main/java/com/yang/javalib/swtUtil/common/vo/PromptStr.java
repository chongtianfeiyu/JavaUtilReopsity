package com.yang.javalib.swtUtil.common.vo;

/**
 * @Description: 提示对话框,在两个线程中传递信息的字符类
 *  因为,只有 final 类才可以 传入一线程中, 里面的元素可以改变
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-24 下午6:08:35 
 */
public class PromptStr {

	private String promptString = "";

	public String getPromptString() {
		return promptString;
	}

	public void setPromptString(String promptString) {
		this.promptString = promptString;
	}

}
