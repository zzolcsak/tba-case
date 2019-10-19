package com.zzolcsak.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzolcsak.vehicles.model.Vehicle;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;

	@GetMapping
	public Vehicle getVehicle() {
		return vehicleService.getVehicle();
	}

	@GetMapping("/reset")
	public Vehicle resetVehicle() {
		vehicleService.resetVehicle();
		return getVehicle();
	}

	@GetMapping("/moveDown")
	public void moveDown() {
		vehicleService.moveDown();
	}

	@GetMapping("/moveUp")
	public void moveUp() {
		vehicleService.moveUp();
	}

	@GetMapping("/moveLeft")
	public void moveLeft() {
		vehicleService.moveLeft();
	}

	@GetMapping("/moveRight")
	public void moveRight() {
		vehicleService.moveRight();
	}
}
