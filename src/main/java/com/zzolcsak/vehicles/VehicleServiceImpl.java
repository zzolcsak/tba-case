package com.zzolcsak.vehicles;

import org.springframework.stereotype.Service;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Position.Direction;
import com.zzolcsak.vehicles.model.Vehicle;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	private Vehicle vehicle = new Vehicle();
	
	@Override
	public void resetVehicle() {
		vehicle.setPosition(Position.CENTER);
	}

	@Override
	public Vehicle getVehicle() {
		return vehicle;
	}

	@Override
	public void moveUp() {
		vehicle.setPosition(vehicle.getPosition().transform(Direction.UP));
	}

	@Override
	public void moveDown() {
		vehicle.setPosition(vehicle.getPosition().transform(Direction.DOWN));
	}

	@Override
	public void moveLeft() {
		vehicle.setPosition(vehicle.getPosition().transform(Direction.LEFT));
	}

	@Override
	public void moveRight() {
		vehicle.setPosition(vehicle.getPosition().transform(Direction.RIGHT));
	}

}
