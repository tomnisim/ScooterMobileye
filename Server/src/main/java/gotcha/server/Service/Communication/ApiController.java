package gotcha.server.Service.Communication;

import gotcha.server.Domain.HazardsModule.StationaryHazard;
import gotcha.server.Domain.UserModule.User;
import gotcha.server.Service.API.AdminAPI;
import gotcha.server.Service.API.UserAPI;
import gotcha.server.Service.Communication.Requests.LoginRequest;
import gotcha.server.Service.Communication.Requests.RegisterRequest;
import gotcha.server.Service.Facade;
import gotcha.server.Service.UserContext;
import gotcha.server.Utils.Location;
import gotcha.server.Utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ApiController implements AdminAPI, UserAPI {
    private final Facade facade;
    private final String USER_CONTEXT_ATTRIBUTE_NAME = "userContext";

    @Autowired
    public ApiController(Facade facade){
        this.facade = facade;

    }


    /**
     * this method will create a new facade for the connection.
     * @param loginRequest
     * @param session
     * @return
     */
    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(@RequestBody LoginRequest loginRequest, HttpSession session){
        System.out.println("login");
        // TODO: Maybe implement Service Layer User object
        Response<User> response = facade.login(loginRequest);
        if(!response.iswas_exception()) {
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
    public Response logout(HttpSession session, @SessionAttribute("userContext") UserContext userContext) {
        Response response = facade.logout(userContext);
        if (!response.iswas_exception())
            session.removeAttribute(USER_CONTEXT_ATTRIBUTE_NAME);
        return response;
    }

    @RequestMapping(value = "/register")
    @CrossOrigin
    @Override
    public Response<Boolean> register(@RequestBody RegisterRequest registerRequest) {
        // TODO: 04/01/2023 : implement according new sequence diagram
        var response = facade.register(registerRequest);
        return response;
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
    public Response get_safe_routes(String origin, String destination, @SessionAttribute("userContext") UserContext userContext) {
        return facade.get_safe_routes(origin, destination, userContext);
    }

    @RequestMapping(value = "/view_all_advertisement")
    @CrossOrigin
    @Override
    public Response view_all_advertisement(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_all_advertisements(userContext);
    }

    //todo : edit with facade
    @RequestMapping(value = "/view_notifications")
    @CrossOrigin
    @Override
    public Response view_notifications(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_notifications(userContext);
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
    public Response add_award(@SessionAttribute("userContext") UserContext userContext, String[] emails, String award) {
        return facade.add_award(emails,award, userContext);
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
    public Response add_admin(String user_email,String name, String lastName, String user_password, String phoneNumber, LocalDate birthDay, String gender, @SessionAttribute("userContext") UserContext userContext) {
        return facade.add_admin(user_email,name, lastName, user_password, phoneNumber, birthDay, gender, userContext);
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

    @RequestMapping(value = "/delete_user")
    @CrossOrigin
    @Override
    public Response delete_user(String user_email, @SessionAttribute("userContext") UserContext userContext) {
        return facade.delete_user(user_email, userContext);
    }


    // super admin API


    @RequestMapping(value = "/view_server_logger")
    @CrossOrigin
    @Override
    public Response view_server_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_server_logger(userContext);
    }

    @RequestMapping(value = "/reset")
    @CrossOrigin
    @Override
    public Response reset(@SessionAttribute("userContext") UserContext userContext) {
        return facade.reset(userContext);
    }


    @RequestMapping(value = "/shut_down")
    @CrossOrigin
    @Override
    public Response shut_down(@SessionAttribute("userContext") UserContext userContext) {
        return facade.shut_down(userContext);
    }

    @RequestMapping(value = "/set_server_config")
    @CrossOrigin
    @Override
    public Response set_server_config(@SessionAttribute("userContext") UserContext userContext) {
        return facade.set_server_config(userContext);
    }

    @RequestMapping(value = "/set_rp_config")
    @CrossOrigin
    @Override
    public Response set_rp_config(@SessionAttribute("userContext") UserContext userContext) {
        return facade.set_rp_config(userContext);
    }

    @RequestMapping(value = "/view_error_logger")
    @CrossOrigin
    @Override
    public Response view_error_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_error_logger(userContext);
    }

    @RequestMapping(value = "/view_system_logger")
    @CrossOrigin
    @Override
    public Response view_system_logger(@SessionAttribute("userContext") UserContext userContext) {
        return facade.view_system_logger(userContext);
    }




}
