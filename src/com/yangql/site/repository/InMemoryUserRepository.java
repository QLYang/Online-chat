package com.yangql.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yangql.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;

@Repository
public class InMemoryUserRepository implements UserRepository {
	private final Map<Long, User> database=new Hashtable<>();
	private volatile long userIdSequence=1L;
	
	@Override
	public List<User> getAllUser() {
		return new ArrayList<>(this.database.values());
	}

	@Override
	public User getUser(long id) {
		return this.database.get(id);
	}

	@Override
	public void addUser(User user) {
		user.setUserId(this.getUserId());
		this.database.put(user.getUserId(), user);

	}

	private synchronized long getUserId() {
		return this.userIdSequence++;
	}

	@Override
	public void updateUser(User user) {
		this.database.put(user.getUserId(), user);

	}

	@Override
	public User getUserByName(String name) {
		ArrayList<User> listAllUser=new ArrayList<User>(this.database.values());
		for (User user : listAllUser) {
			if (user.getUserName().equals(name)) {
				return user;
			}
		}
		return null;
	}

}
