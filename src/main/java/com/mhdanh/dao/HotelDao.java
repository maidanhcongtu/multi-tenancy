package com.mhdanh.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mhdanh.annotation.CurrentTenant;
import com.mhdanh.entity.HotelEntity;

@Stateless
public class HotelDao {

	@Inject
	@CurrentTenant
	EntityManager em;
	
	public void persist(HotelEntity e) {
		em.persist(e);
	}
	
	public List<HotelEntity> getAll() {
		return em.createQuery("SELECT h FROM HotelEntity h", HotelEntity.class).getResultList();
	}
	
	
}
