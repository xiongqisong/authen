package com.xqs.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.xqs.dao.CloneField;

import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode
public abstract class IdEntity<T> implements Cloneable, CloneField<T> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	@Override
	public T clone() {
		try {
			return (T) super.clone();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
