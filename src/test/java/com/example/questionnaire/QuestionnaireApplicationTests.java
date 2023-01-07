package com.example.questionnaire;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.repository.QuestionnaireDao;

@SpringBootTest
class QuestionnaireApplicationTests {
	
	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void test() {
//		String str = "1=[A, C], 2=[A, B]";
//		List<String> ansList = new ArrayList<>();
//		String[] tokens = str.substring(0, str.length() - 1).split("],");
//		for(String token : tokens) {
//			String newStr = token.trim();
//			System.out.println(newStr);
//			String[] a = newStr.split("=");
//			System.out.println(a[0]);
//			System.out.println(a[1].substring(1));
//			ansList.add(a[1].substring(1));
//		}
//		System.out.println(ansList);
//		System.out.println(ansList.toString().split(",").length);
		
		String str = "A, C, A, B";
		String[] tokens = str.split(",");
		List<String> ansList = new ArrayList<>();
		for(String item : tokens) {
			ansList.add(item.trim());
		}
		
		String str2 = "A, B, C, D";
		String[] tokens2 = str2.split(",");
		List<String> ansList2 = new ArrayList<>();
		for(String item : tokens2) {
			ansList2.add(item.trim());
		}
		
		Map<Object, Long> a = ansList.stream().collect(Collectors.groupingBy(k->k, Collectors.counting()));
		
		System.out.println(a);
		
		long sum = 0;
		for (String item : ansList2) {
			if (a.containsKey(item)) {
				sum += a.get(item).intValue();
			}
		}
		System.out.println(sum);
		for (String item : ansList2) {
			System.out.println(item);
			if (a.containsKey(item)) {
				System.out.println(a.get(item).intValue());
				NumberFormat nt = NumberFormat.getPercentInstance();
				nt.setMinimumFractionDigits(0);
				float fl = (float)a.get(item)/sum;
				System.out.println(fl);
				System.out.println(nt.format(fl));
			}
		}
		
		
	}
	
	@Test
	public void pageTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "questionnaireId");
		Pageable pageable = PageRequest.of(1, 3, sort);
		
		Page<Questionnaire> page = questionnaireDao.findAll(pageable);
		
		List<Questionnaire> list = page.getContent();
		
		for(Questionnaire item : list) {
			System.out.println(item.getQuestionnaireId());
			System.out.println(page.getNumber() + "­¶");
		}
		
//		Page<Questionnaire> pageResult = questionnaireDao
//				.findAll(PageRequest.of(req.getPage(), 10, Sort.by("questionnaireId").descending()));
//
//		List<Questionnaire> allQuestionnaire = pageResult.getContent();
	}

	@Test
	public void pageTest2() {
		int page = 2;
		List<Questionnaire> list = questionnaireDao.findAll();
		int pageTotal = list.size()/2;
		for(int i = 1 ; i < (pageTotal + 1) ; i++) {
			System.out.println(i);
		}
		List<Questionnaire> subList = list.stream().skip((page - 1) * 2).limit(2).collect(Collectors.toList());
		
		for(Questionnaire item : subList) {
			System.out.println(item.getQuestionnaireName());
		}
	}

}
