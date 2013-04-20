package edu.osu.picola.businesslogic;

import edu.osu.picola.dao.MCResponseDAO;
import edu.osu.picola.dataobjects.MCResponse;

public class MultipleChoiceHandler {

    public static void createMCResponse(int user_id, int assignment_id,
            int question_id, char MCResponse) {
        MCResponse mcr = new MCResponse(user_id, assignment_id, question_id,
                MCResponse);
        MCResponseDAO.insertMCResponse(mcr);
    }

    public static void editMCResponse(int user_id, int assignment_id,
            int question_id, char MCResponse) {
        MCResponse mcr = new MCResponse(user_id, assignment_id, question_id,
                MCResponse);
        MCResponseDAO.updateMCResponse(mcr);
    }

    public static void deleteMCResponse(int user_id, int assignment_id,
            int question_id) {
        MCResponseDAO.deleteMCResponse(user_id, assignment_id, question_id);
    }

    public static char getStudentResponse(int user_id, int assignment_id,
            int question_id) {
        return MCResponseDAO.getOneStudentMCResponse(user_id, assignment_id,
                question_id).getMcresponse();
    }

    public static MCResponse getStudentWholeResponse(int user_id,
            int assignment_id, int question_id) {
        return MCResponseDAO.getOneStudentMCResponse(user_id, assignment_id,
                question_id);
    }
}
