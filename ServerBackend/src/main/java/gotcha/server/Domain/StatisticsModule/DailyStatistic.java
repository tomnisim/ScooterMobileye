package gotcha.server.Domain.StatisticsModule;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dailyStatistics")
public class DailyStatistic {
    @Id
    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_day_advertisements", nullable = false)
    private int start_day__advertisements;

    @Column(name = "total_advertisements", nullable = false)
    private int total_advertisements;

    @Column(name = "start_day_awards", nullable = false)
    private int start_day__awards;

    @Column(name = "total_awards", nullable = false)
    private int total_awards;

    @Column(name = "start_day_hazards", nullable = false)
    private int start_day__hazards;

    @Column(name = "total_hazards", nullable = false)
    private int total_hazards;

    @Column(name = "start_day_users_questions", nullable = false)
    private int start_day__users_questions;

    @Column(name = "total_users_questions", nullable = false)
    private int total_users_questions;

    @Column(name = "start_day_admin_answers", nullable = false)
    private int start_day__admin_answers;

    @Column(name = "total_admin_answers", nullable = false)
    private int total_admin_answers;

    @Column(name = "start_day_rides", nullable = false)
    private int start_day__rides;

    @Column(name = "total_rides", nullable = false)
    private int total_rides;

    @Column(name = "start_day_users", nullable = false)
    private int start_day__users;

    @Column(name = "total_users", nullable = false)
    private int total_users;

    @Column(name = "start_day_admins", nullable = false)
    private int start_day__admins;

    @Column(name = "total_admins", nullable = false)
    private int total_admins;

    @Column(name = "online_users", nullable = false)

    private int online_users; // login

    @Column(name = "online_guests", nullable = false)

    private int online_guests; // logout

    @Column(name = "shutdown_events", nullable = false)

    private int shut_down_events;
    @Column(name = "reset_events", nullable = false)

    private int reset_events;




    // ----------------------- constructors ---------------------------------------------
    public DailyStatistic(int start_day__admins, int start_day__admin_answers,int start_day__advertisements,int start_day__awards,
                          int start_day__rides, int start_day__hazards,
                          int start_day__users, int start_day__users_questions) {
        this.date = LocalDate.now();

        this.start_day__advertisements = start_day__advertisements;
        this.start_day__awards = start_day__awards;
        this.start_day__hazards = start_day__hazards;
        this.start_day__users_questions = start_day__users_questions;
        this.start_day__admin_answers = start_day__admin_answers;
        this.start_day__rides = start_day__rides;
        this.start_day__users = start_day__users;
        this.start_day__admins = start_day__admins;

        this.update(start_day__admins, start_day__admin_answers, start_day__advertisements, start_day__awards, start_day__rides,
                start_day__hazards, start_day__users, start_day__users_questions);

        this.reset_events = 0;
        this.shut_down_events = 0;
        this.online_guests = 0;
        this.online_users = 0;
    }

    public DailyStatistic() {
    }


    /**
     * Constructor for DB loading
     * @param date
     * @param start_day__advertisements
     * @param total_advertisements
     * @param start_day__awards
     * @param total_awards
     * @param start_day__hazards
     * @param total_hazards
     * @param start_day__users_questions
     * @param total_users_questions
     * @param start_day__admin_answers
     * @param total_admin_answers
     * @param start_day__rides
     * @param total_rides
     * @param start_day__users
     * @param total_users
     * @param start_day__admins
     * @param total_admins
     * @param online_users
     * @param online_guests
     * @param shut_down_events
     * @param reset_events
     */
    public void load(LocalDate date, int start_day__advertisements, int total_advertisements, int start_day__awards, int total_awards, int start_day__hazards, int total_hazards, int start_day__users_questions, int total_users_questions, int start_day__admin_answers, int total_admin_answers, int start_day__rides, int total_rides, int start_day__users, int total_users, int start_day__admins, int total_admins, int online_users, int online_guests, int shut_down_events, int reset_events) {
        this.date = date;
        this.start_day__advertisements = start_day__advertisements;
        this.total_advertisements = total_advertisements;
        this.start_day__awards = start_day__awards;
        this.total_awards = total_awards;
        this.start_day__hazards = start_day__hazards;
        this.total_hazards = total_hazards;
        this.start_day__users_questions = start_day__users_questions;
        this.total_users_questions = total_users_questions;
        this.start_day__admin_answers = start_day__admin_answers;
        this.total_admin_answers = total_admin_answers;
        this.start_day__rides = start_day__rides;
        this.total_rides = total_rides;
        this.start_day__users = start_day__users;
        this.total_users = total_users;
        this.start_day__admins = start_day__admins;
        this.total_admins = total_admins;
        this.online_users = online_users;
        this.online_guests = online_guests;
        this.shut_down_events = shut_down_events;
        this.reset_events = reset_events;
    }



