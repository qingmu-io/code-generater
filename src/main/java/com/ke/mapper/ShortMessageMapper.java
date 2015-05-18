package com.ke.mapper;


import com.ke.entity.ShortMessage;
public interface ShortMessageMapper {

	int insert(ShortMessage shortMessage);

	int update(ShortMessage shortMessage);

	int merge(ShortMessage shortMessage);

	int delete(Long id);

	ShortMessage findOne(Long id);
}