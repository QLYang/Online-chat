package com.yangql.site.interfaceClasses;

import java.util.List;

import com.yangql.entities.User;


public interface UserRepository {
	List<User> getAllUser();
	User getUser(long id);
	User getUserByName(String name);
	void addUser(User user);
	void updateUser(User user);
}
