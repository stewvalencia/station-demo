package com.demo.station;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.station.domain.Station;

/**
 * StationTests contains tests for the Station class and its methods.
 * @author Stew
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StationTests {

	/**
	 * TestStation is a basic test for the Station class and Lombok generated functions.
	 */
	@Test
	public void TestStation() {
		Station station = new Station("TestId", "testStation", true, "KTST");
		Station station2 = new Station("TestId", "testStation", true, "KTSK");
		assertEquals("ToString method is incorrect.", station.toString(), 
				"Station(stationId=TestId, name=testStation, hdEnabled=true, callsign=KTST)");
		assertTrue("Hashcode method is incorrect.", station.hashCode() == station.hashCode());
		assertTrue("Equals method is incorrect.", station.equals(station));
		assertFalse("Equals method is incorrect.", station.equals(null));
		assertFalse("Equals method is incorrect.", station.equals(true));
		assertFalse("Equals method is incorrect.", station.equals(station2));
	}

	/**
	 * TestStation is a basic test to cover Lombok generated setters.
	 */
	@Test
	public void TestStationSetters() {
		Station station = new Station("TestId", "testStation", true, "KTST");
		station.setCallsign("KTTK");
		station.setId(1L);
		station.setHdEnabled(false);
		station.setName("testStation2");
		station.setStationId("TestId2");
		assertEquals("ToString method is incorrect.", station.toString(), 
				"Station(stationId=TestId2, name=testStation2, hdEnabled=false, callsign=KTTK)");
		assertTrue("Hashcode method is incorrect.", station.hashCode() == station.hashCode());
		assertTrue("Equals method is incorrect.", station.equals(station));
		assertTrue("Equals method is incorrect.", station.getId() == 1L);
	}
}
