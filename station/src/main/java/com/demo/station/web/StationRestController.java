package com.demo.station.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.station.domain.Station;
import com.demo.station.domain.repositories.StationRepository;

import io.swagger.annotations.Api;

/**
 * StationRestController class contains implementations of the Station API such as
 * search, get, delete, and update operations.
 * @author Stew
 *
 */
@RestController
@Api
@RequestMapping("/station")
public class StationRestController {
	
	@Autowired StationRepository stationRepository;
	
	@GetMapping
	public Collection<Station> getStations() {
		return this.stationRepository.findAll();
	}
	
	@GetMapping("/{stationId}")
	public Collection<Station> getStation(@PathVariable("stationId") String stationId) {
		return this.stationRepository.findByStationId(stationId);
	}

}
