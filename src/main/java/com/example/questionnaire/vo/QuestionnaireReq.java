package com.example.questionnaire.vo;

import java.time.LocalDate;
import java.util.List;

public class QuestionnaireReq {
	
	private int page;

	private String questionnaireName;

	private String questionnaireContent;

	private LocalDate startTime;

	private LocalDate endTime;

	// ========Question============

	private int questionnaireId;

	private String question;

	private String optionsString;

	private String opType;

	private boolean must;

	private List<QuestionnaireReq> reqList;

	public QuestionnaireReq() {

	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getQuestionnaireContent() {
		return questionnaireContent;
	}

	public void setQuestionnaireContent(String questionnaireContent) {
		this.questionnaireContent = questionnaireContent;
	}

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptionsString() {
		return optionsString;
	}

	public void setOptionsString(String optionsString) {
		this.optionsString = optionsString;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public boolean isMust() {
		return must;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

	public List<QuestionnaireReq> getReqList() {
		return reqList;
	}

	public void setReqList(List<QuestionnaireReq> reqList) {
		this.reqList = reqList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
