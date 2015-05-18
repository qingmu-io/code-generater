package com.ke.mapper;


import com.ke.entity.User;
import java.util.List;
import com.ke.model.query.UserQueryModel;
public interface UserMapper {

	int insert(User user);

	int update(User user);

	int merge(User user);

	int delete(Long id);

	User findOne(Long id);
	
	List<User> findAll(UserQueryModel userQueryModel);

	long count(UserQueryModel userQueryModel);
}