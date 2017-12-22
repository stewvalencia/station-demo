package com.demo.station;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.demo.station.domain.Station;
import com.demo.station.domain.repositories.StationRepository;

/**
 * StationRestControllerTests tests the Station API methods.
 * @author Stew
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StationApplication.class)
@WebAppConfiguration
public class StationRestControllerTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    @Autowired
    private StationRepository stationRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        }
	
	@Test
	public void findAllStations() throws Exception {
		List<Station> stations = this.stationRepository.findAll();
		mockMvc.perform(get("/api/stations/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(stations.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].stationId", is("TestId")))
                .andExpect(jsonPath("$[0].name", is("testStation")))
				.andExpect(jsonPath("$[0].hdEnabled", is(true)))
				.andExpect(jsonPath("$[0].callsign", is("KTST")))
				.andExpect(jsonPath("$[1].id", is(stations.get(1).getId().intValue())))
				.andExpect(jsonPath("$[2].id", is(stations.get(2).getId().intValue())))
				.andExpect(jsonPath("$[3].id", is(stations.get(3).getId().intValue())))
				.andExpect(jsonPath("$[4].id", is(stations.get(4).getId().intValue())))
				.andExpect(jsonPath("$[5].id", is(stations.get(5).getId().intValue())))
				.andExpect(jsonPath("$[6].id", is(stations.get(6).getId().intValue())));
	}
	
	@Test
	public void findbyStationId() throws Exception {
		String stationId = "CCCC-AM";
		List<Station> stations = this.stationRepository.findByStationId(stationId);
		mockMvc.perform(get("/api/stations/" + stationId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(stations.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].stationId", is(stationId)))
                .andExpect(jsonPath("$[0].name", is("News Station")))
				.andExpect(jsonPath("$[0].hdEnabled", is(false)))
				.andExpect(jsonPath("$[0].callsign", is("CCCC")));
	}
	
	@Test
	public void findbyStationName() throws Exception {
		String stationName = "News Station";
		List<Station> stations = this.stationRepository.findByName(stationName);
		mockMvc.perform(get("/api/stations/names/" + stationName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(stations.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].stationId", is("CCCC-AM")))
                .andExpect(jsonPath("$[0].name", is("News Station")))
				.andExpect(jsonPath("$[0].hdEnabled", is(false)))
				.andExpect(jsonPath("$[0].callsign", is("CCCC")));
	}
	
	@Test
	public void findbyHdEnabled() throws Exception {
		List<Station> stations = this.stationRepository.findByHdEnabled(true);
		mockMvc.perform(get("/api/stations/hdEnabled/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", is(stations.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].stationId", is("TestId")))
                .andExpect(jsonPath("$[0].name", is("testStation")))
				.andExpect(jsonPath("$[0].hdEnabled", is(true)))
				.andExpect(jsonPath("$[0].callsign", is("KTST")))
				.andExpect(jsonPath("$[1].id", is(stations.get(1).getId().intValue())))
				.andExpect(jsonPath("$[2].id", is(stations.get(2).getId().intValue())))
				.andExpect(jsonPath("$[3].id", is(stations.get(3).getId().intValue())))
				.andExpect(jsonPath("$[4].id", is(stations.get(4).getId().intValue())));
	}
	
	@Test
	public void addUpdateDeleteStation() throws Exception {
		String stationId = "XXXX-AM";
		String stationIdChanged = "AXXX-AM";
		mockMvc.perform(post("/api/stations/add/" + stationId))
		 .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.stationId", is(stationId)));
		List<Station> stations = this.stationRepository.findByStationId(stationId);
		assertFalse(stations.isEmpty());
		mockMvc.perform(put("/api/stations/update/" + stationId 
				+ "?newStationId=" + stationIdChanged
				+ "&hdEnabled=true"))
		.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.stationId", is(stationIdChanged)))
        .andExpect(jsonPath("$.hdEnabled", is(true)));
		stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertFalse(stations.isEmpty());
		mockMvc.perform(delete("/api/stations/delete/" + stationIdChanged))
			.andExpect(status().isOk());
		stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertTrue(stations.isEmpty());
	}
	
	@Test
	public void addUpdateDeleteStationWithParameters() throws Exception {
		String stationId = "XXXX-AM";
		String name = "Test Station";
		String callsign = "XXXX";
		String stationIdChanged = "AXXX-AM";
		String nameChanged = "A Test Station";
		String callsignChanged = "AXXX";
		mockMvc.perform(post("/api/stations/add/" + stationId
				+ "?name=" + name
				+ "&hdEnabled=true"
				+ "&callsign=" + callsign))
		 .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.stationId", is(stationId)))
         .andExpect(jsonPath("$.name", is(name)))
         .andExpect(jsonPath("$.hdEnabled", is(true)))
         .andExpect(jsonPath("$.callsign", is(callsign)));
		List<Station> stations = this.stationRepository.findByStationId(stationId);
		assertFalse(stations.isEmpty());
		mockMvc.perform(put("/api/stations/update/" + stationId 
				+ "?newStationId=" + stationIdChanged
				+ "&name=" + nameChanged
				+ "&hdEnabled=false"
				+ "&callsign=" + callsignChanged))
		.andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.stationId", is(stationIdChanged)))
        .andExpect(jsonPath("$.name", is(nameChanged)))
        .andExpect(jsonPath("$.hdEnabled", is(false)))
        .andExpect(jsonPath("$.callsign", is(callsignChanged)));
		stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertFalse(stations.isEmpty());
		mockMvc.perform(delete("/api/stations/delete/" + stationIdChanged))
			.andExpect(status().isOk());
		stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertTrue(stations.isEmpty());
	}
	
	@Test
	public void UpdateDeleteStationInvalid() throws Exception {
		String stationId = "XXXX-AM";
		String stationIdChanged = "AXXX-AM";
		mockMvc.perform(put("/api/stations/update/" + stationId 
				+ "?newStationId=" + stationIdChanged
				+ "&hdEnabled=true"))
		.andExpect(status().isNotFound());
		List<Station> stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertTrue(stations.isEmpty());
		mockMvc.perform(delete("/api/stations/delete/" + stationIdChanged))
			.andExpect(status().isNotFound());
		stations = this.stationRepository.findByStationId(stationId);
		assertTrue(stations.isEmpty());
		stations = this.stationRepository.findByStationId(stationIdChanged);
		assertTrue(stations.isEmpty());
	}

}
