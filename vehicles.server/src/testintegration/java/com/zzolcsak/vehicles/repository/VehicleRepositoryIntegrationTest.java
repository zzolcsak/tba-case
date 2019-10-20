package com.zzolcsak.vehicles.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzolcsak.vehicles.model.Position;
import com.zzolcsak.vehicles.model.Vehicle;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleRepositoryIntegrationTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Test
    public void givenVehicle_whenFindById_thenReturnVehicle() {
    	Vehicle v = new Vehicle();
    	v.setPosition(Position.of(1, 2));
		entityManager.persist(v);
    	entityManager.flush();
    	
    	Vehicle found = vehicleRepository.findById(v.getId()).get();
    	
    	assertEquals(Position.of(1, 2), found.getPosition());
    }
}
