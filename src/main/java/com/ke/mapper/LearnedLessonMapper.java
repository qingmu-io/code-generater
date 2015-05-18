package com.ke.mapper;


import com.ke.entity.LearnedLesson;
public interface LearnedLessonMapper {

	int insert(LearnedLesson learnedLesson);

	int update(LearnedLesson learnedLesson);

	int merge(LearnedLesson learnedLesson);

	int delete(Long id);

	LearnedLesson findOne(Long id);
}