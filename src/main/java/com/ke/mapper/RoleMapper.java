package com.ke.mapper;


import com.ke.entity.Role;
import java.util.List;
import com.ke.model.query.RoleQueryModel;
public interface RoleMapper {

	int insert(Role role);

	int update(Role role);

	int merge(Role role);

	int delete(Long id);

	Role findOne(Long id);
	
	List<Role> findAll(RoleQueryModel roleQueryModel);

	long count(RoleQueryModel roleQueryModel);
}