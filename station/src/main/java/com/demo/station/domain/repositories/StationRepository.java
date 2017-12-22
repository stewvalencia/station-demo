package com.demo.station.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.station.domain.Station;

/**
 * StationRepository interface is used by the StationRestController class
 * for Station related operations such as searching.
 * @author Stew
 *
 */
public interface StationRepository extends JpaRepository<Station, Long> {
	List<Station> findByStationId(String stationId);
}
