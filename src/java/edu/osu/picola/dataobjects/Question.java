package edu.osu.picola.dataobjects;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class models the a question in the pi-cola system.
 * 
 * @author akers.79
 */
public class Question implements Serializable{
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

	public Question(ResultSet rs) {
		try {
			question_id = rs.getInt("question_id");
			question = rs.getString("question");
			answer_to_question_explanation = rs
					.getString("answer_to_question_explanation");
			is_multiple_choice = rs.getBoolean("is_multiple_choice");
			multiple_choice_answer = rs.getString("multiple_choice_answer");
			option_a = rs.getString("option_a");
			option_b = rs.getString("option_b");
			option_c = rs.getString("option_c");
			option_d = rs.getString("option_d");
			option_e = rs.getString("option_e");
			is_mp_question = rs.getBoolean("is_mp_question");
			is_bp_question = rs.getBoolean("is_bp_question");
		} catch (SQLException e) {
			System.out.println("Failed to load Question!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Constructs non-multiple choice questions
	 * 
	 * @param question
	 *            the question text
	 * @param answer
	 *            the text answer
	 * @param isMP
	 *            whether it is a MP question
	 * @param isBP
	 *            whether it is a BP question
	 */
	public Question(String question, String answer, boolean isMP, boolean isBP) {
		is_multiple_choice = false;
		option_a = null;
		option_b = null;
		option_c = null;
		option_d = null;
		option_e = null;
		multiple_choice_answer = null;
		is_mp_question = isMP;
		is_bp_question = isBP;
		this.question = question;
		answer_to_question_explanation = answer;
	}

	/**
	 * Constructs a multiple choice question
	 * 
	 * @param question
	 *            the answer to the question
	 * @param answer
	 *            the text answer to the question
	 * @param mcAnswers
	 *            List of multiple choice answer constructor pads lists of
	 *            length < 5
	 */
	public Question(String question, String answer, String mc_answer,
			List<String> mcAnswers) {
		is_multiple_choice = true;

		/* pads the list if less than total answers */
		if (mcAnswers.size() < 5) {
			mcAnswers.add(null);
		}
		option_a = mcAnswers.get(0);
		option_b = mcAnswers.get(1);
		option_c = mcAnswers.get(2);
		option_d = mcAnswers.get(3);
		option_e = mcAnswers.get(4);
		is_mp_question = false;
		is_bp_question = false;
		this.question = question;
		multiple_choice_answer = mc_answer;
		answer_to_question_explanation = answer;
	}

	public Question(String question, String answer, String mc_answer, String a,
			String b, String c, String d, String e, boolean is_mp_question,
			boolean is_bp_question) {
		is_multiple_choice = true;

		option_a = a;
		option_b = b;
		option_c = c;
		option_d = d;
		option_e = e;
		this.is_mp_question = is_mp_question;
		this.is_bp_question = is_bp_question;
		this.question = question;
		multiple_choice_answer = mc_answer;
		answer_to_question_explanation = answer;
	}

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

	public void setAnswer_to_question_explanation(
			String answer_to_question_explanation) {
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

        @Override
        public String toString() {
           if(this.is_bp_question){
               return ("Background Portion");
           }
           else if(this.is_mp_question){
               return ("Main Portion");
           }
           return ("Individual Portion");
        }
        
}
