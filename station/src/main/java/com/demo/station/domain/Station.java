package com.demo.station.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Station class that represents a radio station.
 * 
 * @author Stew
 *
 */

@Entity
@Table(name = "STATION")
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@NoArgsConstructor
@Setter
@Getter
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Size(max = 255)
	private String stationId;

	@NotBlank
	@Size(max = 255)
	private String name;

	private boolean hdEnabled;

	@NotBlank
	@Size(max = 4)
	private String callsign;

	public Station(String stationId, String name, boolean hdEnabled, String callsign) {
		this.stationId = stationId;
		this.name = name;
		this.hdEnabled = hdEnabled;
		this.callsign = callsign;
	}

}
