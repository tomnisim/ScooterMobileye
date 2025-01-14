package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Exceptions.RideNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RidesRepository {
    private IRidesRepository ridesJpaRepository;
    private Map<Integer, Ride> rides; // maps ride_id to ride
    private Map<String, Map<Integer,Ride>> rides_by_rider; // maps rider_email to List of his rides

    public RidesRepository(IRidesRepository ridesJpaRepository) {
        rides = new ConcurrentHashMap<>();
        rides_by_rider = new ConcurrentHashMap<>();
        this.ridesJpaRepository = ridesJpaRepository;
        LoadFromDB();
    }

    public void addRide(Ride newRide, String userEmail) throws Exception {
        ridesJpaRepository.save(newRide);
        // TODO: 4/11/2023 : Need to think about how to store the rides_by_rider in DB
        var addRideResult = this.rides.putIfAbsent(newRide.getRide_id(), newRide);
        if (addRideResult != null) {
            throw new Exception("Ride already exists");
        }
        rides_by_rider.computeIfAbsent(userEmail, k -> new HashMap<>()).putIfAbsent(newRide.getRide_id(), newRide);
    }

    public List<Ride> getAllRides() {
        return  new ArrayList<>(rides.values());
    }

    public List<Ride> getAllRidesByRider(String riderEmail) {
        if (!rides_by_rider.containsKey(riderEmail))
        {
            rides_by_rider.put(riderEmail, new HashMap<>());

        }
        return new ArrayList<>(rides_by_rider.get(riderEmail).values());
    }

    public void removeRide(int rideId) throws RideNotFoundException {
        var result = rides.remove(rideId);
        if (result == null)
            throw new RideNotFoundException("hazard with id:" + rideId + " not found");
        var userEmail = result.getRider_email();
        rides_by_rider.get(userEmail).remove(rideId);
        ridesJpaRepository.delete(result);
    }

    public Ride getRideById(int rideId) throws Exception {
        var result = rides.get(rideId);
        if (result == null) {
            return getRideFromDb(rideId);
        }
        return result;
    }

    public Ride getRideFromDb(int rideId) throws Exception {
        var rideOpt = ridesJpaRepository.findById(rideId);
        if (rideOpt.isPresent()) {
            Ride ride = rideOpt.get();

            Optional<Ride> rideWithActionsOpt = ridesJpaRepository.findByIdWithActions(ride.getRide_id());
            Optional<Ride> rideWithJunctionsOpt = ridesJpaRepository.findByIdWithJunctions(ride.getRide_id());

            if (rideWithActionsOpt.isPresent()) {
                ride.setActions(rideWithActionsOpt.get().getActions());
            }
            else {
                ride.setActions(new ArrayList<>());
            }

            if (rideWithJunctionsOpt.isPresent()) {
                ride.setJunctions(rideWithJunctionsOpt.get().getJunctions());
            }
            else {
                ride.setJunctions(new ArrayList<>());
            }

            return ride;
        } else {
            throw new Exception("ride with id:" + rideId + " not found");
        }
    }

    public void LoadFromDB() {
        var ridesInDb = ridesJpaRepository.findAll();
        for (Ride ride : ridesInDb) {
            Optional<Ride> rideWithActionsOpt = ridesJpaRepository.findByIdWithActions(ride.getRide_id());
            Optional<Ride> rideWithJunctionsOpt = ridesJpaRepository.findByIdWithJunctions(ride.getRide_id());

            if (rideWithActionsOpt.isPresent()) {
                ride.setActions(rideWithActionsOpt.get().getActions());
            }
            else {
                ride.setActions(new ArrayList<>());
            }

            if (rideWithJunctionsOpt.isPresent()) {
                ride.setJunctions(rideWithJunctionsOpt.get().getJunctions());
            }
            else {
                ride.setJunctions(new ArrayList<>());
            }

            rides.put(ride.getRide_id(), ride);
            rides_by_rider.computeIfAbsent(ride.getRider_email(), k -> new HashMap<>()).putIfAbsent(ride.getRide_id(), ride);
        }
    }


}
