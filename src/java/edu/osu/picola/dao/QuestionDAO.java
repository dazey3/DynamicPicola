package edu.osu.picola.dao;

import static edu.osu.picola.dao.DAO.queryDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.osu.picola.dataobjects.Question;

/**
 * This class performs SQL statements
 * related to question data.
 * @author akers.79
 */
public class QuestionDAO extends DAO {

	/**
	 * @param question_id the question you want
	 * @return a question by its question_id
	 */
	public static Question getQuestionById(int question_id) {
		String query = "SELECT * FROM question "
				     + "WHERE question_id='"
				     + question_id+"'";
		ResultSet rs = queryDB(query);
		return loadQuestions(rs).get(0);
	}
	
	/**
	 * @param assignment_id the assignment you want the
	 * questions for
	 * @return a list of questions for an assignment
	 */
	public static List<Question> getQuestionByAssignment(int assignment_id) {
                //OLD
		//String query = "SELECT * FROM assignment_questions "
		//		+ "WHERE assignment_id = '" + assignment_id + "'";
            String query = "SELECT * FROM assignment_questions aq INNER JOIN "
					 + "question q ON aq.question_id=q.question_id "
					 + "WHERE  assignment_id = '" + assignment_id + "'";
		ResultSet rs = queryDB(query);
		return loadQuestions(rs);
	}
        public static Question getInitQuestion(int assignment_id){
            String query = "select * from assignment_questions aq "
                    + "inner join question q on "
                    + "aq.question_id=q.question_id "
                    + "where q.is_multiple_choice and aq.assignment_id='"
                    + assignment_id+"'";
            ResultSet rs = queryDB(query);
            try{
                 if(rs.next()){
                    rs.beforeFirst();
                    return loadQuestions(rs).get(0);
                }
            }
            catch(SQLException ex){
                System.err.println("Failed to getInitQuestion");
            }
            
            return null;
        }
        
        public static void removeQuestionById(int question_id) {
        
        String delete = "DELETE FROM question WHERE question_id='"+question_id+"'";
        deleteRecord(delete);
        }
	/**
	 * @return all multiple choice questions in the db
	 */
	public static List<Question> getAllMultipleChoiceQuestions() {
		String query = "SELECT * FROM question "
				     + "WHERE is_multiple_choice='1'";
		ResultSet rs = queryDB(query);
		return loadQuestions(rs);
	}

	/**
	 * @param assignment_id the assignment you want
	 * @return list of all BP question for an assignment
	 */
	public static List<Question> getBPQuestionByAssignment(int assignment_id) {
		String query = "SELECT * FROM assignment_questions aq INNER JOIN "
					 + "question q ON aq.question_id=q.question_id "
					 + "WHERE aq.assignment_id='" + assignment_id
					 + "' AND q.is_bp_question='1'";
		ResultSet rs = queryDB(query);
		return loadQuestions(rs);
	}

	/**
	 * @param assignment_id the assignment you want
	 * @return a list of MP questions for an assignment
	 */
	public static List<Question> getMPQuestionByAssignment(int assignment_id) {
		String query = "SELECT * FROM assignment_questions aq INNER JOIN "
					 + "question q ON aq.question_id=q.question_id "
					 + "WHERE aq.assignment_id='" + assignment_id
					 + "' AND q.is_mp_question='1'";
		ResultSet rs = queryDB(query);
		return loadQuestions(rs);
	}
	
	/**
	 * Helper to reduce code rewrite
	 * @param rs questions to load
	 * @return list of questions
	 */
	private static List<Question> loadQuestions(ResultSet rs) {
		List<Question> questions = new ArrayList<Question>();
		try {
			while (rs.next()) {
				questions.add(new Question(rs));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return questions;
	}
	
	public static void insertQuestionToAssignment(int assignment_id, int question_id) {
		String insert = "INSERT INTO assignment_questions (assignment_id, question_id) " +
				"VALUES ('"+assignment_id+"','"+question_id+"')";
		System.out.println("[jakers] query ="+ insert);
		insertDB(insert);
	}
	
	public static void insertQuestion(Question q){
		String preparedInsert = "INSERT INTO question (question,answer_to_question_explanation, "
				+ "is_multiple_choice, multiple_choice_answer,option_a,option_b, "
		        + "option_c,option_d,option_e,is_mp_question,is_bp_question)"
		+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = MySQLDBConnection.getConnection().prepareStatement(preparedInsert);
			ps.setString(1, q.getQuestion());
			ps.setString(2, q.getAnswer_to_question_explanation());
			ps.setBoolean(3, q.isIs_multiple_choice());
			ps.setString(4, q.getMultiple_choice_answer());
			ps.setString(5, q.getOption_a());
			ps.setString(6, q.getOption_b());
			ps.setString(7, q.getOption_c());
			ps.setString(8, q.getOption_d());
			ps.setString(9, q.getOption_e());
			ps.setBoolean(10, q.isIs_mp_question());
			ps.setBoolean(11, q.isIs_bp_question());
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
        
        public static void updateQuestion(Question q, int question_id) {
            
            String update = "UPDATE question SET question=?,answer_to_question_explanation=?, "
				+ "is_multiple_choice=?, multiple_choice_answer=?,option_a=?,option_b=?, "
		        + "option_c=?,option_d=?,option_e=?,is_mp_question=?,is_bp_question=? WHERE question_id =?";
            
            try {
			PreparedStatement ps = MySQLDBConnection.getConnection().prepareStatement(update);
			ps.setString(1, q.getQuestion());
			ps.setString(2, q.getAnswer_to_question_explanation());
			ps.setBoolean(3, q.isIs_multiple_choice());
			ps.setString(4, q.getMultiple_choice_answer());
			ps.setString(5, q.getOption_a());
			ps.setString(6, q.getOption_b());
			ps.setString(7, q.getOption_c());
			ps.setString(8, q.getOption_d());
			ps.setString(9, q.getOption_e());
			ps.setBoolean(10, q.isIs_mp_question());
			ps.setBoolean(11, q.isIs_bp_question());
                        ps.setInt(12, question_id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
            
        }
        
	
	public static int getQuestionByQuestion(String question) {
		String query = "SELECT MAX(question_id) FROM question "
				     + "WHERE question = '"
				     + question + "'";
		int question_id = -1;
		ResultSet rs = queryDB(query);
		try {
			rs.next();
			question_id = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return question_id;
	}
}
