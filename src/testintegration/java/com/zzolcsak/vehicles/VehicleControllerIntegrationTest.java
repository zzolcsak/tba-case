package com.zzolcsak.vehicles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.zzolcsak.vehicles.model.Position;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class VehicleControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		resetVehicle();
	}

	@Test
	public void givenResetVehicle_whenGetVehicle_thenReturnsCentralPositionInJsonObject() throws Exception {
		assertThatPositionOfVehicleEquals(Position.CENTER);
	}

	@Test
	public void givenMovedUpVehicle_whenGetVehicle_thenReturnsUpwardsPositionInJsonObject() throws Exception {
		moveUp();
		assertThatPositionOfVehicleEquals(Position.of(0, 1));
	}

	@Test
	public void givenMovedDownVehicle_whenGetVehicle_thenReturnsDownwardsPositionInJsonObject() throws Exception {
		moveDown();
		assertThatPositionOfVehicleEquals(Position.of(0, -1));
	}

	@Test
	public void givenMovedLeftVehicle_whenGetVehicle_thenReturnsLeftwardsPositionInJsonObject() throws Exception {
		moveLeft();
		assertThatPositionOfVehicleEquals(Position.of(-1, 0));
	}

	@Test
	public void givenMovedRightVehicle_whenGetVehicle_thenReturnsRightwardsPositionInJsonObject() throws Exception {
		moveRight();
		assertThatPositionOfVehicleEquals(Position.of(1, 0));
	}

	@Test
	public void givenVehicleMovedUpRight_whenGetVehicle_thenReturnsCorrectPositionInJsonObject() throws Exception {
		moveRight();
		moveRight();
		moveUp();
		moveUp();
		moveUp();
		assertThatPositionOfVehicleEquals(Position.of(2, 3));
	}
	
	@Test
	public void givenVehicleMovedDownLeft_whenGetVehicle_thenReturnsCorrectPositionInJsonObject() throws Exception {
		moveLeft();
		moveLeft();
		moveDown();
		moveDown();
		moveDown();
		moveDown();
		moveUp();
		moveUp();
		assertThatPositionOfVehicleEquals(Position.of(-2, -2));
	}

	@Test
	public void givenVehicle_whenResetVehicle_thenReturnsCentralPositionInJsonObject() throws Exception {
		resetVehicle()//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$.position.x", Matchers.equalTo(Position.CENTER.getX())))//
				.andExpect(jsonPath("$.position.y", Matchers.equalTo(Position.CENTER.getY())));
	}

	private ResultActions resetVehicle() throws Exception {
		return mvc.perform(get("/vehicle/reset")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveUp() throws Exception {
		return mvc.perform(get("/vehicle/moveUp")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveDown() throws Exception {
		return mvc.perform(get("/vehicle/moveDown")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveLeft() throws Exception {
		return mvc.perform(get("/vehicle/moveLeft")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveRight() throws Exception {
		return mvc.perform(get("/vehicle/moveRight")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void assertThatPositionOfVehicleEquals(Position position) throws Exception {
		mvc.perform(get("/vehicle")//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$.position.x", Matchers.equalTo(position.getX())))//
				.andExpect(jsonPath("$.position.y", Matchers.equalTo(position.getY())));
	}
}
