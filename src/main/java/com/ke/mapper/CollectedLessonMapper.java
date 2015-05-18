package com.ke.mapper;


import com.ke.entity.CollectedLesson;
public interface CollectedLessonMapper {

	int insert(CollectedLesson collectedLesson);

	int update(CollectedLesson collectedLesson);

	int merge(CollectedLesson collectedLesson);

	int delete(Long id);

	CollectedLesson findOne(Long id);
}