package gotcha.server.Domain.UserModule;

import gotcha.server.Domain.RatingModule.UserRateCalculator;

import java.time.LocalDate;

public class Rider extends User{
    private double rating;
    private String scooterType;
    private LocalDate licenceIssueDate;

    private String raspberryPiSerialNumber;

    public Rider(String userEmail, String userPassword, String phoneNumber, LocalDate birthDay, String gender, String scooterType, LocalDate licenceIssueDate, String raspberryPiSerialNumber) {
        super(userEmail, userPassword, phoneNumber, birthDay, gender);
        this.licenceIssueDate = licenceIssueDate;
        this.scooterType = scooterType;
        this.rating = 0.0;
        this.raspberryPiSerialNumber = raspberryPiSerialNumber;
    }

    public Rider(){}

    public void update_rating(double rideRating) {
        UserRateCalculator userRateCalculator = new UserRateCalculator();
        this.rating = rating * 0.8 + rideRating * 0.2; // TODO: just an example, need to change
    }

    public void update_scooter_type(String scooterType) {
        this.scooterType = scooterType;
    }

    public void update_licence_issue_date(LocalDate licenceIssueDate) {
        this.licenceIssueDate = licenceIssueDate;
    }

}
