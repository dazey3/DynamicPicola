package edu.osu.picola.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.osu.picola.dataobjects.MCResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MCResponseDAO extends DAO {

	public static void insertMCResponse(MCResponse mcr) {
		String insert = "INSERT INTO Question_response VALUES ('"
				+ mcr.getUser_id() + "', '" + mcr.getAssignment_id() + "', '"
				+ mcr.getQuestion_id() + "', '" + mcr.getMcresponse() + "')";
		System.out.println("this is test of MCR:  " + insert);
		insertDB(insert);

	}

	public static void updateMCResponse(MCResponse mcr) {
		String update = "UPDATE Question_response SET multiple_choice_response ='"
				+ mcr.getMcresponse()
				+ "' where user_id='"
				+ mcr.getUser_id()
				+ "' and assignment_id= '"
				+ mcr.getAssignment_id()
				+ "' and question_id= '" + mcr.getQuestion_id() + "'";
		System.out.println("this is test of MCR:  " + update);
		updateDB(update);
	}

	public static void deleteMCResponse(int user_id, int assignment_id,
			int question_id) {

		String delete = "DELETE from Question_response where  user_id='"
				+ user_id + "' and assignment_id= '" + assignment_id
				+ "' and question_id= '" + question_id + "'";
		System.out.println("this is test of MCR:  " + delete);
		deleteRecord(delete);

	}

        public static boolean hasResponded(int assignment_id,int question_id, int user_id) {
            boolean hasResponded = false;
            String query = "SELECT * FROM question_response WHERE assignment_id='"+assignment_id+
                    "' AND question_id='"+question_id+"' AND user_id='"+user_id+"'";
        try {
            hasResponded = queryDB(query).next();
        } catch (SQLException ex) {
            Logger.getLogger(MCResponseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return hasResponded;
        
        }
        
        
        public static List<MCResponse> getClassResponses(int assignment_id, int question_id) {
            String query = "SELECT * FROM Question_response WHERE assignment_id='"
                    +assignment_id+ "' AND question_id='"+question_id+"'";
            return loadMCReponses(queryDB(query));
        }
        
        
	public static MCResponse getOneStudentMCResponse(int user_id,
			int assignment_id, int question_id) {
		String query = "SELECT * from Question_response where  user_id='"
				+ user_id + "' and assignment_id= '" + assignment_id
				+ "' and question_id= '" + question_id + "'";
		return loadMCReponses((queryDB(query))).get(0);
	}

	public static List<MCResponse> loadMCReponses(ResultSet rs) {
		List<MCResponse> users = new ArrayList<MCResponse>();
		try {
			while (rs.next()) {
				users.add(new MCResponse(rs));
			}
		} catch (SQLException e) {
			System.out.println("Error message: " + e.getMessage());
			e.printStackTrace();
		}
		return users;
	}

}
