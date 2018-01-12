package com.mhdanh.rest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.mhdanh.model.Hotel;
import com.mhdanh.service.HotelService;

@Path("/{company-tenant-id}/hotels")
public class HotelResource {
	
	@Inject
	private HotelService hotelService;
	
	@POST
	public Hotel save(Hotel hotel) {
		return hotelService.save(hotel);
	}
	
}
