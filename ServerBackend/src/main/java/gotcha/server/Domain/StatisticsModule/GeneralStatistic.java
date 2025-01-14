package gotcha.server.Domain.StatisticsModule;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "general_statistics")
public class GeneralStatistic {
    @Id
    private final Long id = 1L;

    @Column(name = "total_advertisements", nullable = false)
    private int total_advertisements;

    @Column(name = "total_awards", nullable = false)
    private int total_awards;

    @Column(name = "total_hazards", nullable = false)
    private int total_hazards;

    @Column(name = "total_users_questions", nullable = false)
    private int total_users_questions;

    @Column(name = "total_admin_answers", nullable = false)
    private int total_admin_answers;

    @Column(name = "total_rides", nullable = false)
    private int total_rides;

    @Column(name = "total_users", nullable = false)
    private int total_users;

    @Column(name = "total_admins", nullable = false)
    private int total_admins;

    @Column(name = "last_initial_time", nullable = false)
    private String last_initial_time;

    public GeneralStatistic(){
        this.last_initial_time = LocalDateTime.now().toString();
    }

    public void update(int total_admins, int total_admin_answers, int total_advertisements, int total_awards, int total_rides,
                       int total_hazards, int total_users, int total_users_questions) {
        this.total_admins = total_admins;
        this.total_advertisements = total_advertisements;
        this.total_admin_answers = total_admin_answers;
        this.total_users = total_users;
        this.total_hazards = total_hazards;
        this.total_awards = total_awards;
        this.total_rides = total_rides;
        this.total_users_questions = total_users_questions;
    }

    public int getTotal_advertisements() {
        return total_advertisements;
    }

    public void setTotal_advertisements(int total_advertisements) {
        this.total_advertisements = total_advertisements;
    }

    public int getTotal_awards() {
        return total_awards;
    }

    public void setTotal_awards(int total_awards) {
        this.total_awards = total_awards;
    }

    public int getTotal_hazards() {
        return total_hazards;
    }

    public void setTotal_hazards(int total_hazards) {
        this.total_hazards = total_hazards;
    }

    public int getTotal_users_questions() {
        return total_users_questions;
    }

    public void setTotal_users_questions(int total_users_questions) {
        this.total_users_questions = total_users_questions;
    }

    public int getTotal_admin_answers() {
        return total_admin_answers;
    }

    public void setTotal_admin_answers(int total_admin_answers) {
        this.total_admin_answers = total_admin_answers;
    }

    public int getTotal_rides() {
        return total_rides;
    }

    public void setTotal_rides(int total_rides) {
        this.total_rides = total_rides;
    }

    public int getTotal_users() {
        return total_users;
    }

    public void setTotal_users(int total_users) {
        this.total_users = total_users;
    }

    public int getTotal_admins() {
        return total_admins;
    }

    public void setTotal_admins(int total_admins) {
        this.total_admins = total_admins;
    }

    public String getLast_initial_time() {
        return last_initial_time;
    }

    public void setLast_initial_time(String last_initial_time) {
        this.last_initial_time = last_initial_time;
    }

    public Long getId() {
        return id;
    }
}
