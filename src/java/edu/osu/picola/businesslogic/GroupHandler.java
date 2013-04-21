package edu.osu.picola.businesslogic;

import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dao.MCResponseDAO;
import edu.osu.picola.dao.QuestionDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.MCResponse;
import edu.osu.picola.dataobjects.Question;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jakers
 */
public class GroupHandler {
    private static int groupCount = 4;
    
    
     public static String autoGrouper(int course_id, int assignment_id) {
        
         /* get question_id */
        int question_id = QuestionDAO.getInitQuestion(assignment_id).getQuestion_id();
         
        /* prepare due date check data */
        Assignment a = AssignmentDAO.getAssignment(assignment_id);
        Timestamp due = a.getIndividual_end_date();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        int number_of_groups = GroupDAO.getAllGroupsAssignedAssignment(assignment_id).size();
        if(number_of_groups > 0){
            return "Groups Already Exist for This assignment";
        }
        
        /* if the due data has passed */
        if (now.after(due)) {

            /* form right and wrong list of people who responded */
            List<List<MCResponse>> rightAndWrong =
                    GroupHandler.getRightAndWrongList(course_id, assignment_id,
                    question_id);
            List<MCResponse> right = rightAndWrong.get(0);
            List<MCResponse> wrong = rightAndWrong.get(1);

            /* check for anyone who didn't answer */
            List<Integer> noAnswer = UserDAO.getStudentsThatDidNotAnswer(assignment_id, course_id);
            
            /* add anyone who has not answered to the wrong list */
            for (Integer user_id : noAnswer) {
                MCResponse noAnswerResponse = new MCResponse(user_id, assignment_id, question_id, 'z');
                wrong.add(noAnswerResponse);
            }
    
            /* checks to see the size that a group should be */
            int courseSize= UserDAO.getCourseRoster(course_id).size();
            if (courseSize <= 9) {
                groupCount = 3 ;
            } else if (courseSize <= 16) {
                groupCount = 4;
            } else if (courseSize <= 25) {
                groupCount = 5;
            } else if (courseSize <= 36) {
                groupCount = 6;
            } else if (courseSize <= 49) {
                groupCount = 7;
            } else {
                groupCount = (int)Math.sqrt((double)courseSize);
            }
            
            if(courseSize == 0){
                return "Course Roster Empty";
            }
            /* group everyone */
            GroupHandler.autoGroup(right, wrong, course_id, course_id, assignment_id);
            return "Successfully grouped students";
        }
        else{
            return "Attempting to do grouping before group date";
        }
    }
    
    /**
     * @param course_id the course to be grouped on
     * @param assignment_id the assignment to be grouped on
     * @param question_id the question be grouped on
     * @return List of List of MCResponse sorted into correct and incorrect
     * responses
     */
    private static List<List<MCResponse>> getRightAndWrongList(int course_id,
            int assignment_id,
            int question_id) {

        /*
         * right & wrong data lists
         */
        
        List<List<MCResponse>> rightAndWrong = new ArrayList<List<MCResponse>>();
        List<MCResponse> right = new ArrayList<MCResponse>();
        List<MCResponse> wrong = new ArrayList<MCResponse>();

        /*
         * get correct answer
         */
        Question q = QuestionDAO.getQuestionById(question_id);
        String answer = q.getMultiple_choice_answer();

        /*
         * get responses
         */
        List<MCResponse> response = MCResponseDAO.getClassResponses(
                assignment_id, question_id);

        /*
         * sort responses
         */
        for (MCResponse r : response) {
            if (r.getMcresponse() == answer.charAt(0)) {
                right.add(r);
            } else {
                wrong.add(r);
            }
        }

        /*
         * store sorted results
         */
        rightAndWrong.add(right);
        rightAndWrong.add(wrong);

        return rightAndWrong;
    }

    private static void autoGroup(List<MCResponse> right, List<MCResponse> wrong, int wrongMembers, int course_id, int assignment_id) {

     /* get the first new groupId */   
	int group_id = GroupDAO.getNextGroupId();
        
        System.out.println("Next Group ID to use: " + group_id);
/* keeps track of the next ingroup id to be assigned to each group */
        Map<Integer, Integer> inGroupIds = new HashMap<Integer, Integer>();
     
/* initialize each group */ 
        System.out.println("GroupHandler: Initialize Each Group");
        for (int i = 0; i < groupCount; i++) {
            GroupDAO.insertGroupId(i+group_id,i+1);
            System.out.println("GroupDAO.insertGroupID("+ i+group_id + ", " + i+1 +") ");
            AssignmentDAO.assignToGroup(group_id+i, assignment_id);
            System.out.println("AssignmentDAO.assignToGroup("+ i+group_id + ", " + assignment_id +") ");
            inGroupIds.put(i+group_id, 1);
            System.out.println("IngroupIds.put("+ i+group_id + ", " + 1 + ")");
        }
        System.out.println();
/* distribute the correct students */
        System.out.println("GroupHandler: Distribute Correct Students");
        int tmpGid;
        for (int i = 0; i < right.size();i++) {
            int c_user = right.get(i).getUser_id();
            tmpGid = group_id + (group_id+i) % groupCount;
            int inGroup = inGroupIds.get(tmpGid);
            GroupDAO.insertUserIntoGroup(c_user, tmpGid, course_id, inGroup);
            System.out.println("GroupDAO.insertuserIntoGroup("+ c_user + ", " + tmpGid  + ", " + course_id + ", " + inGroup + ") ");
            inGroupIds.put(tmpGid, inGroupIds.get(tmpGid) + 1);
        }
        System.out.println();
/* distribute the wrong students */
        System.out.println("GroupHandler: Distribute Wrong Students");
        for (int i = 0; i < wrong.size(); i++) {
            int c_user = wrong.get(i).getUser_id();
            tmpGid = group_id + (group_id+i) % groupCount;
            int inGroup = inGroupIds.get(tmpGid);
            GroupDAO.insertUserIntoGroup(c_user, tmpGid, course_id, inGroup);
            System.out.println("GroupDAO.insertuserIntoGroup("+ c_user + ", " + tmpGid  + ", " + course_id + ", " + inGroup + ") ");
            inGroupIds.put(tmpGid, inGroupIds.get(tmpGid) + 1);
        }
        System.out.println();
    }
    
    public static List getGroupMCResponseMapping(int group_id, int assignment_id, int question_id) {
        Map<Integer, String> mapping = new HashMap<Integer, String>();
        List<Integer> ids = GroupDAO.getGroupMembersIds(group_id);
//        System.out.println("IDS = "+ ids);
//        int i = 0;
        for (Integer user_id : ids) {
            int ingroup = GroupDAO.getIngroupId(user_id, group_id);
            System.out.println(user_id + " => "+ ingroup);
//            System.out.println(i+1);
//            i++;
            MCResponse mc = MCResponseDAO.getOneStudentMCResponse(user_id, assignment_id, question_id);
            mapping.put(ingroup, String.valueOf(mc.getMcresponse()));
        }
        return new ArrayList(mapping.entrySet());
    }
}
