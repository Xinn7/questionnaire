package com.example.questionnaire.constants;

public enum QuestionnaireRtnCode {

	SUCCESSFUL("200", "���\"),
	PARAMS_ERROR("400", "������줣�o����"),
	TIME_IS_EMPTY("400", "�п�J���"),
	TIME_ERROR("400", "�����ɶ����o����}�l�ɶ�"),
	REQUIRED("400", "�򥻸�Ƭ�����"),
	QUESTIONNAIRE_LIST_IS_EMPTY("400", "�ȵL�ݨ�");
	
	private String code;

	private String message;

	private QuestionnaireRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
