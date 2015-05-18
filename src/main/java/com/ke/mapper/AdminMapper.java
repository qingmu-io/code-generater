package com.ke.mapper;


import com.ke.entity.Admin;
import java.util.List;
import com.ke.model.query.AdminQueryModel;
public interface AdminMapper {

	int insert(Admin admin);

	int update(Admin admin);

	int merge(Admin admin);

	int delete(Long id);

	Admin findOne(Long id);
	
	List<Admin> findAll(AdminQueryModel adminQueryModel);

	long count(AdminQueryModel adminQueryModel);
}