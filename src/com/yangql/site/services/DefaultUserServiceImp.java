package com.yangql.site.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yangql.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;
import com.yangql.site.interfaceClasses.UserService;

@Service
public class DefaultUserServiceImp implements UserService {

	@Inject UserRepository userRepository;
	
	@Override
	public User getUser(long id) {
		return this.userRepository.getUser(id);
	}

	@Override
	public void saveUser(User user) {
		if (user.getUserId()<1) {	//是否是新用户？
			this.userRepository.addUser(user);
		}
		else {
			this.userRepository.updateUser(user);
		}

	}

	@Override
	public List<User> getAllUser() {
		List<User> list=this.userRepository.getAllUser();
		return list;
	}

	@Override
	public User getUserByName(String name) {
		User user=this.userRepository.getUserByName(name);
		return user;
	}

}
