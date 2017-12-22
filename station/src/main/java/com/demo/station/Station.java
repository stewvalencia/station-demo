package com.demo.station;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Station class that represents a radio station.
 * @author Stew
 *
 */
@AllArgsConstructor
public @Data class Station {
	private String stationId;
	private String name;
	private boolean hdEnabled;
	private String callSign;
}
