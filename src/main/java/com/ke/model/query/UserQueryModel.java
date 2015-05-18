package com.ke.model.query;

import java.util.Date;

import com.ke.common.model.query.QueryModel;

public class UserQueryModel extends QueryModel {

	private static final long serialVersionUID = 7713321484412949650L;
	private Long idEQ;
	private Date registerTimeGTE;
	private Date registerTimeLT;
	private String nicknameLK;

	public Long getIdEQ() {
		return idEQ;
	}
	public void setIdEQ(Long idEQ) {
		this.idEQ = idEQ;
	}
	public Date getRegisterTimeGTE() {
		return registerTimeGTE;
	}
	public void setRegisterTimeGTE(Date registerTimeGTE) {
		this.registerTimeGTE = registerTimeGTE;
	}
	public Date getRegisterTimeLT() {
		return registerTimeLT;
	}
	public void setRegisterTimeLT(Date registerTimeLT) {
		this.registerTimeLT = registerTimeLT;
	}
	public String getNicknameLK() {
		return nicknameLK;
	}
	public void setNicknameLK(String nicknameLK) {
		this.nicknameLK = nicknameLK;
	}
	
}
