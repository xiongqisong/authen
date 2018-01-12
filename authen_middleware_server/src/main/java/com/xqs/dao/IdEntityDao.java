package com.xqs.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import com.xqs.entity.IdEntity;

@Transactional
public abstract class IdEntityDao<T extends IdEntity<T>> {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Transactional
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	public Query createQuery(String hql) {
		return this.getSession().createQuery(hql);
	}

	public T get(long id) {
		return (T) this.getSession().get(getEntityClass(), id);
	}

	protected Class<?> getEntityClass() {
		return GenericTypeResolver.resolveTypeArgument(getClass(), IdEntityDao.class);
	}

	public T create(T t) {
		this.getSession().save(t);
		return t;
	}

	public T safeUpdate(T base, T changed) {
		Field[] fields = getEntityClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		T clone = null;
		try {
			clone = base.clone();
			clone = clone.cloneFields(clone, changed, fieldNames);
			this.getSession().update(clone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clone;
	}

	public void delete(long id) {
		this.getSession().delete(get(id));
	}

}
