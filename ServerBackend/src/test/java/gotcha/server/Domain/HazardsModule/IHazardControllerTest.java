package gotcha.server.Domain.HazardsModule;

import gotcha.server.Domain.s3.S3Service;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.LocationDTO;
import gotcha.server.Utils.Logger.SystemLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

class IHazardControllerTest {

    private HazardController hazardController;
    private AtomicInteger idGenerator = new AtomicInteger(1);

    @Mock
    private SystemLogger systemLogger;
    @Mock
    private ReporterAdapter reporterAdapter;

    @Mock
    private IHazardRepository iHazardRepository;

    @Mock
    private S3Service s3Service;

    private HazardRepository hazardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hazardRepository = new HazardRepository(iHazardRepository, s3Service);
        hazardController = new HazardController(systemLogger, hazardRepository, reporterAdapter);
        hazardController.setHAZARD_THRESHOLD_RATE(20.0);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void updateHazards_AllHazardsAreNew_AllAdded() {
//        configureHazardRepository();
//        final String City = "beersheva";
//        final HazardType Type = HazardType.pothole;
//        var location1 = new Location(new BigDecimal(20), new BigDecimal(20));
//        var location2 = new Location(new BigDecimal(40), new BigDecimal(40));
//        var hazards = new ArrayList<>(Arrays.asList(
//                new StationaryHazardRPDTO(20, new LocationDTO(location1),Type.name(), null),
//                new StationaryHazardRPDTO(20, new LocationDTO(location2),Type.name(), null)
//        ));
//        assertDoesNotThrow(() -> hazardController.update_hazards(hazards,1, City));
//        assertTrue(hazardController.view_hazards().size() == hazards.size());
//    }

    @Test
    void updateHazards_LocationIsTheSame_HazardSizeUpdated() {
        configureHazardRepository();
        final String City = "beersheva";
        final HazardType Type = HazardType.pothole;
        var location1 = new Location(new BigDecimal(20), new BigDecimal(20));
        var hazard1 = new StationaryHazard(1,location1,City,Type,20);
        var hazard2 = new StationaryHazard(1,location1,City,Type,40);
        var hazards1 = new ArrayList<>(Arrays.asList(
                hazard1
        ));
        var hazards2 = new ArrayList<>(Arrays.asList(
                hazard2
        ));
//        assertDoesNotThrow(() -> hazardController.update_hazards(hazards1,1));
//        var actualHazards = hazardController.view_hazards();
//        assertTrue(actualHazards.size() == hazards1.size());
//        assertTrue(actualHazards.iterator().next().getSize() == 20);
//        assertDoesNotThrow(() -> hazardController.update_hazards(hazards2,1));
//        var actualHazards2 = hazardController.view_hazards();
//
//        assertTrue(actualHazards2.size() == actualHazards.size());
//        assertTrue(actualHazards2.iterator().next().getSize() == 40);
    }

    @Test
    void updateHazards_LocationsAreWithinRadiusDistance_HazardSizeUpdated() {
        configureHazardRepository();
        final String City = "beersheva";
        final HazardType Type = HazardType.pothole;
        // Create two Location objects with coordinates within RADIOS distance.
        BigDecimal longitude1 = new BigDecimal("12.345678");
        BigDecimal latitude1 = new BigDecimal("56.789012");

        // TODO: 4/8/2023 : Need to create distance based on RADIUS
        BigDecimal longitude2 = longitude1.add(new BigDecimal("0.00001"));
        BigDecimal latitude2 = latitude1.add(new BigDecimal("0.00001"));

        Location location1 = new Location(longitude1, latitude1);
        Location location2 = new Location(longitude2, latitude2);
        var hazard1 = new StationaryHazard(1,location1,City,Type,20);
        var hazard2 = new StationaryHazard(1,location2,City,Type,40);
        var hazards1 = new ArrayList<>(Arrays.asList(
                hazard1
        ));
        var hazards2 = new ArrayList<>(Arrays.asList(
                hazard2
        ));
//        assertDoesNotThrow(() -> hazardController.update_hazards(hazards1,1));
//        var actualHazards = hazardController.view_hazards();
//        assertTrue(actualHazards.size() == hazards1.size());
//        assertTrue(actualHazards.iterator().next().getSize() == 20);
//        assertDoesNotThrow(() -> hazardController.update_hazards(hazards2,1));
//        var actualHazards2 = hazardController.view_hazards();
//
//        assertTrue(actualHazards2.size() == actualHazards.size());
//        assertTrue(actualHazards2.iterator().next().getSize() == 40);
    }

    @Test
    void remove_hazard() {
    }

    private void configureHazardRepository() {
        doAnswer(invocation -> {
            StationaryHazard hazard = (StationaryHazard) invocation.getArgument(0);
            hazard.setId(idGenerator.getAndIncrement());
            return null;
        }).when(iHazardRepository).save(any(StationaryHazard.class));
    }
}