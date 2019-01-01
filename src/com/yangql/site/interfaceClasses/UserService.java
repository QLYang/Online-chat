package com.yangql.site.interfaceClasses;

import java.util.List;

import com.yangql.site.entities.User;

public interface UserService {
	User getUser(long id);
	void saveUser(User user);
	List<User> getAllUser();
	User getUserByName(String name);
}
