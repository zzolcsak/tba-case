package com.zzolcsak.vehicles;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Position.Direction;
import com.zzolcsak.vehicles.repository.VehicleRepository;
import com.zzolcsak.vehicles.model.Vehicle;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository repository;

	@Override
	public void resetVehicle(Long id) {
		Vehicle vehicle = findById(id);
		vehicle.setPosition(Position.CENTER);
		save(vehicle);
	}

	@Override
	public void moveUp(Long id) {
		move(id, Direction.UP);
	}

	private void move(Long id, Direction direction) {
		Vehicle vehicle = findById(id);
		vehicle.setPosition(vehicle.getPosition().transform(direction));
		save(vehicle);
	}

	private Vehicle save(Vehicle vehicle) {
		return repository.save(vehicle);
	}

	@Override
	public void moveDown(Long id) {
		move(id, Direction.DOWN);
	}

	@Override
	public void moveLeft(Long id) {
		move(id, Direction.LEFT);
	}

	@Override
	public void moveRight(Long id) {
		move(id, Direction.RIGHT);
	}

	@Override
	public Vehicle findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Set<Vehicle> findAll() {
		return new HashSet<>(repository.findAll());
	}

	@Override
	public Vehicle createNewVehicle() {
		return save(new Vehicle());
	}

}
