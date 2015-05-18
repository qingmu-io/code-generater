package com.ke.model.query;

import java.util.Date;

import com.ke.common.model.query.QueryModel;

public class LessonQueryModel extends QueryModel {

	private static final long serialVersionUID = 9041135804381319534L;
	
	private Long idEQ;
	private Date createdTimeGTE;
	private Date createdTimeLT;
	
	public Long getIdEQ() {
		return idEQ;
	}
	public void setIdEQ(Long idEQ) {
		this.idEQ = idEQ;
	}
	public Date getCreatedTimeGTE() {
		return createdTimeGTE;
	}
	public void setCreatedTimeGTE(Date createdTimeGTE) {
		this.createdTimeGTE = createdTimeGTE;
	}
	public Date getCreatedTimeLT() {
		return createdTimeLT;
	}
	public void setCreatedTimeLT(Date createdTimeLT) {
		this.createdTimeLT = createdTimeLT;
	}
	
}
