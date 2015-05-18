package com.ke.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "video")
public class Video {
	public enum VideoStatus {
		UPLOADED, TRANSCODING, TRANSCODE_SUCCESS, TRANSCODE_ERROR;
	}

	@Id
	private Long id;
	private Long chapterId;
	private String title;
	private String snapshot;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	@Enumerated(EnumType.ORDINAL)
	private VideoStatus videoStatus;
	private Integer timeLength;
	@Column(length = 36)
	private String originKey;
	
	/* transcode */
	@Column(length = 36)
	private String flvLowKey;
	@Column(length = 36)
	private String flvMediumKey;
	@Column(length = 36)
	private String flvHighKey;
	@Column(length = 36)
	private String mp4LowKey;
	@Column(length = 36)
	private String mp4MediumKey;
	@Column(length = 36)
	private String mp4HighKey;
	private Boolean isFlvHighSuccess;
	private Boolean isFlvMediumSuccess;
	private Boolean isFlvLowSuccess;
	private Boolean isMp4HighSuccess;
	private Boolean isMp4MediumSuccess;
	private Boolean isMp4LowSuccess;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public VideoStatus getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(VideoStatus videoStatus) {
		this.videoStatus = videoStatus;
	}

	public Integer getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	public String getFlvLowKey() {
		return flvLowKey;
	}

	public void setFlvLowKey(String flvLowKey) {
		this.flvLowKey = flvLowKey;
	}

	public String getFlvMediumKey() {
		return flvMediumKey;
	}

	public void setFlvMediumKey(String flvMediumKey) {
		this.flvMediumKey = flvMediumKey;
	}

	public String getFlvHighKey() {
		return flvHighKey;
	}

	public void setFlvHighKey(String flvHighKey) {
		this.flvHighKey = flvHighKey;
	}

	public String getMp4LowKey() {
		return mp4LowKey;
	}

	public void setMp4LowKey(String mp4LowKey) {
		this.mp4LowKey = mp4LowKey;
	}

	public String getMp4MediumKey() {
		return mp4MediumKey;
	}

	public void setMp4MediumKey(String mp4MediumKey) {
		this.mp4MediumKey = mp4MediumKey;
	}

	public String getMp4HighKey() {
		return mp4HighKey;
	}

	public void setMp4HighKey(String mp4HighKey) {
		this.mp4HighKey = mp4HighKey;
	}

	public Boolean getIsFlvHighSuccess() {
		return isFlvHighSuccess;
	}

	public void setIsFlvHighSuccess(Boolean isFlvHighSuccess) {
		this.isFlvHighSuccess = isFlvHighSuccess;
	}

	public Boolean getIsFlvMediumSuccess() {
		return isFlvMediumSuccess;
	}

	public void setIsFlvMediumSuccess(Boolean isFlvMediumSuccess) {
		this.isFlvMediumSuccess = isFlvMediumSuccess;
	}

	public Boolean getIsFlvLowSuccess() {
		return isFlvLowSuccess;
	}

	public void setIsFlvLowSuccess(Boolean isFlvLowSuccess) {
		this.isFlvLowSuccess = isFlvLowSuccess;
	}

	public Boolean getIsMp4HighSuccess() {
		return isMp4HighSuccess;
	}

	public void setIsMp4HighSuccess(Boolean isMp4HighSuccess) {
		this.isMp4HighSuccess = isMp4HighSuccess;
	}

	public Boolean getIsMp4MediumSuccess() {
		return isMp4MediumSuccess;
	}

	public void setIsMp4MediumSuccess(Boolean isMp4MediumSuccess) {
		this.isMp4MediumSuccess = isMp4MediumSuccess;
	}

	public Boolean getIsMp4LowSuccess() {
		return isMp4LowSuccess;
	}

	public void setIsMp4LowSuccess(Boolean isMp4LowSuccess) {
		this.isMp4LowSuccess = isMp4LowSuccess;
	}
}