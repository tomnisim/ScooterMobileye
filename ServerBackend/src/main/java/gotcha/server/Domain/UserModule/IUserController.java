package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.RidesModule.Ride;
import gotcha.server.Utils.Exceptions.UserExceptions.UserNotFoundException;
import gotcha.server.Utils.Observable;

import java.time.LocalDate;
import java.util.List;

public interface IUserController extends Observable {
    User get_user_by_email(String userEmail) throws UserNotFoundException;
    List<User> get_all_users();
    Boolean register(String userEmail, String password,String name, String lastName, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) throws Exception;
    User login(String userEmail, String password) throws Exception;
    void logout(String userEmail) throws Exception;
    void appoint_new_admin(String newAdminEmail,String name, String lastName, String password, String phoneNumber, LocalDate birthDay, String gender, String appointingAdminEmail) throws Exception;
    void reply_to_user_question(String adminEmail, String reply, int question_id) throws Exception;
    void send_question_to_admin(String userEmail, String message) throws Exception;
    List<AdminDTO> view_admins();
    List<RiderDTO> get_all_riders();
    List<WaitingRaspberryPiDTO> get_waiting_rp();
    void add_rp_serial_number(String rpSerial) throws Exception;
    void change_password(String userEmail, String oldPassword, String newPassword) throws Exception;
    void remove_admin_appointment(String user_email, String admin_email) throws Exception;
    void delete_user(String user_email) throws Exception ;
    void notify_all_users(String senderEmail, String message) throws Exception;
    void notify_users(String senderEmail, List<String> emails, String message) throws UserNotFoundException;
    // user id -> user email
    void update_user_rate(String user_id, Ride ride, int number_of_rides) throws Exception;
    String resetPassword(String userEmail) throws UserNotFoundException;
    String get_user_email_by_rp_serial(String rpSerialNumber) throws Exception;

    Boolean update_information(String userEmail , String name, String lastName, String phone, LocalDate birthDate, String gender, String scooterType) throws Exception;
}
