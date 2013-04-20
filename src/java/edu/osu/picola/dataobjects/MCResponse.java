package edu.osu.picola.dataobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MCResponse {
	private int user_id;
	private int assignment_id;
	private int question_id;
	private char mcresponse;
	public MCResponse(int user_id, int assignment_id, int question_id, char mcresponse)
	{
		this.user_id=user_id;
		this.assignment_id=assignment_id;
		this.question_id=question_id;
		this.mcresponse=Character.toLowerCase(mcresponse);
	}
	public MCResponse(ResultSet rs){
		try{
			user_id=rs.getInt("user_id");
			assignment_id=rs.getInt("assignment_id");
			question_id=rs.getInt("question_id");
			mcresponse=rs.getString("multiple_choice_response").charAt(0);
			
		}catch (SQLException e) {
			System.out.println("Failed to read in MCResponse!");
			e.printStackTrace();
		}
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getAssignment_id() {
		return assignment_id;
	}
	public void setAssignment_id(int assignment_id) {
		this.assignment_id = assignment_id;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public char getMcresponse() {
		return mcresponse;
	}
	public void setMcresponse(char mcresponse) {
		this.mcresponse = mcresponse;
	}
	
}
