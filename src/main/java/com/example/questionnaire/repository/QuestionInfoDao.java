package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.QuestionInfo;

@Repository
public interface QuestionInfoDao extends JpaRepository<QuestionInfo, Integer>{
	
	public List<QuestionInfo> findByQuestionnaireId(int questionnaireId);

}
