package com.ke.mapper;


import com.ke.entity.LessonLog;
public interface LessonLogMapper {

	int insert(LessonLog lessonLog);

	int update(LessonLog lessonLog);

	int merge(LessonLog lessonLog);

	int delete(Long id);

	LessonLog findOne(Long id);
}