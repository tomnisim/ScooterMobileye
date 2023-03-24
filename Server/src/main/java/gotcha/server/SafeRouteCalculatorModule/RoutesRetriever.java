package gotcha.server.SafeRouteCalculatorModule;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.Utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoutesRetriever {
    private final MapsAdapter google_maps;
    private final HazardController hazard_controller;
    private final Configuration configuration;
    private static int NUMBER_OF_ROUTES ; // TODO: 28/12/2022  from config

    @Autowired
    public RoutesRetriever(MapsAdapter maps_implementation,HazardController hazardController, Configuration configuration)
    {
        this.google_maps = maps_implementation; // TODO - fetch google maps class properly
        this.configuration = configuration;
        this.hazard_controller = hazardController;

    }

    /**
     *
     * @param source
     * @param destination
     * @return List of routs sort by safe rate - index 0 for safest route
     */
    public List<Route> fetch_safe_routes(Location source, Location destination)
    {
        return null;
/*        // TODO - add number_of_routes to utils , from Appendix A
        List<Route> routes = this.google_maps.get_routes(source, destination, number_of_routes); // TODO - API request to get routes
        List<Route> sorted_routes_by_safety = this.rate_routes(routes);
        return sorted_routes_by_safety; //TODO - maybe return the rating for each route to show it to the rider*/
    }

    private List<Route> rate_routes(List<Route> routes) {

        Hashtable<Double, Route> routes_by_rating = new Hashtable<Double, Route>();
        for (Route route : routes)
        {
            Double rate = 0.0;
            List<StationaryHazard> hazards_in_route = this.hazard_controller.get_hazards_in_route(route);
            rate = 0.0;
            for (StationaryHazard hazard :hazards_in_route )
            {
                rate += hazard.getRate();
            }
            routes_by_rating.put(rate, route);
        }
        List<Double> ratings = Collections.list(routes_by_rating.keys());
        Collections.sort(ratings);
        List<Route> sorted_routes_by_safety = new ArrayList<Route>();
        for (Double rate: ratings)
        {
            sorted_routes_by_safety.add(routes_by_rating.get(rate));
        }
        return sorted_routes_by_safety;


    }
}
