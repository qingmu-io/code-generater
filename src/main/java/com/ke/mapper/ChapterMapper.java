package com.ke.mapper;


import com.ke.entity.Chapter;
public interface ChapterMapper {

	int insert(Chapter chapter);

	int update(Chapter chapter);

	int merge(Chapter chapter);

	int delete(Long id);

	Chapter findOne(Long id);
}