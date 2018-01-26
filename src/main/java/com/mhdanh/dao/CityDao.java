package com.mhdanh.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mhdanh.entity.CityEntity;

@Stateless
public class CityDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public CityEntity findByCode(String code) {
		return em.createQuery("SELECT city FROM CityEntity city WHERE city.code = :code",CityEntity.class).setParameter("code", code).getSingleResult();
	}
	
}
