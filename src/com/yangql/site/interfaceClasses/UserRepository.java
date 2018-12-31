package com.yangql.site.interfaceClasses;


import com.yangql.site.entities.User;


public interface UserRepository extends GenericRepository<Long, User>{
	User getUserByName(String name);
}
