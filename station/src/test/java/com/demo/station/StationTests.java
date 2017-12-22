package com.demo.station;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		assertEquals("ToString method is incorrect.", station.toString(), 
				"Station(stationId=TestId, name=testStation, hdEnabled=true, callSign=KTST)");
		assertEquals("Equals method is incorrect.", station, station);
	}

}
