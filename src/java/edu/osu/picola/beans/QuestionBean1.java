/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.beans;

import edu.osu.picola.businesslogic.QuestionHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author akers.79
 */
@ManagedBean(name="questionBean")
@SessionScoped
public class QuestionBean1 {

    private int question_id;
    private String question;
    private String answer_to_question_explanation;
    private boolean is_multiple_choice;
    private String multiple_choice_answer;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String option_e;
    private boolean is_mp_question;
    private boolean is_bp_question;
    private int assignment_id;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_to_question_explanation() {
        return answer_to_question_explanation;
    }

    public void setAnswer_to_question_explanation(String answer_to_question_explanation) {
        this.answer_to_question_explanation = answer_to_question_explanation;
    }

    public boolean isIs_multiple_choice() {
        return is_multiple_choice;
    }

    public void setIs_multiple_choice(boolean is_multiple_choice) {
        this.is_multiple_choice = is_multiple_choice;
    }

    public String getMultiple_choice_answer() {
        return multiple_choice_answer;
    }

    public void setMultiple_choice_answer(String multiple_choice_answer) {
        this.multiple_choice_answer = multiple_choice_answer;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getOption_e() {
        return option_e;
    }

    public void setOption_e(String option_e) {
        this.option_e = option_e;
    }

    public boolean isIs_mp_question() {
        return is_mp_question;
    }

    public void setIs_mp_question(boolean is_mp_question) {
        this.is_mp_question = is_mp_question;
    }

    public boolean isIs_bp_question() {
        return is_bp_question;
    }

    public void setIs_bp_question(boolean is_bp_question) {
        this.is_bp_question = is_bp_question;
    }

    public void createBP(ActionEvent event){
        setIs_bp_question(true);
        setIs_mp_question(false);
        assignment_id=(Integer)event.getComponent().getAttributes().get("aid");
        createStandardQuestion();
    }
    
    public void createMP(ActionEvent event){
        setIs_bp_question(false);
        setIs_mp_question(true);
        assignment_id=(Integer)event.getComponent().getAttributes().get("aid");
        createStandardQuestion();
    }
    
    public void createStandardQuestion() {
        QuestionHandler.createStandardQuestion(question, answer_to_question_explanation, is_mp_question, is_bp_question, assignment_id);
    }

    public void createMCQuestion() {
        QuestionHandler.createMCQuestion(question, answer_to_question_explanation, multiple_choice_answer, option_a, option_b, option_c, option_d, option_e, is_mp_question, is_bp_question, assignment_id);
    }
}
