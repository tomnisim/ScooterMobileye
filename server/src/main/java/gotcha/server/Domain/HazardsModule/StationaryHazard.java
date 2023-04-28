package gotcha.server.Domain.HazardsModule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gotcha.server.Domain.RatingModule.HazardRateCalculator;
import gotcha.server.Utils.Location;

public class StationaryHazard {

    @JsonIgnore
    private int id;
    private int ride_id; // will be -1 if the hazard added by admin.
    private Location location;
    private String city;
    private HazardType type;
    private double size;
    private double rate;
    private boolean report;

    // TODO: 28/04/2023 : Add photo field.




    public StationaryHazard(int id, int ride_id, Location location, String city, HazardType type, double size) {
        this.id = id;
        this.ride_id = ride_id;
        this.location = location;
        this.city = city;
        this.type = type;
        this.size = size;
        this.report = false;
        this.setRate();
    }
    

    // Default Constructor for deserialization
    public StationaryHazard() {}


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRide_id() {
        return ride_id;
    }
    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public HazardType getType() {
        return type;
    }
    public void setType(HazardType type) {
        this.type = type;
    }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }


    public double getRate() {
        return rate;
    }


    public void setRate() {
        HazardRateCalculator hazardRateCalculator = HazardRateCalculator.get_instance();
        this.rate = hazardRateCalculator.rate_hazard(this);

    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public StationaryHazardDAO getDAO(){
        return new StationaryHazardDAO(this);
    }
}
