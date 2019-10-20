package com.zzolcsak.vehicle.presenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zzolcsak.vehicle.presenter.bean.VehicleBean;

@SpringBootApplication
public class Application {
	private static Map<Long, ScheduledExecutorService> vehicleMovers = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		String nextMove = null;
		try (Scanner scanner = new Scanner(System.in)) {
			while (!"exit".equals(nextMove)) {
				printAllVehicles();
				System.out.print("Next move ('help' for list of moves):");
				nextMove = scanner.nextLine();
				if ("help".equals(nextMove)) {
					System.out.println(
							"Available moves: help, exit, create, refresh, moveUp id, moveDown id, moveLeft id, moveRight id");
				} else if ("create".equals(nextMove)) {
					createNew();
				} else if (nextMove.trim().startsWith("move")) {
					try {
						int indexOfSpace = nextMove.indexOf(' ');
						String direction = nextMove.substring(4, indexOfSpace);
						long id = Long.parseLong(nextMove.substring(indexOfSpace + 1));
						String capitalizedDirection = direction.substring(0, 1).toUpperCase() + direction.substring(1);
						move(id, capitalizedDirection);
						moveForever(id, capitalizedDirection);
					} catch (NumberFormatException e) {
						System.err.println("Error: '" + nextMove
								+ "' couldn't be interpreted. Usage: moveUp vehicleId. Example: moveUp 1");
					}
				}
			}
			System.exit(0);
		}
	}

	private static void moveForever(long id, String capitalizedDirection) {
		ScheduledExecutorService executor = vehicleMovers.get(id);
		if (executor != null) {
			executor.shutdownNow();
		}
		createNewMover(id, capitalizedDirection);
	}

	private static void createNewMover(long id, String capitalizedDirection) {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> move(id, capitalizedDirection), 1, 1, TimeUnit.SECONDS);
		vehicleMovers.put(id, executor);
	}

	private static void move(long id, String direction) {
		Map<String, Long> uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		new RestTemplate()
				.getForEntity("http://localhost:8080/vehicles/{id}/move" + direction, String.class, uriVariables)
				.getBody();
	}

	private static void createNew() {
		ResponseEntity<VehicleBean> responseEntity = new RestTemplate().postForEntity("http://localhost:8080/vehicles",
				new HashMap<>(), VehicleBean.class);
		System.out.println("Created " + responseEntity.getBody());
	}

	private static void printAllVehicles() {
		System.out.println("Here's all vehicles:");
		ResponseEntity<List<VehicleBean>> responseEntity = new RestTemplate().exchange("http://localhost:8080/vehicles",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<VehicleBean>>() {
				});
		List<VehicleBean> allVehicles = responseEntity.getBody();
		Collections.sort(allVehicles);
		allVehicles.forEach(vehicle -> System.out.println(vehicle));
	}

}
