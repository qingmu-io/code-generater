package com.ke.mapper;


import com.ke.entity.RolePermission;
public interface RolePermissionMapper {

	int insert(RolePermission rolePermission);

	int update(RolePermission rolePermission);

	int merge(RolePermission rolePermission);

	int delete(Long id);

	RolePermission findOne(Long id);
}