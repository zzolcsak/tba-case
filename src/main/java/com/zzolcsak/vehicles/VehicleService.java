package com.zzolcsak.vehicles;

import com.zzolcsak.vehicles.model.Vehicle;

public interface VehicleService {
	void resetVehicle();
	Vehicle getVehicle();
	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();
}
