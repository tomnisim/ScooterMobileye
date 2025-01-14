import datetime

import keyboard
from Config import ConfigurationController
from CameraModule.CameraController import CameraController
from Config.Constants import Constants
from GPSModule.GPSController import GPSController
from GPSModule.Location import Location
from PersistenceModule.PersistenceController import PersistenceController
from RidesModule.Ride import Ride
from Service.EndButtonThread import EndButtonThread
from Utils.Logger import ride_logger, system_logger
from VideoProccessorModule.EventDetector import EventDetector
from VideoProccessorModule.HazardDetector import HazardDetector
from VideoProccessorModule.RoadDetector import RoadDetector
from moviepy.editor import VideoFileClip
import RPi.GPIO as GPIO
import threading
import time
from PIL import Image



junctions = []
hazards = []
frames = []
id = 0
stop = False
end_button = False
stop_flag = False



def collect_junctions_task(gps_controller):
    system_logger.info(f'Start thread collect_junctions_task')
    global junctions
    junction_no = 1 
    while not stop:
        loc = gps_controller.get_location()
        junctions.append(loc)
        ride_logger.info(f'Junction #{junction_no} Was Collected - TIME -> {datetime.datetime.now()}')
        junction_no+=1
        time_between_junctions = Constants.get_instance().get_time_between_junctions()
        time.sleep(float(time_between_junctions))

def frames_and_detection_task(camera_controller, gps_controller, hazard_detector, alerter, num):
    global hazards
    system_logger.info(f'STRAT THREAD frames_and_detection_task')
    while not stop:
        image, image_path = camera_controller.get_next_frame()
        loc = gps_controller.get_location()
        ride_logger.info(f'get frame number {num}')
        current_hazards = hazard_detector.detect_hazards_in_frame(image, image_path, loc)
        ride_logger.info(f'Detect hazard number {num}')
        hazards_detect = False
        num+=1
        if len(current_hazards) > 0:
            alerter.alert()
            hazards += current_hazards
            hazards_detect = True
        # ADD to ride logger
        hazards_detect_msg = 'YES' if hazards_detect else 'NO'
        ride_logger.info(
            f'Frame -> Location : lan = {loc.longitude} , lng  = {loc.latitude} \n Time : {datetime.datetime.now()} \n hazard detected : {hazards_detect_msg}\n')
class RideController:
    def __init__(self, alerter, gps_controller, camera_controller):
        system_logger.info(f'Ride Controller initialization')
        self.alerter = alerter
        self._GPS_controller = gps_controller
        self._camera_controller = camera_controller

        # create road, event, hazard detectors
        self._event_detector = EventDetector()
        self._road_detector = RoadDetector()
        self._hazard_detector = HazardDetector()
        

    def execute_ride(self):
        system_logger.info('execute ride')
        global stop
        global frames
        global hazards
        global stop_flag
        stop = False
        

        junctions_thread = threading.Thread(target=collect_junctions_task, args=(self._GPS_controller,))
        frames_and_detection_thread = threading.Thread(target= frames_and_detection_task, args=(self._camera_controller, self._GPS_controller, self._hazard_detector, self.alerter, 1))

    

        start_time = datetime.datetime.now()
        start_loc = self._GPS_controller.get_location_realtime()
        junctions_thread.start()
        frames_and_detection_thread.start()


        # create thread that manage end button
        end_button_thread = EndButtonThread()
        endbutton_thread = threading.Thread(target=end_button_thread.task())
        endbutton_thread.start()
        while True:
            if not end_button_thread.get_end_button():
                a=5
            else:
                break
            

        system_logger.info(f"----------------------------- Finish ride  ---------------------------------------------")
        
        stop = True

        finish_time = datetime.datetime.now()
        destination_loc = self._GPS_controller.get_location_mock()
        ride = Ride(hazards, start_loc, destination_loc, start_time, finish_time, junctions)
        ride_logger.info(vars(ride))
        return ride
      
