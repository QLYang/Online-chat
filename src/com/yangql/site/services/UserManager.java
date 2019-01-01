package com.yangql.site.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangql.site.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;
import com.yangql.site.interfaceClasses.UserService;
@Service
public class UserManager implements UserService {
	@Inject UserRepository userRepo;
	
	@Override
	@Transactional
	public User getUser(long id) {
		return this.userRepo.get(id);
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		this.userRepo.add(user);
	}

	@Override
	@Transactional
	public List<User> getAllUser() {
		return (List<User>) this.userRepo.getAll();
	}

	@Override
	@Transactional
	public User getUserByName(String name) {
		return this.userRepo.getUserByName(name);
	}

}
