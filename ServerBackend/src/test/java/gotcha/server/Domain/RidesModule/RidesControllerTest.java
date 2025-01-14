package gotcha.server.Domain.RidesModule;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Service.Communication.Requests.FinishRideRequest;
import gotcha.server.Utils.Exceptions.RideNotFoundException;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

class RidesControllerTest {
    private  RidesController ride_controller;
    private AtomicInteger idGenerator = new AtomicInteger(1);


    @Mock
    private IRidesRepository ridesJpaRepository;

    private RidesRepository ridesRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ridesRepository = new RidesRepository(ridesJpaRepository);
        ride_controller = new RidesController(ridesRepository);
    }

    @Test
    void addRide_MultipleConcurrentThreads_AllAdded(){
        configureRideRepository();
        // Set up the test data
        var userEmail = "test@example.com";
        var origin = new LocationDTO(20 ,20);
        var destination = new LocationDTO(40,40);
        var finishRideRequest = new FinishRideRequest("RP",origin, destination, LocalDateTime.now().minusHours(1), LocalDateTime.now(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

        // Set up the executor service for concurrent tasks
        var numberOfThreads = 1000;
        var executor = Executors.newFixedThreadPool(numberOfThreads);

        // Run the tasks concurrently
        List<Future<Ride>> results = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            results.add(executor.submit(() -> {
                try {
                    return ride_controller.add_ride(finishRideRequest,"city", userEmail, "originAdd", "destAdd");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        // Wait for all tasks to complete
        List<Ride> rides = new ArrayList<>();
        try {
            executor.shutdown();
            for (Future<Ride> result : results) {
                Ride ride = result.get();
                assertNotNull(ride);
                rides.add(ride);
            }
        }
        catch(Exception ex) {
            fail("should not fail: "+ ex.getMessage());
        }

        // Check for unique ride IDs
        var uniqueRideIds = new HashSet<>();
        for (Ride ride : rides) {
            assertTrue(uniqueRideIds.add(ride.getRide_id()), String.format("ride id: %d is repeated twice", ride.getRide_id()));
        }

        // Check the total number of rides
        assertEquals(numberOfThreads, rides.size(), String.format("expected %d rides to be added but only %d were added", numberOfThreads, rides.size()));
    }

    //@Test
    void remove_ride() throws RideNotFoundException {
        //ride_controller.add_ride("");
        List<RideDTO> rides = this.ride_controller.get_all_rides();
        int ride_id=1;
        for (RideDTO ride: rides)
        {
            ride_id = ride.getRide_id();
        }
        int prev_size = this.ride_controller.get_all_rides().size();
        ride_controller.remove_ride(ride_id);
        assertEquals(prev_size-1, this.ride_controller.get_all_rides().size());
    }

    @Test
    void get_rides_by_rider_id() {
    }

    @Test
    void get_rides_by_city() {
    }

    @Test
    void get_rides_by_date_ranges() {
    }

    private void configureRideRepository() {
        doAnswer(invocation -> {
            Ride ride = (Ride) invocation.getArgument(0);
            ride.setRide_id(idGenerator.getAndIncrement());
            return null;
        }).when(ridesJpaRepository).save(any(Ride.class));
    }
}