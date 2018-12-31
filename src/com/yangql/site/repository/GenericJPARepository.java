package com.yangql.site.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


public abstract class GenericJPARepository<I extends Serializable, E extends Serializable> extends GenericBasicRepository<I, E> {
	@PersistenceContext protected EntityManager entityManager;
	
	@Override
	public Iterable<E> getAll() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(this.entityClass);

        return this.entityManager.createQuery(
                query.select(query.from(this.entityClass))
        ).getResultList();
	}
	@Override
	public E get(I id) {
		return this.entityManager.find(this.entityClass, id);
	}
	
	@Override
	public void add(E entity) {
		this.entityManager.persist(entity);
		
	}
	@Override
	public void update(E entity) {
		this.entityManager.merge(entity);
		
	}
	@Override
	public void delete(E entity) {
		this.entityManager.remove(entity);	
	}

}
