package com.ke.mapper;


import com.ke.entity.Lesson;
import java.util.List;
import com.ke.model.query.LessonQueryModel;
public interface LessonMapper {

	int insert(Lesson lesson);

	int update(Lesson lesson);

	int merge(Lesson lesson);

	int delete(Long id);

	Lesson findOne(Long id);
	
	List<Lesson> findAll(LessonQueryModel lessonQueryModel);

	long count(LessonQueryModel lessonQueryModel);
}