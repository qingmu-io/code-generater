package com.ke.mapper;


import com.ke.entity.VideoLog;
public interface VideoLogMapper {

	int insert(VideoLog videoLog);

	int update(VideoLog videoLog);

	int merge(VideoLog videoLog);

	int delete(Long id);

	VideoLog findOne(Long id);
}