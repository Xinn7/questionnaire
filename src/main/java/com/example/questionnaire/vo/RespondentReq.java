package com.example.questionnaire.vo;

import java.util.List;
import java.util.Map;

public class RespondentReq {

	private int questionnaireId;

	private String name;

	private String phone;

	private String email;

	private int age;

	private String gender;

	private Map<Object, List<String>> answer;

	public RespondentReq() {

	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
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

	public Map<Object, List<String>> getAnswer() {
		return answer;
	}

	public void setAnswer(Map<Object, List<String>> answer) {
		this.answer = answer;
	}

}
