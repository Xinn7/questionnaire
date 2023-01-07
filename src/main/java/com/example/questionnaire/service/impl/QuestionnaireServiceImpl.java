package com.example.questionnaire.service.impl;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.questionnaire.constants.QuestionnaireRtnCode;
import com.example.questionnaire.entity.QuestionInfo;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.entity.Respondent;
import com.example.questionnaire.repository.QuestionInfoDao;
import com.example.questionnaire.repository.QuestionnaireDao;
import com.example.questionnaire.repository.RespondentDao;
import com.example.questionnaire.service.ifs.QuestionnaireService;
import com.example.questionnaire.vo.CountRes;
import com.example.questionnaire.vo.QuestionnaireReq;
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.RespondentReq;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionInfoDao questionInfoDao;

	@Autowired
	private RespondentDao respondentDao;

	// 建立問卷
	@Override
	public QuestionnaireRes createQuestionnaire(QuestionnaireReq req) {
		// 問卷名稱
		if (!StringUtils.hasText(req.getQuestionnaireName())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
		}

		// 問卷內容
		if (!StringUtils.hasText(req.getQuestionnaireContent())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
		}

		// 日期為空
		if (req.getStartTime() == null || req.getEndTime() == null) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_IS_EMPTY.getMessage());
		}

		// 開始在結束之後
		if (req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_ERROR.getMessage());
		}

		Questionnaire questionnaire = new Questionnaire(req.getQuestionnaireName(), req.getQuestionnaireContent(),
				req.getStartTime(), req.getEndTime());

		questionnaireDao.save(questionnaire);

		return new QuestionnaireRes(questionnaire, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	// 建立問題
	@Override
	public QuestionnaireRes createQuestionInfo(List<QuestionnaireReq> reqList) {
		List<QuestionInfo> questionInfoList = new ArrayList<>();

		// 建立每一筆問題
		for (QuestionnaireReq req : reqList) {

			// 問題為空
			if (!StringUtils.hasText(req.getQuestion())) {
				return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
			}

			// 單選或多選，選項不能為空
			if (req.getOpType().equals("單選") || req.getOpType().equals("多選")) {
				if (!StringUtils.hasText(req.getOptionsString())) {
					return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
				}
			}

			QuestionInfo questionInfo = new QuestionInfo(req.getQuestionnaireId(), req.getQuestion(),
					req.getOptionsString(), req.getOpType(), req.isMust());

			questionInfoList.add(questionInfo);
		}

		questionInfoDao.saveAll(questionInfoList);
		return new QuestionnaireRes(null, questionInfoList, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes getAllQuestionnaire(QuestionnaireReq req) {
		Page<Questionnaire> pageResult = questionnaireDao
				.findAll(PageRequest.of(req.getPage(), 10, Sort.by("questionnaireId").descending()));

		List<Questionnaire> allQuestionnaire = pageResult.getContent();

		if (allQuestionnaire.isEmpty()) {
			return new QuestionnaireRes(QuestionnaireRtnCode.QUESTIONNAIRE_LIST_IS_EMPTY.getMessage());
		}

		List<QuestionnaireRes> questionnaireResList = new ArrayList<>();

		String str = "";

		// 判斷問卷狀態
		for (Questionnaire questionnaire : allQuestionnaire) {
			QuestionnaireRes res = new QuestionnaireRes();
			if (questionnaire.getStartTime().isAfter(LocalDate.now())) {
				str = "尚未開始";
			} else if (questionnaire.getEndTime().isAfter(LocalDate.now())
					|| questionnaire.getEndTime().isEqual(LocalDate.now())) {
				str = "投票中";
			} else {
				str = "已完結";
			}

			res.setStatus(str);
			res.setQuestionnaire(questionnaire);

			questionnaireResList.add(res);
		}
		return new QuestionnaireRes(questionnaireResList, null, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes findQuestionnaire(QuestionnaireReq req) {
		// 開始在結束之後
		if (req.getStartTime() != null && req.getEndTime() != null && req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_ERROR.getMessage());
		}

		List<Questionnaire> newQuestionnaireList = new ArrayList<>();

		// 有填寫標題
		if (StringUtils.hasText(req.getQuestionnaireName())) {
			List<Questionnaire> questionnaireList = questionnaireDao
					.findByQuestionnaireNameContaining(req.getQuestionnaireName());

			// 沒有填寫時間
			if (req.getStartTime() == null && req.getEndTime() == null) {
				newQuestionnaireList.addAll(questionnaireList);
			}

			// 有填寫開始與結束時間
			else if (req.getStartTime() != null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())
							&& questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// 只填寫開始時間
			else if (req.getStartTime() != null && req.getEndTime() == null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// 只填寫結束時間
			else if (req.getStartTime() == null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}
		} else {
			List<Questionnaire> findAllList = questionnaireDao.findAll();

			// 只填寫開始與結束時間
			if (!StringUtils.hasText(req.getQuestionnaireName()) && req.getStartTime() != null
					&& req.getEndTime() != null) {
				for (Questionnaire questionnaire : findAllList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())
							&& questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// 只填寫開始時間
			else if (req.getStartTime() != null && req.getEndTime() == null) {
				for (Questionnaire questionnaire : findAllList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// 只填寫結束時間
			else if (req.getStartTime() == null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : findAllList) {
					if (questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// 都沒有填寫
			else {
				newQuestionnaireList.addAll(findAllList);
			}
		}

		// 搜尋不到問卷
		if (newQuestionnaireList.isEmpty()) {
			return new QuestionnaireRes(QuestionnaireRtnCode.QUESTIONNAIRE_LIST_IS_EMPTY.getMessage());
		}
		
		// 顯示問卷資訊與問卷狀態
		List<QuestionnaireRes> questionnaireResList = new ArrayList<>();

		String str = "";

		for (Questionnaire questionnaire : newQuestionnaireList) {
			QuestionnaireRes res = new QuestionnaireRes();
			if (questionnaire.getStartTime().isAfter(LocalDate.now())) {
				str = "尚未開始";
			} else if (questionnaire.getEndTime().isAfter(LocalDate.now())
					|| questionnaire.getEndTime().isEqual(LocalDate.now())) {
				str = "投票中";
			} else {
				str = "已完結";
			}

			res.setStatus(str);
			res.setQuestionnaire(questionnaire);

			questionnaireResList.add(res);
		}

		// 限制每一頁2筆list
		List<QuestionnaireRes> subList = questionnaireResList.stream()
				.skip((req.getPage()) * 2).limit(2).collect(Collectors.toList());
		
		return new QuestionnaireRes(subList, null, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes getQuestionsDetails(QuestionnaireReq req) {
		Optional<Questionnaire> questionnaireOp = questionnaireDao.findById(req.getQuestionnaireId());

		// 問卷不存在
		if (!questionnaireOp.isPresent()) {
			return new QuestionnaireRes(QuestionnaireRtnCode.QUESTIONNAIRE_LIST_IS_EMPTY.getMessage());
		}

		Questionnaire questionnaire = questionnaireOp.get();

		// 查詢問卷的題目
		List<QuestionInfo> questionInfoList = questionInfoDao.findByQuestionnaireId(questionnaire.getQuestionnaireId());

		// 顯示每個題目的問題/選項/類型
		List<QuestionnaireRes> questionnaireResList = new ArrayList<>();

		for (QuestionInfo questionInfo : questionInfoList) {
			QuestionnaireRes res = new QuestionnaireRes();

			String question = questionInfo.getQuestion();

			List<String> opList = new ArrayList<>();

			if (StringUtils.hasText(questionInfo.getOp())) {
				String[] OpArray = questionInfo.getOp().split(";");
				for (String item : OpArray) {
					opList.add(item.trim());
				}
			}

			String opType = questionInfo.getOpType();

			res.setQuestion(question);
			res.setOpList(opList);
			res.setOpType(opType);
			questionnaireResList.add(res);
		}
		return new QuestionnaireRes(questionnaire, questionnaireResList, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes respondent(RespondentReq req) {
		// 基本資料填寫
		if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getPhone())
				|| !StringUtils.hasText(req.getEmail()) || req.getAge() <= 0 || !StringUtils.hasText(req.getGender())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.REQUIRED.getMessage());
		}

		// 將map型態的回覆轉成字串
		String ansString = req.getAnswer().toString().substring(1, req.getAnswer().toString().length() - 1);

		Respondent respondent = new Respondent(req.getQuestionnaireId(), LocalDateTime.now(), req.getName(),
				req.getPhone(), req.getEmail(), req.getAge(), req.getGender(), ansString);

		respondentDao.save(respondent);

		// 將這位答卷者的回覆儲存到QuestionInfo
		List<QuestionInfo> QuestionInfoList = questionInfoDao.findByQuestionnaireId(respondent.getQuestionnaireId());

		for (QuestionInfo questionInfo : QuestionInfoList) {
			// 不儲存文字回復
			if (!questionInfo.getOpType().equals("文字")) {

				// 取得此答卷者這題題目的回答
				List<String> ansList = req.getAnswer().get(questionInfo.getQuestionId());

				// 取得這題題目原先的回復數據
				String oldOpData = questionInfo.getOpData();

				List<String> opDataList = new ArrayList<>();

				// 將回復數據由字串轉成list
				if (StringUtils.hasText(oldOpData)) {
					String[] oldOpCountArray = oldOpData.split(",");

					for (String item : oldOpCountArray) {
						String str = item.trim();
						opDataList.add(str);
					}
				}

				// 將此位答卷者的回復儲存到QuestionInfo
				opDataList.addAll(ansList);

				questionInfo.setOpData(opDataList.toString().substring(1, opDataList.toString().length() - 1));

				questionInfoDao.save(questionInfo);
			}
		}

		return new QuestionnaireRes(respondent, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public CountRes dataCount(QuestionnaireReq req) {
		// 依問券id查詢問卷的題目
		List<QuestionInfo> questionInfoList = questionInfoDao.findByQuestionnaireId(req.getQuestionnaireId());

		// 每題題目的統計數據:題目,選項的統計
		List<CountRes> questionList = new ArrayList<>();

		for (QuestionInfo questionInfo : questionInfoList) {

			CountRes res = new CountRes();

			res.setQuestion(questionInfo.getQuestion());

			// 將選項由字串轉成List
			List<String> opList = new ArrayList<>();
			if (StringUtils.hasText(questionInfo.getOp())) {
				String[] OpArray = questionInfo.getOp().split(";");

				for (String item : OpArray) {
					opList.add(item.trim());
				}
			}

			// 將回復由字串轉成list
			List<String> opDataList = new ArrayList<>();
			if (StringUtils.hasText(questionInfo.getOpData())) {
				String[] opCountArray = questionInfo.getOpData().split(",");

				for (String item : opCountArray) {
					opDataList.add(item.trim());
				}
			}

			// 統計回復的數據
			Map<Object, Long> countData = opDataList.stream()
					.collect(Collectors.groupingBy(k -> k, Collectors.counting()));

			List<CountRes> opInfoList = new ArrayList<>();
			// 百分比
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(0);

			// 取每個選項的個數與百分比
			for (String item : opList) {
				CountRes res2 = new CountRes();

				if (opDataList.contains(item)) {
					res2.setOp(item);
					Long optCount = countData.get(item);
					res2.setOpData(optCount.intValue());
					float fl = (float) countData.get(item) / opDataList.size();
					res2.setPercent(nf.format(fl));
					opInfoList.add(res2);
				} else {
					res2.setOp(item);
					res2.setOpData(0);
					res2.setPercent("0%");
					opInfoList.add(res2);
				}
			}
			res.setOpInfoList(opInfoList);
			questionList.add(res);
		}
		return new CountRes(questionList);
	}
}
