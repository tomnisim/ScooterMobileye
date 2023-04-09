package gotcha.server.Domain.AwardsModule;

import java.time.LocalDate;
import java.util.List;

public class Award {
    private int id;
    private String message;
    private String admin_email;
    private List<String> emails;

    private LocalDate date;

    public Award(int id, String message, String admin_email, List<String> emails){
        this.id = id;
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = LocalDate.now(); // TODO: use another constructor for loading DB!
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public List<String> getEmails() {
        return emails;
    }

    public LocalDate getDate() {
        return date;
    }
}
