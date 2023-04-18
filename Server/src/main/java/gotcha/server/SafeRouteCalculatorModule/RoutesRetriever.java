package gotcha.server.SafeRouteCalculatorModule;

import gotcha.server.Config.Configuration;
import gotcha.server.Domain.HazardsModule.HazardController;
import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.ExternalService.MapsAdapter;
import gotcha.server.ExternalService.MapsAdapterImpl;
import gotcha.server.ExternalService.MapsAdapterRealTime;
import gotcha.server.Utils.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoutesRetriever {
    private final MapsAdapter google_maps;
    private final HazardController hazard_controller;
    private final Configuration configuration;

    @Autowired
    public RoutesRetriever(HazardController hazardController, Configuration configuration)
    {
        this.configuration = configuration;
        this.hazard_controller = hazardController;
        if (configuration.getMapsAdapter().equals("develop"))
        {
            this.google_maps = new MapsAdapterImpl();
        }
        else
        {
            this.google_maps = new MapsAdapterRealTime();
        }

    }

    /**
     *
     * @param source - location of rider
     * @param destination - location to navigate.
     * @return List of routs sort by safe rate - index 0 for safest route
     */
    public List<Route> fetch_safe_routes(String source, String destination)
    {
        List<Route> routes = this.google_maps.get_routes(source, destination, configuration.getNumberOfRoutes());
        return this.rate_routes(routes);
    }

    /**
     * @param routes from origin to dest.
     * @return sorted list of routes by acceding hazards rate order.
     */
    private List<Route> rate_routes(List<Route> routes) {

        Hashtable<Double, Route> routes_by_rating = new Hashtable<Double, Route>();
        for (Route route : routes)
        {
            double rate = 0.0;
            List<StationaryHazard> hazards_in_route = this.hazard_controller.get_hazards_in_route(route);
            rate = 0.0;
            for (StationaryHazard hazard : hazards_in_route)
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