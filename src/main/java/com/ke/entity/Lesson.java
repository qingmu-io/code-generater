package com.ke.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lesson")
public class Lesson {
	public enum LessonStatus {
		CREATED, CONFIRMED, REJECTED;
	}

	@Id
	private Long id;
	@Column(length = 14)
	private String title;
	private Long userId;
	private Long categoryId;
	private LessonStatus lessonStatus;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmedTime;
	private String image;
	private Integer totalTimeLength;
	private String rejectedReason;

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getTotalTimeLength() {
		return totalTimeLength;
	}

	public void setTotalTimeLength(Integer totalTimeLength) {
		this.totalTimeLength = totalTimeLength;
	}

	public LessonStatus getLessonStatus() {
		return lessonStatus;
	}

	public void setLessonStatus(LessonStatus lessonStatus) {
		this.lessonStatus = lessonStatus;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getConfirmedTime() {
		return confirmedTime;
	}

	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}

}