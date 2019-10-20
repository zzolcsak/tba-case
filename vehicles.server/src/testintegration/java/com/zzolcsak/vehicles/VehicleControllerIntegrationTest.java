package com.zzolcsak.vehicles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Vehicle;
import com.zzolcsak.vehicles.repository.VehicleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class VehicleControllerIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private VehicleRepository repository;

	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
	}

	@Test
	public void whenCreateNewVehicle_thenItIsCreated() throws Exception {
		mvc.perform(post("/vehicles")//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk());
	}

	@Test
	public void givenNonExistentVehicleId_whenGetVehicle_thenThrowsNoSuchElementException() throws Exception {
		Assertions.assertThatThrownBy(() -> mvc.perform(get("/vehicles/{id}", 98989898L)).andExpect(status().isOk()))
				.hasCause(new NoSuchElementException("No value present"));
	}

	@Test
	public void givenThreeVehicles_whenGetAllVehicles_thenReturnsAJsonArrayOfThree() throws Exception {
		newTestVehicle();
		newTestVehicle();
		newTestVehicle();
		repository.flush();
		mvc.perform(get("/vehicles")//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$", Matchers.hasSize(3)));
	}

	@Test
	public void givenTwoVehiclesMoved_whenGetVehicle_thenReturnsAccuratePositionInJsonObject() throws Exception {
		Vehicle first = newTestVehicle();
		Vehicle second = newTestVehicle();
		repository.flush();
		moveUp(first.getId());
		moveRight(second.getId());
		assertThatPositionOfVehicleEquals(first.getId(), Position.of(0, 1));
		assertThatPositionOfVehicleEquals(second.getId(), Position.of(1, 0));
	}

	@Test
	public void givenResetVehicle_whenGetVehicle_thenReturnsCentralPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveUp(vehicle.getId());
		resetVehicle(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.CENTER);
	}

	private Vehicle newTestVehicle() {
		return repository.save(new Vehicle());
	}

	@Test
	public void givenMovedUpVehicle_whenGetVehicle_thenReturnsUpwardsPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveUp(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(0, 1));
	}

	@Test
	public void givenMovedDownVehicle_whenGetVehicle_thenReturnsDownwardsPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveDown(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(0, -1));
	}

	@Test
	public void givenMovedLeftVehicle_whenGetVehicle_thenReturnsLeftwardsPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveLeft(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(-1, 0));
	}

	@Test
	public void givenMovedRightVehicle_whenGetVehicle_thenReturnsRightwardsPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveRight(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(1, 0));
	}

	@Test
	public void givenVehicleMovedUpRight_whenGetVehicle_thenReturnsCorrectPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveRight(vehicle.getId());
		moveRight(vehicle.getId());
		moveUp(vehicle.getId());
		moveUp(vehicle.getId());
		moveUp(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(2, 3));
	}

	@Test
	public void givenVehicleMovedDownLeft_whenGetVehicle_thenReturnsCorrectPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		moveLeft(vehicle.getId());
		moveLeft(vehicle.getId());
		moveDown(vehicle.getId());
		moveDown(vehicle.getId());
		moveDown(vehicle.getId());
		moveDown(vehicle.getId());
		moveUp(vehicle.getId());
		moveUp(vehicle.getId());
		assertThatPositionOfVehicleEquals(vehicle.getId(), Position.of(-2, -2));
	}

	@Test
	public void givenVehicle_whenResetVehicle_thenReturnsCentralPositionInJsonObject() throws Exception {
		Vehicle vehicle = newTestVehicle();
		resetVehicle(vehicle.getId())//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$.position.x", Matchers.equalTo(Position.CENTER.getX())))//
				.andExpect(jsonPath("$.position.y", Matchers.equalTo(Position.CENTER.getY())));
	}

	private ResultActions resetVehicle(Long vehicleId) throws Exception {
		return mvc.perform(get("/vehicles/{id}/reset", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveUp(Long vehicleId) throws Exception {
		return mvc.perform(get("/vehicles/{id}/moveUp", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveDown(Long vehicleId) throws Exception {
		return mvc.perform(get("/vehicles/{id}/moveDown", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveLeft(Long vehicleId) throws Exception {
		return mvc.perform(get("/vehicles/{id}/moveLeft", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions moveRight(Long vehicleId) throws Exception {
		return mvc.perform(get("/vehicles/{id}/moveRight", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void assertThatPositionOfVehicleEquals(Long vehicleId, Position position) throws Exception {
		mvc.perform(get("/vehicles/{id}", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$.position.x", Matchers.equalTo(position.getX())))//
				.andExpect(jsonPath("$.position.y", Matchers.equalTo(position.getY())));
	}
}
