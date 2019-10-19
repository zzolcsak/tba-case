package com.zzolcsak.vehicles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Vehicle;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private VehicleService vehicleService;

	@Test
	public void givenCentralVehicle_whenGetVehicle_thenReturnsCentralPositionInJsonObject() throws Exception {
		givenVehicle_whenGetVehicle_thenReturnsJsonObjectWithCorrectPosition(new Vehicle(), Position.CENTER);
	}

	@Test
	public void givenVehicleAtCertainPosition_whenGetVehicle_thenReturnsSamePositionInJsonObject() throws Exception {
		Vehicle vehicle = new Vehicle();
		vehicle.setPosition(Position.of(1, 2));
		givenVehicle_whenGetVehicle_thenReturnsJsonObjectWithCorrectPosition(vehicle, Position.of(1, 2));
	}

	private void givenVehicle_whenGetVehicle_thenReturnsJsonObjectWithCorrectPosition(Vehicle vehicle,
			Position position) throws Exception {
		Mockito.when(vehicleService.getVehicle()).thenReturn(vehicle);
		assertPositionOfGetVehicleEquals(position);
	}

	private void assertPositionOfGetVehicleEquals(Position position) throws Exception {
		mvc.perform(get("/vehicle")//
				.contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//
				.andExpect(jsonPath("$.position.x", Matchers.equalTo(position.getX())))//
				.andExpect(jsonPath("$.position.y", Matchers.equalTo(position.getY())));
	}

	@Test
	public void givenVehicleMovedToTopRight_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertPosition(2, 3);
	}

	@Test
	public void givenVehicleMovedToTopLeft_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertPosition(-5, 100);
	}

	@Test
	public void givenVehicleMovedToBottomRight_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertPosition(5000, -33);
	}

	@Test
	public void givenVehicleMovedToBottomLeft_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertPosition(-9, -7);
	}

	private void moveVehicleAndAssertPosition(int x, int y) throws Exception {
		for (int i = 0; i > x; i--) {
			moveLeft();
		}
		for (int i = 0; i < x; i++) {
			moveRight();
		}
		for (int i = 0; i > y; i--) {
			moveDown();
		}
		for (int i = 0; i < y; i++) {
			moveUp();
		}
		verify(vehicleService, times(x < 0 ? -x : 0)).moveLeft();
		verify(vehicleService, times(x > 0 ? x : 0)).moveRight();
		verify(vehicleService, times(y < 0 ? -y : 0)).moveDown();
		verify(vehicleService, times(y > 0 ? y : 0)).moveUp();
		verifyNoMoreInteractions(vehicleService);
	}

	private void moveLeft() throws Exception {
		mvc.perform(get("/vehicle/moveLeft")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveRight() throws Exception {
		mvc.perform(get("/vehicle/moveRight")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveDown() throws Exception {
		mvc.perform(get("/vehicle/moveDown")//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveUp() throws Exception {
		mvc.perform(get("/vehicle/moveUp")//
				.contentType(MediaType.APPLICATION_JSON));
	}
}
