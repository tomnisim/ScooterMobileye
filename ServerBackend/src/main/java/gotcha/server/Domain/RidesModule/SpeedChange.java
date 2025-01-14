package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SpeedChange")
public class SpeedChange extends RidingAction{

    @Column(name="startSpeed", nullable = false)
    private Double start_speed;
    @Column(name="finalSpeed", nullable = false)
    private Double final_speed;

    public SpeedChange() {

    }

    public SpeedChange(LocalDateTime time, Location location, Double start_speed, Double final_speed)
    {
        super(time,location);
        this.start_speed = start_speed;
        this.final_speed = final_speed;
    }


    // ------------------------------------------ Getters & Setters ----------------------------------------------------------

    public Double getStart_speed() {
        return start_speed;
    }

    public void setStart_speed(Double start_speed) {
        this.start_speed = start_speed;
    }

    public Double getFinal_speed() {
        return final_speed;
    }

    public void setFinal_speed(Double final_speed) {
        this.final_speed = final_speed;
    }
}
