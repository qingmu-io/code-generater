package com.ke.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lesson_log")
public class LessonLog {
	public enum LessonLogType {
		CREATE, APPLY, CONFIRM_OK, CONFIRM_REJECT, RE_APPLY;
	}

	@Id
	private Long id;
	private String content;
	@Enumerated(EnumType.ORDINAL)
	private LessonLogType lessonLogType;
	private Long operatorId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LessonLogType getLessonLogType() {
		return lessonLogType;
	}

	public void setLessonLogType(LessonLogType lessonLogType) {
		this.lessonLogType = lessonLogType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}