package com.zzolcsak.vehicles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

	private static final long vehicleId = 1L;
	@Autowired
	private MockMvc mvc;
	@MockBean
	private VehicleService vehicleService;

	@Test
	public void whenGetVehicle_thenCallsGetVehicleOfService() throws Exception {
		mvc.perform(get("/vehicles/1")//
				.contentType(MediaType.APPLICATION_JSON));
		verify(vehicleService, times(1)).findById(vehicleId);
	}

	@Test
	public void givenVehicleMovedToTopRight_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertMethodsCalled(2, 3);
	}

	@Test
	public void givenVehicleMovedToTopLeft_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertMethodsCalled(-5, 100);
	}

	@Test
	public void givenVehicleMovedToBottomRight_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertMethodsCalled(5000, -33);
	}

	@Test
	public void givenVehicleMovedToBottomLeft_whenVerifying_thenCalledMethodsCorrectNumberOfTimes() throws Exception {
		moveVehicleAndAssertMethodsCalled(-9, -7);
	}

	private void moveVehicleAndAssertMethodsCalled(int x, int y) throws Exception {
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
		verify(vehicleService, times(x < 0 ? -x : 0)).moveLeft(1L);
		verify(vehicleService, times(x > 0 ? x : 0)).moveRight(1L);
		verify(vehicleService, times(y < 0 ? -y : 0)).moveDown(1L);
		verify(vehicleService, times(y > 0 ? y : 0)).moveUp(1L);
		verifyNoMoreInteractions(vehicleService);
	}

	private void moveLeft() throws Exception {
		mvc.perform(get("/vehicles/{id}/moveLeft", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveRight() throws Exception {
		mvc.perform(get("/vehicles/{id}/moveRight", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveDown() throws Exception {
		mvc.perform(get("/vehicles/{id}/moveDown", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}

	private void moveUp() throws Exception {
		mvc.perform(get("/vehicles/{id}/moveUp", vehicleId)//
				.contentType(MediaType.APPLICATION_JSON));
	}
}
