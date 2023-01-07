package com.example.questionnaire.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "respondent")
public class Respondent {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name ="respondent_id")
	private int respondentId;
	
	@Column(name ="questionnaire_Id")
	private int questionnaireId;
	
	@CreationTimestamp
	@Column(name ="create_time")
	private LocalDateTime createTime;
	
	@Column(name ="name")
	private String name;
	
	@Column(name ="phone")
	private String phone;
	
	@Column(name ="email")
	private String email;
	
	@Column(name ="age")
	private int age;
	
	@Column(name ="gender")
	private String gender;
	
	@Column(name ="answer")
	private String answer;
	
	public Respondent() {
		
	}

	public Respondent(int questionnaireId, LocalDateTime createTime, String name, String phone, String email,
			int age, String gender, String answer) {
		this.questionnaireId = questionnaireId;
		this.createTime = createTime;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.answer = answer;
	}

	public int getRespondentId() {
		return respondentId;
	}

	public void setRespondentId(int respondentId) {
		this.respondentId = respondentId;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
