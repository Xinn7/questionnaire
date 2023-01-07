package com.example.questionnaire.service.ifs;

import java.util.List;

import com.example.questionnaire.vo.CountRes;
import com.example.questionnaire.vo.QuestionnaireReq;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.RespondentReq;

public interface QuestionnaireService {

	public QuestionnaireRes createQuestionnaire(QuestionnaireReq req);

	public QuestionnaireRes createQuestionInfo(List<QuestionnaireReq> reqList);

	public QuestionnaireRes getAllQuestionnaire(QuestionnaireReq req);

	public QuestionnaireRes findQuestionnaire(QuestionnaireReq req);

	public QuestionnaireRes getQuestionsDetails(QuestionnaireReq req);

	public QuestionnaireRes respondent(RespondentReq req);

	public CountRes dataCount(QuestionnaireReq req);

}
