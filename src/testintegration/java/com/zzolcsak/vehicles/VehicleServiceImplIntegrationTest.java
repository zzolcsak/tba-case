package com.zzolcsak.vehicles;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Vehicle;
import com.zzolcsak.vehicles.repository.VehicleRepository;

@RunWith(SpringRunner.class)
public class VehicleServiceImplIntegrationTest {
	@TestConfiguration
	static class VehicleServiceImplTestConfiguration {
		@Bean
		public VehicleService vehicleService() {
			return new VehicleServiceImpl();
		}
	}

	@Autowired
	private VehicleService vehicleService;

	@MockBean
	private VehicleRepository repository;
	private final Position position = Position.of(1, 2);

	@Before
	public void setUp() {
		Vehicle vehicle = new Vehicle();
		vehicle.setPosition(position);
		when(repository.findById(any())).thenReturn(Optional.of(vehicle));
	}

	@Test
	public void whenFindById_thenCallsFindByIdMethodOfRepo() {
		Vehicle v = vehicleService.findById(1L);
		assertEquals(position, v.getPosition());
	}

	// TODO add more tests
}
