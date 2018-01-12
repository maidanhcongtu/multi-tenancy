package com.mhdanh.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mhdanh.dao.HotelDao;
import com.mhdanh.entity.HotelEntity;
import com.mhdanh.model.Hotel;

@Stateless
public class HotelService {

	@Inject
	private HotelDao hotelDao;
	
	public Hotel save(Hotel h) {
		HotelEntity e = new HotelEntity();
		e.setLocation(h.getLocation());
		e.setName(h.getName());
		hotelDao.persist(e);
		h.setId(e.getId());
		return h;
	}
	
}
