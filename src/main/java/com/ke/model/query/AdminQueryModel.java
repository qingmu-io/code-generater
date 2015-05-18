package com.ke.model.query;

import com.ke.common.model.query.QueryModel;

public class AdminQueryModel extends QueryModel {
	private static final long serialVersionUID = -8493398486786898485L;
	private String nicknameLK;
	private String phoneEQ;

	public String getNicknameLK() {
		return nicknameLK;
	}

	public void setNicknameLK(String nicknameLK) {
		this.nicknameLK = nicknameLK;
	}

	public String getPhoneEQ() {
		return phoneEQ;
	}

	public void setPhoneEQ(String phoneEQ) {
		this.phoneEQ = phoneEQ;
	}

}
