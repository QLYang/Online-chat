package com.yangql.site.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.yangql.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;

public class DefaultUserRepository implements UserRepository {
	@PersistenceContext EntityManager entityManager;
	
	@Override
	public List<User> getAllUser() {
		return (List<User>) this.entityManager.createQuery("select a from User a order by a.userName",User.class).getResultList();
	}

	@Override
	public User getUser(long id) {
		return this.entityManager.createQuery("select a from User a where a.userId=:id",User.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public User getUserByName(String name) {
		return this.entityManager.createQuery("select a from User a where a.userName=:name",User.class).setParameter("name", name).getSingleResult();
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
