import Ride
import datetime

from CameraModule.CameraController import CameraController
from GPSModule.GPSController import GPSController
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector


class RideController():
    def __init__(self, alerter):

        self.rides={}
        self._GPS_controller = GPSController.get_instance()
        self._camera_controller = CameraController.get_instance()
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()
        self.alerter = alerter
        self.end_curr_ride = False


        self.events_detector = EventDetector()

    def end_ride(self):
        self.end_curr_ride = True

    def start_ride(self):
        start_time = datetime.datetime.now()
        hazards = []
        events = []  # speed changes, sharp turns..

        start_location = self._GPS_controller.get_location()


        # todo : implement event who finish the loop - the ride is over.
        while not self.end_curr_ride:
            frame = self._camera_controller.get_next_frame()
            self._road_detector.detect(frame)
            current_hazards = self._hazard_detector.detect_hazards_in_frame(frame)
            for hazard in current_hazards:
                self.alerter.alert()
                hazards.append(hazard)

            # todo : what data should event hold? detector should return event.
            speed_change_event = self.events_detector.detect_speed_change(frame)
            if speed_change_event:
                events.append(speed_change_event)
            sharp_turn_event = self.events_detector.detect_sharp_turn(frame)
            if sharp_turn_event:
                events.append(sharp_turn_event)



        end_time = datetime.datetime.now()
        destination_location = self._GPS_controller.get_location()
        city = self._GPS_controller.get_city(destination_location)
        sideway_precent, roadway_precent = self._road_detector.calculate_percentages()
        self.finish_ride(city, sideway_precent, roadway_precent, hazards, events, start_location, destination_location, start_time, end_time)

    def finish_ride(self, city, sideway_precent, roadway_precent, hazards, events, start_location, destination_location, start_time, end_time):
        ride = Ride(city, sideway_precent, roadway_precent, hazards, events, start_location, destination_location, start_time, end_time)
        # todo :  send the server user's & ride's data.
