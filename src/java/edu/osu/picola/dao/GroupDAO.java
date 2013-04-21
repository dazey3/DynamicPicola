package edu.osu.picola.dao;

import edu.osu.picola.businesslogic.GroupHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles SQL statements related to grouping data
 *
 * @author akers.79
 */
public class GroupDAO extends DAO {

    /**
     * Removes a member form a team
     *
     * @param user_id the user to remove
     * @param group_id the group to remove from
     */
    public static void removeUserFromGroup(int user_id, int group_id) {
        String delete = "DELETE FROM test_group " + "WHERE user_id = '"
                + user_id + "' AND group_id = '" + group_id + "')";
        deleteRecord(delete);
    }
    
  
/**
 * Returns the next unused group id
 */
    public static int getNextGroupId() {
        int max = -1;

        String query = "SELECT MAX(group_id) FROM group_ids";

        ResultSet rs = queryDB(query);
        try {
            rs.next();
            max = rs.getInt("max(group_id)");
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("[jakers] "+max);
        return max+1;
    }
    
    public static List<Integer> getGroupMembersIds(int group_id) {
        List<Integer> members= new ArrayList<Integer>();
        try {
            String query = "SELECT u.user_id FROM test_group dg " + "INNER JOIN user u "
                    + "ON dg.user_id=u.user_id " + "WHERE group_id ='" + group_id
                    + "'";
            
            ResultSet rs = queryDB(query);
            
            while (rs.next()) {
                    members.add(rs.getInt("u.user_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return members;
        
    }
    
    public static int getIngroupId(int user_id, int group_id) {
        int ingroup_id = 0;
        String query = "SELECT * FROM test_group WHERE user_id ='"+user_id+"' AND"
                + " group_id='"+group_id+"' LIMIT 1";
        ResultSet rs = queryDB(query);
        try {
            while (rs.next()) {
                ingroup_id = rs.getInt("ingroup_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return ingroup_id;
    }

   /**
     * Insert a user into a group
     *
     * @param user_id the user to insert
     * @param group_id the group to insert into
     * @param course_id the course the group is in
     */
    public static void insertUserIntoGroup(int user_id, int group_id, int course_id, int ingroup_id) {
        String insert = "INSERT INTO test_group (user_id, group_id, course_id, ingroup_id)"
                + " VALUES ('"
                + user_id
                + "', '"
                + group_id
                + "', '"
                + course_id
                + "', '"
                + ingroup_id
                + "')";
        insertDB(insert);
    }
    
    public static void insertGroupId(int group_id, int group_number) {

       String insert = "INSERT INTO group_ids VALUES('" + group_id

                     + "','" +group_number+"')";

       

       insertDB(insert);

   }
    
    

    /**
     * @param course_id the course you want
     * @return a list of groups of students in a given course
     */
    public static List<Group> getAllGroupsAssignedAssignment(int assignment_id) {
        List<Group> groupList = new ArrayList<Group>();
 
        /* gets all the group ids assignment an assignment */
        String query = "SELECT gi.group_id FROM group_ids gi INNER JOIN " +
        "assign_to ato ON ato.group_id=gi.group_id "+
        "WHERE ato.assignment_id='"+assignment_id+"'";
        
        ResultSet rs = queryDB(query);
        List<Integer> gids = new ArrayList<Integer>();
        
        try {
            while (rs.next()) {
                gids.add(rs.getInt("group_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        /* load all members for each of the groups */
        for (Integer id : gids) {
            query = "SELECT * FROM test_group tg INNER JOIN user u ON tg.user_id=u.user_id WHERE group_id='"+id+"'";
            rs = queryDB(query);
            groupList.add(loadGroup(rs));
        }

        return groupList;
    }

    /**
     * @param course_id the course you want
     * @return a list of groups of students in a given course
     */
    public static List<Group> getAllGroupsInCourse(int course_id) {
        List<Group> groupList = new ArrayList<Group>();
        String query = "SELECT * FROM test_group dg INNER JOIN user u "
                + "ON dg.group_id=u.group_id JOIN assign_to a "
                + "ON dg.group_id=a.group_id " + "WHERE a.course_id = '"
                + course_id + "' ORDER BY dg.group_id";
        ResultSet rs = queryDB(query);

        try {
            /*
             * for each group create a group, add to list
             */
            while (rs.next()) {
                groupList.add(loadGroup(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<Group>();
    }


    
    
    public static Group getGroupByUserAndAssignment(int user_id, int assignment_id) {
        Group group = null;
        String query = "SELECT a.group_id FROM assign_to a INNER JOIN test_group dg "
                + "ON a.group_id=dg.group_id WHERE user_id ='" + user_id
                + "' AND assignment_id='" + assignment_id + "'";

        ResultSet rs = queryDB(query);
        try {
            rs.next();
            int group_id = rs.getInt(1);

            group = getGroupMembers(group_id);
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return group;
    }

    /**
     * @param group_id the group you want
     * @return a list of users in the group
     */
    public static Group getGroupMembers(int group_id) {
        String query = "SELECT * FROM test_group dg " + "INNER JOIN user u "
                + "ON dg.user_id=u.user_id " + "WHERE group_id ='" + group_id
                + "'";
        return loadGroup(queryDB(query));
    }

    private static Group loadGroup(ResultSet rs) {
        Group group = new Group();
        try {
//            rs.next();
//            group.setGroup_id(rs.getInt("group_id"));
//            group.add(new User(rs));
            while (rs.next()) {
                /*
                 * only add if the user belongs to a different group
                 */
//                if (group.getGroup_id() == rs.getInt("group_id")) {
                    group.setGroup_id(rs.getInt("group_id"));
                    group.add(new User(rs));
//                } else {
//                    rs.previous();
//                    break;
//                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return group;
    }
    
    public static boolean isUserInGroup(int user_id, int assignment_id) {
        
        Group group = null;
        String query = "SELECT a.group_id FROM assign_to a INNER JOIN test_group dg "
                + "ON a.group_id=dg.group_id WHERE user_id ='" + user_id
                + "' AND assignment_id='" + assignment_id + "'";

        ResultSet rs = queryDB(query);
        try {
            rs.next();
            int group_id = rs.getInt(1);

            group = getGroupMembers(group_id);
        } catch (SQLException ex) {
            System.out.println("GROUPDAO: User not in a group for this assignment!");
            return false;
        }
        return true;
    }
}
