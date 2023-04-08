package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.QuestionInfo;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.Respondent;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireRes {
	
	private int pageNum;

	private String message;

	private Questionnaire questionnaire;

	private String status;

	private List<QuestionnaireRes> questionnaireResList;

	// ============Question================

	private List<QuestionInfo> questionInfoList;

	private String question;

	private List<String> opList;

	private String opType;

	// =============Respondent==============

	private Respondent respondent;

	public QuestionnaireRes() {

	}

	public QuestionnaireRes(String message) {
		this.message = message;
	}

	public QuestionnaireRes(Questionnaire questionnaire, String message) {
		this.questionnaire = questionnaire;
		this.message = message;
	}
	
	public QuestionnaireRes(List<QuestionnaireRes> questionnaireResList, List<QuestionInfo> questionInfoList,
			String message) {
		this.questionnaireResList = questionnaireResList;
		this.questionInfoList = questionInfoList;
		this.message = message;
	}

	public QuestionnaireRes(List<QuestionnaireRes> questionnaireResList, List<QuestionInfo> questionInfoList,
			String message, int pageNum) {
		this.questionnaireResList = questionnaireResList;
		this.questionInfoList = questionInfoList;
		this.message = message;
		this.pageNum = pageNum;
	}

	public QuestionnaireRes(Respondent respondent, String message) {
		this.respondent = respondent;
		this.message = message;
	}

	public QuestionnaireRes(Questionnaire questionnaire, String status, List<QuestionnaireRes> questionnaireResList, String message) {
		this.questionnaire = questionnaire;
		this.status = status;
		this.questionnaireResList = questionnaireResList;
		this.message = message;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<QuestionnaireRes> getQuestionnaireResList() {
		return questionnaireResList;
	}

	public void setQuestionnaireResList(List<QuestionnaireRes> questionnaireResList) {
		this.questionnaireResList = questionnaireResList;
	}

	public List<QuestionInfo> getQuestionInfoList() {
		return questionInfoList;
	}

	public void setQuestionInfoList(List<QuestionInfo> questionInfoList) {
		this.questionInfoList = questionInfoList;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getOpList() {
		return opList;
	}

	public void setOpList(List<String> opList) {
		this.opList = opList;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public Respondent getRespondent() {
		return respondent;
	}

	public void setRespondent(Respondent respondent) {
		this.respondent = respondent;
	}
}
