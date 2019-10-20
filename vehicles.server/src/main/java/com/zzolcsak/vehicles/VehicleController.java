package com.zzolcsak.vehicles;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzolcsak.vehicles.model.Vehicle;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping
	public Set<Vehicle> getVehicles() {
		return vehicleService.findAll();
	}

	@GetMapping("/{id}")
	public Vehicle getVehicle(@PathVariable Long id) {
		return vehicleService.findById(id);
	}

	@GetMapping("/{id}/reset")
	public Vehicle resetVehicle(@PathVariable Long id) {
		vehicleService.resetVehicle(id);
		return getVehicle(id);
	}

	@GetMapping("/{id}/moveDown")
	public void moveDown(@PathVariable Long id) {
		vehicleService.moveDown(id);
	}

	@GetMapping("/{id}/moveUp")
	public void moveUp(@PathVariable Long id) {
		vehicleService.moveUp(id);
	}

	@GetMapping("/{id}/moveLeft")
	public void moveLeft(@PathVariable Long id) {
		vehicleService.moveLeft(id);
	}

	@GetMapping("/{id}/moveRight")
	public void moveRight(@PathVariable Long id) {
		vehicleService.moveRight(id);
	}
	
	@PostMapping
	public Vehicle createNewVehicle() {
		return vehicleService.createNewVehicle();
	}
}
