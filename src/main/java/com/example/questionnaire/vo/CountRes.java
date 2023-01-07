package com.example.questionnaire.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountRes {

	private List<CountRes> questionList;

	private String question;

	private String op;

	private int opData;

	private String percent;
	
	private List<CountRes> opInfoList;

	public CountRes() {

	}

	public CountRes(List<CountRes> questionList) {
		this.questionList = questionList;
	}

	public List<CountRes> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<CountRes> questionList) {
		this.questionList = questionList;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getOpData() {
		return opData;
	}

	public void setOpData(int opData) {
		this.opData = opData;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public List<CountRes> getOpInfoList() {
		return opInfoList;
	}

	public void setOpInfoList(List<CountRes> opInfoList) {
		this.opInfoList = opInfoList;
	}
}
