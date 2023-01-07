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

	// �إ߰ݨ�
	@Override
	public QuestionnaireRes createQuestionnaire(QuestionnaireReq req) {
		// �ݨ��W��
		if (!StringUtils.hasText(req.getQuestionnaireName())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
		}

		// �ݨ����e
		if (!StringUtils.hasText(req.getQuestionnaireContent())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
		}

		// �������
		if (req.getStartTime() == null || req.getEndTime() == null) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_IS_EMPTY.getMessage());
		}

		// �}�l�b��������
		if (req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_ERROR.getMessage());
		}

		Questionnaire questionnaire = new Questionnaire(req.getQuestionnaireName(), req.getQuestionnaireContent(),
				req.getStartTime(), req.getEndTime());

		questionnaireDao.save(questionnaire);

		return new QuestionnaireRes(questionnaire, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	// �إ߰��D
	@Override
	public QuestionnaireRes createQuestionInfo(List<QuestionnaireReq> reqList) {
		List<QuestionInfo> questionInfoList = new ArrayList<>();

		// �إߨC�@�����D
		for (QuestionnaireReq req : reqList) {

			// ���D����
			if (!StringUtils.hasText(req.getQuestion())) {
				return new QuestionnaireRes(QuestionnaireRtnCode.PARAMS_ERROR.getMessage());
			}

			// ���Φh��A�ﶵ���ର��
			if (req.getOpType().equals("���") || req.getOpType().equals("�h��")) {
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

		// �P�_�ݨ����A
		for (Questionnaire questionnaire : allQuestionnaire) {
			QuestionnaireRes res = new QuestionnaireRes();
			if (questionnaire.getStartTime().isAfter(LocalDate.now())) {
				str = "�|���}�l";
			} else if (questionnaire.getEndTime().isAfter(LocalDate.now())
					|| questionnaire.getEndTime().isEqual(LocalDate.now())) {
				str = "�벼��";
			} else {
				str = "�w����";
			}

			res.setStatus(str);
			res.setQuestionnaire(questionnaire);

			questionnaireResList.add(res);
		}
		return new QuestionnaireRes(questionnaireResList, null, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes findQuestionnaire(QuestionnaireReq req) {
		// �}�l�b��������
		if (req.getStartTime() != null && req.getEndTime() != null && req.getStartTime().isAfter(req.getEndTime())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.TIME_ERROR.getMessage());
		}

		List<Questionnaire> newQuestionnaireList = new ArrayList<>();

		// ����g���D
		if (StringUtils.hasText(req.getQuestionnaireName())) {
			List<Questionnaire> questionnaireList = questionnaireDao
					.findByQuestionnaireNameContaining(req.getQuestionnaireName());

			// �S����g�ɶ�
			if (req.getStartTime() == null && req.getEndTime() == null) {
				newQuestionnaireList.addAll(questionnaireList);
			}

			// ����g�}�l�P�����ɶ�
			else if (req.getStartTime() != null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())
							&& questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// �u��g�}�l�ɶ�
			else if (req.getStartTime() != null && req.getEndTime() == null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// �u��g�����ɶ�
			else if (req.getStartTime() == null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : questionnaireList) {
					if (questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}
		} else {
			List<Questionnaire> findAllList = questionnaireDao.findAll();

			// �u��g�}�l�P�����ɶ�
			if (!StringUtils.hasText(req.getQuestionnaireName()) && req.getStartTime() != null
					&& req.getEndTime() != null) {
				for (Questionnaire questionnaire : findAllList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())
							&& questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// �u��g�}�l�ɶ�
			else if (req.getStartTime() != null && req.getEndTime() == null) {
				for (Questionnaire questionnaire : findAllList) {
					if (req.getStartTime().minusDays(1).isBefore(questionnaire.getStartTime())) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// �u��g�����ɶ�
			else if (req.getStartTime() == null && req.getEndTime() != null) {
				for (Questionnaire questionnaire : findAllList) {
					if (questionnaire.getEndTime().isBefore(req.getEndTime().plusDays(1))) {
						newQuestionnaireList.add(questionnaire);
					}
				}
			}

			// ���S����g
			else {
				newQuestionnaireList.addAll(findAllList);
			}
		}

		// �j�M����ݨ�
		if (newQuestionnaireList.isEmpty()) {
			return new QuestionnaireRes(QuestionnaireRtnCode.QUESTIONNAIRE_LIST_IS_EMPTY.getMessage());
		}
		
		// ��ܰݨ���T�P�ݨ����A
		List<QuestionnaireRes> questionnaireResList = new ArrayList<>();

		String str = "";

		for (Questionnaire questionnaire : newQuestionnaireList) {
			QuestionnaireRes res = new QuestionnaireRes();
			if (questionnaire.getStartTime().isAfter(LocalDate.now())) {
				str = "�|���}�l";
			} else if (questionnaire.getEndTime().isAfter(LocalDate.now())
					|| questionnaire.getEndTime().isEqual(LocalDate.now())) {
				str = "�벼��";
			} else {
				str = "�w����";
			}

			res.setStatus(str);
			res.setQuestionnaire(questionnaire);

			questionnaireResList.add(res);
		}

		// ����C�@��2��list
		List<QuestionnaireRes> subList = questionnaireResList.stream()
				.skip((req.getPage()) * 2).limit(2).collect(Collectors.toList());
		
		return new QuestionnaireRes(subList, null, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuestionnaireRes getQuestionsDetails(QuestionnaireReq req) {
		Optional<Questionnaire> questionnaireOp = questionnaireDao.findById(req.getQuestionnaireId());

		// �ݨ����s�b
		if (!questionnaireOp.isPresent()) {
			return new QuestionnaireRes(QuestionnaireRtnCode.QUESTIONNAIRE_LIST_IS_EMPTY.getMessage());
		}

		Questionnaire questionnaire = questionnaireOp.get();

		// �d�߰ݨ����D��
		List<QuestionInfo> questionInfoList = questionInfoDao.findByQuestionnaireId(questionnaire.getQuestionnaireId());

		// ��ܨC���D�ت����D/�ﶵ/����
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
		// �򥻸�ƶ�g
		if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getPhone())
				|| !StringUtils.hasText(req.getEmail()) || req.getAge() <= 0 || !StringUtils.hasText(req.getGender())) {
			return new QuestionnaireRes(QuestionnaireRtnCode.REQUIRED.getMessage());
		}

		// �Nmap���A���^���ন�r��
		String ansString = req.getAnswer().toString().substring(1, req.getAnswer().toString().length() - 1);

		Respondent respondent = new Respondent(req.getQuestionnaireId(), LocalDateTime.now(), req.getName(),
				req.getPhone(), req.getEmail(), req.getAge(), req.getGender(), ansString);

		respondentDao.save(respondent);

		// �N�o�쵪���̪��^���x�s��QuestionInfo
		List<QuestionInfo> QuestionInfoList = questionInfoDao.findByQuestionnaireId(respondent.getQuestionnaireId());

		for (QuestionInfo questionInfo : QuestionInfoList) {
			// ���x�s��r�^�_
			if (!questionInfo.getOpType().equals("��r")) {

				// ���o�������̳o�D�D�ت��^��
				List<String> ansList = req.getAnswer().get(questionInfo.getQuestionId());

				// ���o�o�D�D�ح�����^�_�ƾ�
				String oldOpData = questionInfo.getOpData();

				List<String> opDataList = new ArrayList<>();

				// �N�^�_�ƾڥѦr���নlist
				if (StringUtils.hasText(oldOpData)) {
					String[] oldOpCountArray = oldOpData.split(",");

					for (String item : oldOpCountArray) {
						String str = item.trim();
						opDataList.add(str);
					}
				}

				// �N���쵪���̪��^�_�x�s��QuestionInfo
				opDataList.addAll(ansList);

				questionInfo.setOpData(opDataList.toString().substring(1, opDataList.toString().length() - 1));

				questionInfoDao.save(questionInfo);
			}
		}

		return new QuestionnaireRes(respondent, QuestionnaireRtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public CountRes dataCount(QuestionnaireReq req) {
		// �̰ݨ�id�d�߰ݨ����D��
		List<QuestionInfo> questionInfoList = questionInfoDao.findByQuestionnaireId(req.getQuestionnaireId());

		// �C�D�D�ت��έp�ƾ�:�D��,�ﶵ���έp
		List<CountRes> questionList = new ArrayList<>();

		for (QuestionInfo questionInfo : questionInfoList) {

			CountRes res = new CountRes();

			res.setQuestion(questionInfo.getQuestion());

			// �N�ﶵ�Ѧr���নList
			List<String> opList = new ArrayList<>();
			if (StringUtils.hasText(questionInfo.getOp())) {
				String[] OpArray = questionInfo.getOp().split(";");

				for (String item : OpArray) {
					opList.add(item.trim());
				}
			}

			// �N�^�_�Ѧr���নlist
			List<String> opDataList = new ArrayList<>();
			if (StringUtils.hasText(questionInfo.getOpData())) {
				String[] opCountArray = questionInfo.getOpData().split(",");

				for (String item : opCountArray) {
					opDataList.add(item.trim());
				}
			}

			// �έp�^�_���ƾ�
			Map<Object, Long> countData = opDataList.stream()
					.collect(Collectors.groupingBy(k -> k, Collectors.counting()));

			List<CountRes> opInfoList = new ArrayList<>();
			// �ʤ���
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(0);

			// ���C�ӿﶵ���ӼƻP�ʤ���
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
