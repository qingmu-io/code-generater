package com.ke.mapper;


import com.ke.entity.Video;
public interface VideoMapper {

	int insert(Video video);

	int update(Video video);

	int merge(Video video);

	int delete(Long id);

	Video findOne(Long id);
}