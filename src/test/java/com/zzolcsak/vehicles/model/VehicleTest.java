package com.zzolcsak.vehicles.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VehicleTest {
	@Test
	public void givenAVehicle_whenCreated_itsPositionIsInTheCenter() {
		// given
		Vehicle vehicle = new Vehicle();
		// when
		Position position = vehicle.getPosition();
		//then
		assertEquals(Position.CENTER, position);
	}
}
