package gotcha.server.Domain.QuestionsModule;

import gotcha.server.DAL.HibernateUtils;
import gotcha.server.Domain.UserModule.Admin;
import gotcha.server.Domain.UserModule.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
@Component
public class QuestionController implements IQuestionController {

    private Map<Integer, Question> open_questions;
    private Map<String, List<Question>> users_questions;
    private AtomicInteger question_ids_counter;
    public QuestionController(){
        this.open_questions = new ConcurrentHashMap<>();
        this.users_questions = new ConcurrentHashMap<>();
        this.question_ids_counter = new AtomicInteger(1);
    }


    // TODO: 14/12/2022 implement
    public void load() {
//        this.questionsMap = HibernateUtils.get_questions();
        this.question_ids_counter = new AtomicInteger(HibernateUtils.get_max_question_id());
    }


    @Override
    public void add_user_question(String message, String senderEmail, BiConsumer<String, Integer> update_function) {

    }

    /**
     * this method for an admin who answer user question
     * the method will set the answer on the question and will return the sending user email
     * @param question_id
     * @param answer
     * @param adminEmail
     * @throws Exception
     */
    @Override
    public String answer_user_question(int question_id, String answer, String adminEmail) throws Exception {
        if (!this.open_questions.containsKey(question_id))
        {
            throw new Exception("Question does not exist");
        }
        Question question = this.open_questions.get(question_id);
        question.set_answer(answer, adminEmail);
        this.open_questions.remove(question_id);
        return question.getSenderEmail();
    }

    @Override
    public Question get_question(int question_id) throws Exception {
        if (!this.open_questions.containsKey(question_id))
        {
            throw new Exception("Question does not exist");
        }
        return this.open_questions.get(question_id);
    }


    /**
     * this method is for a user who want to see his questions.
     * @param user_email
     * @return open & close questions of the user
     */
    @Override
    public List<String> get_all_user_questions(String user_email) {
        ArrayList<String> answer = new ArrayList();
        List<Question> user_questions = this.users_questions.get(user_email);
        for (Question question : user_questions){
            answer.add(question.toString_for_user());
        }
        return answer;
    }

    /**
     * this method is for an admin who want to see all the open questions.
     * @return all the open questions.
     */
    @Override

    public List<String> get_all_open_questions(){
        ArrayList<String> answer = new ArrayList<String>();
        for (Question question : this.open_questions.values()){
            answer.add(question.toString_for_admin());
        }
        return answer;
    }

    public int getQuestion_ids_counter() {
        return question_ids_counter.get();
    }


    private void notify_admins(String message) {
    }
}



