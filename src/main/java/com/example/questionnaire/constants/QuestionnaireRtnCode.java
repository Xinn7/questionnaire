package com.example.questionnaire.constants;

public enum QuestionnaireRtnCode {

	SUCCESSFUL("200", "成功"),
	PARAMS_ERROR("400", "必填欄位不得為空"),
	TIME_IS_EMPTY("400", "請輸入日期"),
	TIME_ERROR("400", "結束時間不得早於開始時間"),
	REQUIRED("400", "基本資料為必填"),
	QUESTIONNAIRE_LIST_IS_EMPTY("400", "暫無問卷");
	
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
