package com.mhdanh.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.mhdanh.dao.CityDao;
import com.mhdanh.dao.HotelDao;
import com.mhdanh.entity.CityEntity;
import com.mhdanh.entity.HotelEntity;
import com.mhdanh.model.City;
import com.mhdanh.model.Hotel;

@Stateless
public class HotelService {

	@Inject
	private HotelDao hotelDao;
	
	@Inject
	private CityDao cityDao;
	
	public Hotel save(Hotel h) {
		HotelEntity e = new HotelEntity();
		e.setLocation(h.getLocation());
		e.setName(h.getName());
		hotelDao.persist(e);
		h.setId(e.getId());
		return h;
	}

	public List<Hotel> getAll() {
		List<HotelEntity> entities = hotelDao.getAll();
		return entities.stream().map(e -> {
			CityEntity cityEntity = cityDao.findByCode(e.getLocation());
			City city = new City();
			city.setId(cityEntity.getId());
			city.setCode(cityEntity.getCode());
			city.setName(cityEntity.getName());
			
			Hotel h = new Hotel();
			h.setId(e.getId());
			h.setName(e.getName());
			h.setLocation(e.getLocation());
			h.setCity(city);
			return h;
		}).collect(Collectors.toList());
	}
	
}
