package com.example.questionnaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Respondent;

@Repository
public interface RespondentDao extends JpaRepository<Respondent, Integer>{

	public List<Respondent> findByQuestionnaireId(int questionnaireId);
}
