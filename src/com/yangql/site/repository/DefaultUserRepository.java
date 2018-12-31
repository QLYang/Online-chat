package com.yangql.site.repository;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yangql.site.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;

@Repository
public class DefaultUserRepository extends GenericJPARepository<Long, User> implements UserRepository {
	@Override
	public User getUserByName(String name) {
		User result=new User();
		try {	
			result=(User)this.entityManager.createQuery("SELECT u FROM User u WHERE u.userName=:name").setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			result=null;
		}
		return result;
	}

}
