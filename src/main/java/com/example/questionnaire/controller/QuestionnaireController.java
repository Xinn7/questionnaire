package com.example.questionnaire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.service.ifs.QuestionnaireService;
import com.example.questionnaire.vo.CountRes;
import com.example.questionnaire.vo.QuestionnaireReq;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.RespondentReq;

@CrossOrigin
@RestController
public class QuestionnaireController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@PostMapping(value = "/api/createQuestionnaire")
	public QuestionnaireRes createQuestionnaire(@RequestBody QuestionnaireReq req) {
		return questionnaireService.createQuestionnaire(req);
	}
	
	@PostMapping(value = "/api/createQuestionInfo")
	public QuestionnaireRes createQuestionInfo(@RequestBody List<QuestionnaireReq> reqList) {
		return questionnaireService.createQuestionInfo(reqList);
	}
	
	@PostMapping(value = "/api/getAllQuestionnaire")
	public QuestionnaireRes getAllQuestionnaire(@RequestBody QuestionnaireReq req) {
		return questionnaireService.getAllQuestionnaire(req);
	}
	
	@PostMapping(value = "/api/findQuestionnaire")
	public QuestionnaireRes findQuestionnaire(@RequestBody QuestionnaireReq req) {
		return questionnaireService.findQuestionnaire(req);
	}
	
	@PostMapping(value = "/api/getQuestionsDetails")
	public QuestionnaireRes getQuestionsDetails(@RequestBody QuestionnaireReq req) {
		return questionnaireService.getQuestionsDetails(req);
	}
	
	@PostMapping(value = "/api/respondent")
	public QuestionnaireRes respondent(@RequestBody RespondentReq req) {
		return questionnaireService.respondent(req);
	}
	
	@PostMapping(value = "/api/dataCount")
	public CountRes dataCount(@RequestBody QuestionnaireReq req) {
		return questionnaireService.dataCount(req);
	}
}
