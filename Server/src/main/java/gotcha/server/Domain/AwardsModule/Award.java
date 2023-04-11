package gotcha.server.Domain.AwardsModule;

import gotcha.server.Utils.HibernateConverters.StringArrayConverter;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="awards")
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="message", columnDefinition = "TEXT")
    private String message;

    @Column(name="adminEmail", columnDefinition = "VARCHAR")
    private String admin_email;

    @Column(name="emails", columnDefinition = "TEXT")
    @Convert(converter = StringArrayConverter.class)
    private List<String> emails;

    private LocalDate date;

    /**
     * BL Constructor for new Award, create time -> now().
     * @param id
     * @param message
     * @param admin_email
     * @param emails
     */
    public Award(int id, String message, String admin_email, List<String> emails){
        this.id = id;
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = LocalDate.now();
    }

    /**
     * Constructor for loading DB.
     * @param id
     * @param message
     * @param admin_email
     * @param emails
     * @param date
     */
    public Award(int id, String message, String admin_email, List<String> emails, LocalDate date){
        this.id = id;
        this.message = message;
        this.admin_email = admin_email;
        this.emails = emails;
        this.date = date;
    }

    public Award(){}

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
