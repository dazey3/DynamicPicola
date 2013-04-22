
package edu.osu.picola.businesslogic;

import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.MCResponseDAO;
import edu.osu.picola.dao.QuestionDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.MCResponse;
import java.util.ArrayList;
import java.util.List;

public class MCStatisticHandler {
    public static List<Integer> getMCStatsForAssignment(int assignment_id) {
        List<Integer> mcStats = new ArrayList<Integer>();
        int question_id = QuestionDAO.getInitQuestion(assignment_id).getQuestion_id();
        List<MCResponse> responses = MCResponseDAO.getClassResponses(assignment_id, question_id);
        Assignment a = AssignmentDAO.getAssignment(assignment_id);
       
        System.out.println(a.getCourse_id());
        int noResponseCount = UserDAO.getCourseRoster(a.getCourse_id()).size()- responses.size();
        
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        int dCount = 0;
        int eCount = 0;
        
        for (MCResponse mc : responses) {
            switch(mc.getMcresponse()) {
                case 'a':
                    aCount++;
                    break;
                case 'b':
                    bCount++;
                    break;
                case 'c':
                    cCount++;
                    break;
                case 'd':
                    dCount++;
                    break;
                case 'e':
                    eCount++;
                    break;
            }
        }
        
        mcStats.add(aCount);
        mcStats.add(bCount);
        mcStats.add(cCount);
        mcStats.add(dCount);
        mcStats.add(eCount);
        mcStats.add(noResponseCount);
        
        return mcStats;
    }
    
}
