package com.demo.station.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.station.domain.Station;
import com.demo.station.domain.repositories.StationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * StationRestController class contains implementations of the Station API such as
 * search, get, delete, and update operations.
 * @author Stew
 *
 */
@RestController
@Api(value = "Station API")
@RequestMapping("/api")
public class StationRestController {
	
	@Autowired StationRepository stationRepository;
	
	/**
	 * Returns all the stations currently in the database.
	 */
	@GetMapping("/stations")
	@ApiOperation(value = "Returns all the stations currently in the database")
	public Collection<Station> getStations() {
		return this.stationRepository.findAll();
	}
	
	/**
	 * Returns the stations that matches the stationId.
	 * @param stationId
	 * @return Collection of stations matching criteria
	 */
	@GetMapping("/stations/{stationId}")
	@ApiOperation(value = "Returns the stations that matches the stationId")
	public Collection<Station> getStationByStationId(@PathVariable("stationId") String stationId) {
		return this.stationRepository.findByStationId(stationId);
	}
	
	/**
	 * Returns the stations that matches the name.
	 * @param name
	 * @return Collection of stations matching criteria
	 */
	@GetMapping("/stations/names/{name}")
	@ApiOperation(value = "Returns the stations that matches the name")
	public Collection<Station> getStationByName(@PathVariable("name") String name) {
		return this.stationRepository.findByName(name);
	}
	
	/**
	 * Returns all stations that are HD enabled.
	 * @return Collection of stations matching criteria
	 */
	@GetMapping("/stations/hdEnabled")
	@ApiOperation(value = "Returns all stations that are HD enabled")
	public Collection<Station> getHdEnabledStations() {
		return this.stationRepository.findByHdEnabled(true);
	}
	
	/**
	 * Adds station to database.
	 * @param stationId
	 * @param name
	 * @param hdEnabled
	 * @param callsign
	 * @return the newly added station
	 */
	@PostMapping("/stations/add/{stationId}")
	@ApiOperation(value = "Adds station to database")
	public Station addStation(@Valid @PathVariable(value = "stationId") String stationId,
			@Valid @RequestParam(required = false) String name, 
			@Valid @RequestParam(required = false) boolean hdEnabled, 
			@Valid @RequestParam(required = false) String callsign) {
		Station station = new Station();
		callsign = (callsign == null) ? "DFLT" : callsign;
		name = (name == null) ? "default" : name;
		stationId = (stationId == null) ? "DFLT-FM" : stationId;
		station.setCallsign(callsign);
		station.setHdEnabled(hdEnabled);
		station.setName(name);
		station.setStationId(stationId);
		return this.stationRepository.save(station);
	}
	
	/**
	 * Updates the station that matches the given stationId.
	 * @param stationId - Id to match
	 * @param name - new Name
	 * @param hdEnabled - new hdEnabled status
	 * @param callsign - new callsign
	 * @return Response if successfully updated
	 */
	@PutMapping("/stations/update/{stationId}")
	@ApiOperation(value = "Updates the station that matches the given stationId")
	public ResponseEntity<Station> updateStation(@Valid @PathVariable(value = "stationId") String stationId,
			@Valid @RequestParam(required = false) String newStationId,
			@Valid @RequestParam(required = false) String name, 
			@Valid @RequestParam boolean hdEnabled, 
			@Valid @RequestParam(required = false) String callsign) {
		List<Station> validStations = this.stationRepository.findByStationId(stationId);
		if(validStations.isEmpty() || validStations.size() > 1) {
			return ResponseEntity.notFound().build();
		}
		Station station = this.stationRepository.findByStationId(stationId).get(0);
		callsign = (callsign == null) ? station.getCallsign() : callsign;
		name = (name == null) ? station.getName() : name;
		newStationId = (newStationId == null) ? stationId: newStationId;
		station.setCallsign(callsign);
		station.setHdEnabled(hdEnabled);
		station.setName(name);
		station.setStationId(newStationId);
		
		Station updatedStation = this.stationRepository.save(station);
		return ResponseEntity.ok(updatedStation);
	}
	
	/**
	 * Deletes the station that matches the given stationId.
	 * @param stationId - ID to match
	 * @return Response if successfully deleted.
	 */
	@DeleteMapping("/stations/delete/{stationId}")
	@ApiOperation(value = "Deletes the station that matches the given stationId")
	public ResponseEntity<Station> deleteStation(@PathVariable(value = "stationId") String stationId) {
		List<Station> validStations = this.stationRepository.findByStationId(stationId);
		if(validStations.isEmpty() || validStations.size() > 1) {
			return ResponseEntity.notFound().build();
		}
		Station station = this.stationRepository.findByStationId(stationId).get(0);
		
		this.stationRepository.delete(station);
		return ResponseEntity.ok().build();
	}

}
