from PersistenceModule import FilesController
from PersistenceModule.CommunicationController import CommunicationController


class PersistenceController():
    def __init__(self):
        self.files_controller = FilesController.get_instance()
        self.communication_controller = CommunicationController.get_instance()

    def save_ride(self, ride):
        waiting_rides = self.files_controller.get_waiting_rides()
        waiting_rides.append(ride)
        success = self.communication_controller.send_rides_to_server(waiting_rides)
        if not success:
            self.files_controller.save_ride(ride)
        else:
            self.files_controller.clear_waiting_rides()