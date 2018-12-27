package com.yangql.site.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yangql.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;
import com.yangql.site.interfaceClasses.UserService;
@Service
public class InDatabaseUserService implements UserService {
	@Inject UserRepository userRepo;
	
	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
