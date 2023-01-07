package com.example.questionnaire.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "qu_id")
	private int questionnaireId;

	@Column(name = "qu_name")
	private String questionnaireName;

	@Column(name = "qu_content")
	private String questionnaireContent;

	@Column(name = "start_time")
	private LocalDate startTime;

	@Column(name = "end_time")
	private LocalDate endTime;

	public Questionnaire() {

	}

	public Questionnaire(String questionnaireName, String questionnaireContent, LocalDate startTime, LocalDate endTime) {
		this.questionnaireName = questionnaireName;
		this.questionnaireContent = questionnaireContent;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
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
}
