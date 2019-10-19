package com.zzolcsak.vehicles;

import java.util.Set;

import com.zzolcsak.vehicles.model.Vehicle;

public interface VehicleService {
	void resetVehicle(Long id);
	void moveUp(Long id);
	void moveDown(Long id);
	void moveLeft(Long id);
	void moveRight(Long id);
	Vehicle findById(Long id);
	Set<Vehicle> findAll();
}
