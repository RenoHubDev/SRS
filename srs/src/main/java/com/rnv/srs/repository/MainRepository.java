package com.rnv.srs.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class MainRepository<T extends Serializable> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Class<T> type;

	public MainRepository(Class<T> type) {
		super();
		this.type = type;
	}
	
	public void persist(T entity) {
		entityManager.persist(entity);
	}
	
	public void merge(T entity) {
		entityManager.merge(entity);
	}
	
	public void delete(T entity) {
		entityManager.remove(entity);
	}
	
	public List<T> find(String sql, Map<String, Object> params) {
		TypedQuery<T> query = entityManager.createQuery(sql, type);
		return find(query, params, type);
	}
	
	public T findById(long id) {
		String sql = String.format("select t from %s t where t.id = "+id, type.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(sql, type);
		return query.getSingleResult();
		
	}
	
	public List<T> findAll() {
		String sql = String.format("select t from %s t ", type.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(sql, type);
		return query.getResultList();
	}
	
	public List<T> findAllCount() {
		String sql = String.format("select count(t) from %s t ", type.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(sql, type);
		return query.getResultList();
	}
	
	public long findCountByNamedQuery(String sql, Map<String, Object> params) {
		TypedQuery<Long> query  = entityManager.createNamedQuery(sql, Long.class);
		for (String key : params.keySet())
			query.setParameter(key, params.get(key));
		return query.getSingleResult().longValue();
	}
	
	public List<T> findByNamedQuery(String sql, Map<String, Object> params) {
		TypedQuery<T> query  = entityManager.createNamedQuery(sql, type);
		return find(query, params, type);
	}
	
	public T findSingleByNamedQuery(String sql, Map<String, Object> params) {
		TypedQuery<T> query  = entityManager.createNamedQuery(sql, type);
		return find(query, params, type).get(0);
	}
	
	private<E> List<E> find(TypedQuery<E> query, Map<String, Object> params, Class<E> type) {
		if (params != null)
			for(Entry<String, Object> entry : params.entrySet())
				query.setParameter(entry.getKey(), entry.getValue());
		return query.getResultList();
	}

}