    // --------------------- getters -----------------------------------


    public LocalDate getDate() {
        return date;
    }

    public int getNew_advertisements() {
        return total_advertisements - start_day__advertisements;
    }

    public int getTotal_advertisements() {
        return total_advertisements;
    }

    public int getNew_awards() {
        return total_awards - start_day__awards;
    }

    public int getTotal_awards() {
        return total_awards;
    }

    public int getNew_hazards() {
        return total_hazards - start_day__hazards;
    }

    public int getTotal_hazards() {
        return total_hazards;
    }

    public int getNew_users_questions() {
        return total_users_questions - start_day__users_questions;
    }

    public int getTotal_users_questions() {
        return total_users_questions;
    }

    public int getNew_admin_answers() {
        return total_admin_answers - start_day__admin_answers;
    }

    public int getTotal_admin_answers() {
        return total_admin_answers;
    }

    public int getNew_rides() {
        return total_rides - start_day__rides;
    }

    public int getTotal_rides() {
        return total_rides;
    }

    public int getNew_users() {
        return total_users - start_day__users;
    }

    public int getTotal_users() {
        return total_users;
    }

    public int getNew_admins() {
        return total_admins - start_day__admins;
    }

    public int getTotal_admins() {
        return total_admins;
    }

    public int getOnline_users() {
        return online_users;
    }


    public int getOnline_guests() {
        return online_guests;
    }


    public int getShut_down_events() {
        return shut_down_events;
    }


    public int getReset_events() {
        return reset_events;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotal_advertisements(int total_advertisements) {
        this.total_advertisements = total_advertisements;
    }

    public void setTotal_awards(int total_awards) {
        this.total_awards = total_awards;
    }

    public void setTotal_hazards(int total_hazards) {
        this.total_hazards = total_hazards;
    }

    public void setTotal_users_questions(int total_users_questions) {
        this.total_users_questions = total_users_questions;
    }

    public void setTotal_admin_answers(int total_admin_answers) {
        this.total_admin_answers = total_admin_answers;
    }

    public void setTotal_rides(int total_rides) {
        this.total_rides = total_rides;
    }

    public void setTotal_users(int total_users) {
        this.total_users = total_users;
    }

    public void setTotal_admins(int total_admins) {
        this.total_admins = total_admins;
    }

    public void setOnline_users(int online_users) {
        this.online_users = online_users;
    }

    public void setOnline_guests(int online_guests) {
        this.online_guests = online_guests;
    }

    public void setShut_down_events(int shut_down_events) {
        this.shut_down_events = shut_down_events;
    }

    public void setReset_events(int reset_events) {
        this.reset_events = reset_events;
    }


    public void update(int total_admins, int total_admin_answers, int total_advertisements, int total_awards, int total_rides,
                       int total_hazards, int total_users, int total_users_questions) {
        this.setTotal_admins(total_admins);
        this.setTotal_advertisements(total_advertisements);
        this.setTotal_awards(total_awards);
        this.setTotal_admin_answers(total_admin_answers);
        this.setTotal_rides(total_rides);
        this.setTotal_users(total_users);
        this.setTotal_users_questions(total_users_questions);
        this.setTotal_hazards(total_hazards);

    }

    public DailyStatisticDTO getDTO() {
        return new DailyStatisticDTO(this);
    }

    public int getStart_day__advertisements() {
        return start_day__advertisements;
    }

    public int getStart_day__awards() {
        return start_day__awards;
    }

    public int getStart_day__hazards() {
        return start_day__hazards;
    }

    public int getStart_day__users_questions() {
        return start_day__users_questions;
    }

    public int getStart_day__admin_answers() {
        return start_day__admin_answers;
    }

    public int getStart_day__rides() {
        return start_day__rides;
    }

    public int getStart_day__users() {
        return start_day__users;
    }

    public int getStart_day__admins() {
        return start_day__admins;
    }

    public void setStart_day__advertisements(int start_day__advertisements) {
        this.start_day__advertisements = start_day__advertisements;
    }

    public void setStart_day__awards(int start_day__awards) {
        this.start_day__awards = start_day__awards;
    }

    public void setStart_day__hazards(int start_day__hazards) {
        this.start_day__hazards = start_day__hazards;
    }

    public void setStart_day__users_questions(int start_day__users_questions) {
        this.start_day__users_questions = start_day__users_questions;
    }

    public void setStart_day__admin_answers(int start_day__admin_answers) {
        this.start_day__admin_answers = start_day__admin_answers;
    }

    public void setStart_day__rides(int start_day__rides) {
        this.start_day__rides = start_day__rides;
    }

    public void setStart_day__users(int start_day__users) {
        this.start_day__users = start_day__users;
    }

    public void setStart_day__admins(int start_day__admins) {
        this.start_day__admins = start_day__admins;
    }
}
