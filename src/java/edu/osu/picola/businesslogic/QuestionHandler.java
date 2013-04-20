package edu.osu.picola.businesslogic;

import java.util.List;

import edu.osu.picola.dao.QuestionDAO;
import edu.osu.picola.dataobjects.Question;

/**
 * Business logic component for handling
 * question related business logic
 * 
 * Purpose : FE-1
 * 
 * @author xu.840 & akers.79
 *
 */
public class QuestionHandler {
	/**
	 * Creates standard questions which are non-multiple choice questions
	 * inserts and add question to the set of questions for an assignment
	 * 
	 * @param question
	 * @param answer
	 * @param isMP
	 * @param isBP
	 * @param assignment_id
	 */
	public static void createStandardQuestion(String question, String answer,
			boolean isMP, boolean isBP, int assignment_id) {
		Question sq = new Question(question, answer, isMP, isBP);
		QuestionDAO.insertQuestion(sq);
		int q_id = QuestionDAO.getQuestionByQuestion(question);
		QuestionDAO.insertQuestionToAssignment(assignment_id,q_id);
	}

	/**
	 * Creates a multiple choice question with list versions
	 * like deprecated. 
	 * @param question
	 * @param answer
	 * @param mc_answer
	 * @param mcAnswers
	 */
	public static void createMCQuestion(String question, String answer,
			String mc_answer, List<String> mcAnswers) {
		Question mcq = new Question(question, answer, mc_answer, mcAnswers);
		QuestionDAO.insertQuestion(mcq);
	}

	/**
	 * Creates a multiple choice question with not list format
	 * inserts into db, and adds question to the given assignment question list.
	 * @param question
	 * @param answer
	 * @param mc_answer
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param is_mp_question
	 * @param is_bp_question
	 * @param assignment_id
	 */
	public static void createMCQuestion(String question, String answer, String mc_answer, String a,String  b,String  c,String  d,String  e,boolean is_mp_question, boolean is_bp_question, int assignment_id){
		Question mcq = new Question(question, answer, mc_answer, a, b, c, d, e, is_mp_question, is_bp_question);
		QuestionDAO.insertQuestion(mcq);
		int q_id = QuestionDAO.getQuestionByQuestion(question);
		
		QuestionDAO.insertQuestionToAssignment(assignment_id,q_id);
	}
}
