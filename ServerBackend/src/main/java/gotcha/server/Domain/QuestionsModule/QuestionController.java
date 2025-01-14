package gotcha.server.Domain.QuestionsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
@Component
public class QuestionController implements IQuestionController {
    private final QuestionsRepository questionsRepository;

    @Autowired
    public QuestionController(QuestionsRepository questionsRepository){
        this.questionsRepository = questionsRepository;

    }
    @Override
    public void add_user_question(String message, String senderEmail, BiConsumer<String, Integer> update_function) throws Exception {
        if (message.equals("")){
            throw new Exception("message cant be blank");
        }
        if (message.contains("<") || message.contains(">")){
            throw new Exception("no scripts allowed.");
        }
//        Question question = new Question(message, senderEmail);
        Question question = new Question(LocalDateTime.now(), LocalDateTime.now(), message, "", false, senderEmail,  "");
        this.questionsRepository.addQuestion(question);
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
        return this.questionsRepository.answerOpenQuestion(question_id, answer, adminEmail);
    }

    @Override
    public Question get_question(int question_id) throws Exception {
        return questionsRepository.getOpenQuestion(question_id);
    }


    /**
     * this method is for a user who want to see his questions.
     * @param user_email
     * @return open & close questions of the user
     */
    @Override
    public List<QuestionDTO> get_all_user_questions(String user_email) {
        ArrayList<QuestionDTO> answer = new ArrayList();
        List<Question> users_questions_from_db = this.questionsRepository.getUsersQuestions(user_email);
        for (Question question : users_questions_from_db)
            answer.add(new QuestionDTO(question));
        return answer;
    }

    /**
     * this method is for an admin who want to see all the open questions.
     * @return all the open questions.
     */
    @Override
    public List<Question> get_all_open_questions(){
        ArrayList<Question> answer = new ArrayList<Question>();
        for (Question question : this.questionsRepository.getAllOpenQuestions()){
            answer.add(question);
        }
        return answer;
    }
    private void notify_admins(String message) {
    }
}



