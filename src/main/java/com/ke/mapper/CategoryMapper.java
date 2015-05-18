package com.ke.mapper;


import com.ke.entity.Category;
public interface CategoryMapper {

	int insert(Category category);

	int update(Category category);

	int merge(Category category);

	int delete(Long id);

	Category findOne(Long id);
}