package com.example.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_info")
public class QuestionInfo {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "question_id")
	private int questionId;

	@Column(name = "questionnaire_id")
	private int questionnaireId;

	@Column(name = "question")
	private String question;

	@Column(name = "options")
	private String op;

	@Column(name = "options_type")
	private String opType;

	@Column(name = "must")
	private boolean must;

	@Column(name = "op_data")
	private String opData;

	public QuestionInfo() {

	}

	public QuestionInfo(int questionnaireId, String question, String op, String opType, boolean must) {
		this.questionnaireId = questionnaireId;
		this.question = question;
		this.op = op;
		this.opType = opType;
		this.must = must;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
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

	public String getOpData() {
		return opData;
	}

	public void setOpData(String opData) {
		this.opData = opData;
	}
}
