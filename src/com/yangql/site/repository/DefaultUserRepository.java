package com.yangql.site.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.yangql.site.entities.User;
import com.yangql.site.interfaceClasses.UserRepository;

@Repository
public class DefaultUserRepository implements UserRepository {
	@PersistenceContext EntityManager entityManager;
	
	@Override
	public List<User> getAllUser() {
		CriteriaBuilder builder=this.entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query=builder.createQuery(User.class);
		return this.entityManager.createQuery(query.select(query.from(User.class))).getResultList();
	}

	@Override
	public User getUser(long id) {
		return this.entityManager.find(User.class, id);
	}

	@Override
	public User getUserByName(String name) {
		List<User> list=this.getAllUser();
		for(User user : list) {
			if (user.getUserName().equals(name)) {
				return user;
			}
		}
		return null;
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
