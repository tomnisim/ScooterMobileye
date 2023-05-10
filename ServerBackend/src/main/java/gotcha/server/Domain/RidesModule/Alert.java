package gotcha.server.Domain.RidesModule;

import gotcha.server.Utils.Location;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Alert")
public class Alert extends RidingAction {
    public Alert(LocalDateTime time, Location location) {
        super(time, location);
    }

    public Alert() {
    }
    // TODO: 27/03/2023 : implement
}

