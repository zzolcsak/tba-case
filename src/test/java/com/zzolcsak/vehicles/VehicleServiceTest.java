package com.zzolcsak.vehicles;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzolcsak.vehicles.model.Position;

@RunWith(SpringRunner.class)
public class VehicleServiceTest {
	@TestConfiguration
	static class VehicleServiceImplTestConfiguration {
		@Bean
		public VehicleService vehicleService() {
			return new VehicleServiceImpl();
		}
	}
	
	@Autowired
	private VehicleService vehicleService;
	
	@Before
	public void setUp() {
		vehicleService.resetVehicle();
	}
	
	@Test
	public void givenVehicle_whenCallingGetVehicleAfterReset_thenReturnsVehicleInCenter() {
		Position positionOfTheVehicle = vehicleService.getVehicle()//
				.getPosition();
		assertEquals(Position.CENTER, positionOfTheVehicle);
	}
	
	@Test
	public void givenVehicle_whenMoveUp_thenReturnsPositionUp() {
		vehicleService.moveUp();
		assertEquals(Position.of(0, 1), vehicleService.getVehicle().getPosition());
	}
	
	@Test
	public void givenVehicle_whenMoveDown_thenReturnsPositionDown() {
		vehicleService.moveDown();
		assertEquals(Position.of(0, -1), vehicleService.getVehicle().getPosition());
	}
	
	@Test
	public void givenVehicle_whenMoveRight_thenReturnsPositionRight() {
		vehicleService.moveRight();
		assertEquals(Position.of(1, 0), vehicleService.getVehicle().getPosition());
	}
	
	@Test
	public void givenVehicle_whenMoveLeft_thenReturnsPositionLeft() {
		vehicleService.moveLeft();
		assertEquals(Position.of(-1, 0), vehicleService.getVehicle().getPosition());
	}
}
