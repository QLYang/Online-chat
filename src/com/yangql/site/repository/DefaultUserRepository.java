package com.yangql.site.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.yangql.site.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;

@Repository
public class DefaultUserRepository implements UserRepository {
	@PersistenceContext EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() {
		return this.entityManager.createQuery("SELECT u FROM User u").getResultList();
	}

	@Override
	public User getUser(long id) {
		return this.entityManager.find(User.class, id);
	}

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

	@Override
	public void addUser(User user) {
		this.entityManager.persist(user);
	}

	@Override
	public void updateUser(User user) {
		this.entityManager.merge(user);
	}

}
