package gotcha.server.Service.Communication;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Service.Facade;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ApiController implements AdminAPI, UserAPI  {
    private final Facade facade;
    private final String USER_CONTEXT_ATTRIBUTE_NAME = "userContext";

    @Autowired
    public ApiController(Facade facade){
        this.facade = facade;

    }


    /**
     * this method will create a new facade for the connection.
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String email, String password, HttpSession session){
        System.out.println("login");
        // TODO: Maybe implement Service Layer User object
        Response<User> response = facade.login(email, password);
        if(response.getValue() != null) {
            var userContext = new UserContext(response.getValue());
            session.setAttribute(USER_CONTEXT_ATTRIBUTE_NAME, userContext);
        }
        return response;
    }


    /**
     * this method will clear the facade.
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    @CrossOrigin
    @Override
    public Response logout(HttpSession session) {
        Response response = facade.logout();
        session.removeAttribute(USER_CONTEXT_ATTRIBUTE_NAME);
        return response;
    }

    @RequestMapping(value = "/register")
    @CrossOrigin
    @Override
    public Response register(String email, String password, String name, String last_name, String birth_date, String phone_number, String gender) {
        // TODO: 04/01/2023 : implement according new sequence diagram
        return null;
    }

    @RequestMapping(value = "/change_password")
    @CrossOrigin
    @Override
    public Response change_password(String old_password, String password, @SessionAttribute("userContext") UserContext userContext) {
        return facade.change_password(old_password, password, userContext);
    }

    @RequestMapping(value = "/view_user_rides_history")
    @CrossOrigin
    public Response view_user_rides_history(@SessionAttribute("userContext") UserContext userContext){
        return facade.view_user_rides_history(userContext);
    }

    @RequestMapping(value = "/add_user_question")
    @CrossOrigin
    @Override
    public Response add_user_question(String message, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_user_question(message, userContext);
    }

    @RequestMapping(value = "/view_all_user_questions")
    @CrossOrigin
    @Override
    public Response view_all_user_questions(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_user_questions(userContext);
    }

    @RequestMapping(value = "/get_safe_routes")
    @CrossOrigin
    @Override
    public Response get_safe_routes(Location origin, Location destination, @SessionAttribute("userContext") UserContext userContext) {
        return facade.get_safe_routes(origin, destination, userContext);
    }

    @RequestMapping(value = "/view_all_advertisement")
    @CrossOrigin
    @Override
    public Response view_all_advertisement(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_advertisements(userContext);
    }





    /* the methods for RP */


    /**
     * this method is for RP usage, when user is not have to be logged in.
     * this method will create a new facade for rp connection and remove it after finish in case of dismiss connection,
     * and will use an existing facade and not remove him else.
     * @param userEmail
     * @param origin
     * @param destination
     * @param city
     * @param start_time
     * @param end_time
     * @param hazards
     * @return
     */
    @RequestMapping(value = "/finish_ride")
    @CrossOrigin
    @Override
    public Response finish_ride(String userEmail, Location origin, Location destination, String city, LocalDateTime start_time,
                                LocalDateTime end_time, List<StationaryHazard> hazards) {

        Response response = facade.finish_ride(userEmail,origin, destination, city, start_time, end_time, hazards);
        return response;
    }

    /**
     * this method is for RP usage, when user is not have to be logged in.
     * this method will create a new facade for rp connection and remove it after finish in case of dismiss connection,
     * and will use an existing facade and not remove him else.
     * @return
     */
    @RequestMapping(value = "/get_rp_config_file")
    @CrossOrigin
    @Override
    public Response get_rp_config_file() {
        // TODO: Not sure we even need this, the config file should be injected
        String config_file_data = "test";
        Response response = new Response(config_file_data, "rp config file got successfully");
        return response;
    }








    // ADMIN METHODS




    @RequestMapping(value = "/view_all_open_questions")
    @CrossOrigin
    @Override
    public Response view_all_open_questions(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_open_questions(userContext);
    }

    @RequestMapping(value = "/answer_user_question")
    @CrossOrigin
    @Override
    public Response answer_user_question(int question_id, String answer, @SessionAttribute("userContext") UserContext userContext) {
        return facade.answer_user_question(question_id, answer, userContext);
    }

    @RequestMapping(value = "/send_message_to_all_users")
    @CrossOrigin
    @Override
    public Response send_message_to_all_users(String message, @SessionAttribute("userContext") UserContext userContext) {
        return facade.send_message_to_all_users(message, userContext);
    }

    @RequestMapping(value = "/view_rides")
    @CrossOrigin
    @Override
    public Response view_rides(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_rides(userContext);

    }

    @RequestMapping(value = "/view_statistics")
    @CrossOrigin
    @Override
    public Response view_statistics(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_statistics(userContext);
    }

    @RequestMapping(value = "/view_advertisements")
    @CrossOrigin
    @Override
    public Response view_advertisements(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_advertisements(userContext);
    }

    @RequestMapping(value = "/add_advertisement")
    @CrossOrigin
    @Override
    public Response add_advertisement(LocalDateTime final_date, String owner, String message, String photo, String url, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_advertisement(final_date, owner, message, photo, url, userContext);
    }

    @RequestMapping(value = "/delete_advertisement")
    @CrossOrigin
    @Override
    public Response delete_advertisement(int advertise_id, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_advertisement(advertise_id, userContext);
    }

    @RequestMapping(value = "/view_awards")
    @CrossOrigin
    @Override
    public Response view_awards(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_awards(userContext);
    }

    @RequestMapping(value = "/add_award")
    @CrossOrigin
    @Override
    public Response add_award(@SessionAttribute("userContext") UserContext userContext) {
        return null;
    }

    @RequestMapping(value = "/delete_award")
    @CrossOrigin
    @Override
    public Response delete_award(int award_id, @SessionAttribute("userContext") UserContext userContext) {
         return facade.delete_award(award_id, userContext);
    }

    @RequestMapping(value = "/view_admins")
    @CrossOrigin
    @Override
    public Response view_admins(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_admins(userContext);
    }

    @RequestMapping(value = "/add_admin")
    @CrossOrigin
    @Override
    public Response add_admin(String user_email, String user_password, String phoneNumber, LocalDate birthDay, String gender, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_admin(user_email, user_password, phoneNumber, birthDay, gender, userContext);
    }

    @RequestMapping(value = "/delete_admin")
    @CrossOrigin
    @Override
    public Response delete_admin(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_admin(user_email, userContext);
    }

    @RequestMapping(value = "/view_users")
    @CrossOrigin
    @Override
    public Response view_users(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_users(userContext);
    }

    @RequestMapping(value = "/edit_user")
    @CrossOrigin
    @Override
    public Response edit_user(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return null;
    }

    @RequestMapping(value = "/delete_user")
    @CrossOrigin
    @Override
    public Response delete_user(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_user(user_email, userContext);
    }




}