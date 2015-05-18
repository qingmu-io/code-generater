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
@Table(name = "video_log")
public class VideoLog {
	public enum VideoLogType {
		UPLOAD, TRANSCODE_START, TRANSCODE_SUCCESS, TRANSCODE_FAILURE;
	}

	@Id
	private Long id;
	private String content;
	@Enumerated(EnumType.ORDINAL)
	private VideoLogType videoLogType;
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

	public VideoLogType getVideoLogType() {
		return videoLogType;
	}

	public void setVideoLogType(VideoLogType videoLogType) {
		this.videoLogType = videoLogType;
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