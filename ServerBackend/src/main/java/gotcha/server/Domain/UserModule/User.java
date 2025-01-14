package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.Notifications.Notification;
import gotcha.server.Utils.Observer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class
User implements Observer {
    @Id
    @Column(name="email")
    private String userEmail;

    @Column(name="password", nullable = false)
    private String userPasswordToken;
    @Column(name="phoneNumber", nullable = false)

    private String phoneNumber;
    @Column(name="gender", nullable = false)

    private String gender;

    @Column(name="birthDay", nullable = false)

    private LocalDate birthDay;

    @Column(name="name", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", nullable = false)

    private String name;
    @Column(name="lastName", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", nullable = false)

    private String lastName;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_notifications",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private Set<Notification> userNotifications;

    private boolean loggedIn;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User(String userEmail, String name, String lastName, String userPasswordToken, String phoneNumber, LocalDate birthDay, String gender) {
        this.userEmail = userEmail;
        this.userPasswordToken = userPasswordToken;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.loggedIn = false;
        this.userNotifications = new HashSet<>();
        this.name = name;
        this.lastName = lastName;
    }

    // Empty constructor for DB
    public User() {

    }
    public String get_email() {
        return this.userEmail;
    }

    public String get_phone_number() {
        return this.phoneNumber;
    }

    public void change_password_token(String newToken){
        this.userPasswordToken = newToken;
    }
    public String get_gender() {
        return this.gender;
    }

    public LocalDate get_birth_day() {
        return this.birthDay;
    }

    public String get_password_token() {return  this.userPasswordToken;}

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean is_logged_in() {
        return this.loggedIn;
    }


    public synchronized void login(){
        this.loggedIn = true;
    }

    public synchronized void logout() throws Exception {
        if(!loggedIn)
        {
            var message = String.format("User with email %s is NOT logged in", userEmail);
            throw new Exception(message);
        }
        this.loggedIn = false;
    }

    /**
     * adds the notification to the list of notification and tries to notify the user
     * @param notification
     * @return
     */
    public boolean notify_user(Notification notification) {
        if (userNotifications.contains(notification))
            return false;
        userNotifications.add(notification);
        return true;

    }

    /**
     * function that will be called when user log in to show him all of his not seen notifications
     * @throws Exception
     */
    public void notify_user() throws Exception {
        for(var notification : userNotifications) {
            if (!notification.isWasSeen()) {
                notify_user(notification);
            }
        }
    }

    public Collection<Notification> get_notifications(){
        return this.userNotifications;
    }
    public Boolean is_admin() {
        return false;
    }

    public void edit_details(String phoneNumber, String gender) {
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public void update_information(String name, String lastName, String phone, LocalDate birthDate, String gender) {
        this.setBirthDay(birthDate);
        this.setGender(gender);
        this.setName(name);
        this.setLastName(lastName);
        this.setPhoneNumber(phone);

    }
}
