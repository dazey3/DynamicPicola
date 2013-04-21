package edu.osu.picola.businesslogic;

import java.sql.Date;
import java.util.List;

import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.User;
import java.sql.Timestamp;

/**
 * Business logic component for handling assignment related features.
 *
 * Purpose: FE-1
 *
 * @author xu.840 & akers.79
 *
 */
public class AssignmentHandler {

    /**
     * inserts an assignment into assignment table.
     *
     * and assigns to all students in the course
     *
     * @param assignment_descr
     * @param indivdual_start_date
     * @param indivdual_end_date
     * @param BP_start_date
     * @param BP_end_date
     * @param MP_start_date
     * @param MP_end_date
     * @param user_id
     * @param course_id
     * @param assignment_number
     */
    public static void createAssignment(String assignment_descr,
            String indivdual_start_date, String indivdual_end_date,
            String BP_start_date, String BP_end_date, String MP_start_date,
            String MP_end_date, int user_id, int course_id,
            int assignment_number, String assignment_name) {

        /* convert string to timestamp */
        Timestamp is = Timestamp.valueOf(indivdual_start_date);
        Timestamp ie = Timestamp.valueOf(indivdual_end_date);
        Timestamp bs = Timestamp.valueOf(BP_start_date);
        Timestamp be = Timestamp.valueOf(BP_end_date);
        Timestamp ms = Timestamp.valueOf(MP_start_date);
        Timestamp me = Timestamp.valueOf(MP_end_date);

        /* insert assignment */
        Assignment assignment = new Assignment(assignment_descr, is, ie, bs,
                be, ms, me, user_id, course_id, assignment_number,assignment_name);
        
        /* this sets the assignment number in the assignment object 
         * so we can immediately get it back
        */
        AssignmentDAO.insertAssignment(assignment);
        
        assignment = AssignmentDAO.getAssignmentByDateAndIds(user_id,
                course_id,assignment.getAssignment_number());

        /* assign assignment to each student in the course */
        List<User> roster = UserDAO.getCourseRoster(course_id);
        for (int i = 0; i < roster.size(); i++) {
            AssignmentDAO.assignToStudent(roster.get(i).getUser_id(),
                    assignment.getAssignment_id());
        }
    }
}
