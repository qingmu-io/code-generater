package com.ke.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chapter")
public class Chapter {
	public enum ChapterType {
		VIDEO, ARTICLE;
	}

	@Id
	private Long id;
	private Long parentId;
	private Long lessonId;
	@Column(length = 14)
	private String title;
	@Enumerated(EnumType.ORDINAL)
	private ChapterType chapterType;
	private Integer timeLength;
	private Long refId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ChapterType getChapterType() {
		return chapterType;
	}

	public void setChapterType(ChapterType chapterType) {
		this.chapterType = chapterType;
	}

	public Integer getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}
}