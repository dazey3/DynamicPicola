package edu.osu.picola.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import edu.osu.picola.dataobjects.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class performs SQL statements on User data.
 *
 * @author akers.79
 */
public class UserDAO extends DAO {

    /**
     * @param courseID the course you want
     * @return a list of instructors teaching a course
     */
    public static List<User> getInstructor(int courseID) {
        String query = "SELECT * FROM teaches t " + "INNER JOIN user u ON "
                + "t.user_id=u.user_id " + "WHERE course_id ='" + courseID
                + "'";
        return loadUsers(queryDB(query));
    }
    
    public static void updateUserPassword(String text, int user_id) {
        String key = "picola";
        String update = "UPDATE user SET password=AES_ENCRYPT('"
                + text
                + "','"
                + key + "') WHERE user_id='"+user_id+"'";
        updateDB(update);
    }
    
    public static boolean verifyPassword(String password, int user_id) {
        boolean isCorrect= false;
        String key = "picola";
        String dbPass = "";
        String query ="SELECT CAST(AES_DECRYPT(password,'"
                +key
                +"') AS CHAR) pass FROM user WHERE user_id='"
                +user_id+"'";
                
        ResultSet rs = queryDB(query);
        try {
            rs.next();
           dbPass =  rs.getString("pass");
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (dbPass.equals(password)) {
            isCorrect = true;
        }
        return isCorrect;
    }

    public static User getUserByLoginInfo(String username) {
 if(username != null){
            if(username.indexOf(".") > 0){
                String lastname = username.substring(0, username.indexOf("."));
                String dot_num = username.substring(username.indexOf(".") + 1);
                String query = "SELECT * FROM user WHERE l_name='" + lastname
                     + "' AND dot_number='" + dot_num + "'";
                
                ResultSet rs = queryDB(query);
                if (rs != null) {
                List<User> user = loadUsers(queryDB(query));
                
                if (user.size() > 0) {
                    return user.get(0);
                } else {
                    return null;
                }
                } else {
                    return null;
                }
            }
        }
        return null;
   }

    /**
     * Inserts a user into the enrolled table
     *
     * @param courseID the course to enroll in
     * @param userID the user to enroll
     */
    public static void enrollUser(int courseID, int userID) {
        String insert = "INSERT INTO enrolled (user_id, course_id) "
                + " VALUES ('" + userID + "','" + courseID + "')";
        insertDB(insert);
    }
    
    /**
     * Assigns a user to be an instructor to the a class.
     * @param courseID the course to teach
     * @param userID the user to added to the teacher
     */
    public static void addInstructorToCourse(int courseID, int userID) {
        String insert = "INSERT INTO teaches (user_id, course_id) "
                + " VALUES ('" + userID + "', '" + courseID + "')";
        insertDB(insert);
    } 
    
    /**
     * @param user the user to check for
     * @return true iff user exists in the db
     * @throws SQLException
     */
    public static boolean userExists(User user) throws SQLException {
        String query = "SELECT * FROM user "
                + "WHERE l_name = '" + user.getL_name()
                + "' AND dot_number = '" + user.getDot_number() + "'";
        return queryDB(query).next();
    }

    public static User getUserByNameDotNumber(User user) {
        User u = null;
        String query = "SELECT * FROM user "
                + "WHERE l_name = '" + user.getL_name() 
                + "' AND dot_number = '" + user.getDot_number() + "'";
        ResultSet rs = queryDB(query);
        try {
            rs.next();
            u = new User(queryDB(query));
        } catch (SQLException e) {
            System.out.println("Query: " + query);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Updates the user for record based of passed in user.
     *
     * @param user the new state of the user
     */
    public static void updateUser(User user) {

        String preparedUpdate = "UPDATE user SET f_name=?, l_name=?, dot_number=?, "
                + "img_path=?, status=?, profile_decr=?, "
                + "last_login_date=?, last_update=?, "
                + "birthday_date=?, gender=?,role_id=? WHERE user_id=?";
        try {
            PreparedStatement ps = MySQLDBConnection.getConnection().prepareStatement(preparedUpdate);
            ps.setString(1, user.getF_name());
            ps.setString(2, user.getL_name());
            ps.setString(3, user.getDot_number());
            ps.setString(4, user.getImg_path());
            ps.setString(5, user.getStatus());
            ps.setString(6, user.getProfile_decr());
            ps.setTimestamp(7, user.getLast_login_date());
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setDate(9, user.getBirthday());
            ps.setString(10, user.getGender());
            ps.setInt(11, user.getRole());
            ps.setInt(12, user.getUser_id());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String encryptPassword(String password) {
        String key = "picola";
        String epass = "";
        String query ="SELECT CAST(AES_ENCRYPT('"+password+"', '"+key + "') AS CHAR) pass";
        ResultSet rs = queryDB(query);
        try {
            rs.next();
             epass = rs.getString("pass");
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return epass;
    }
    
    
    
    /**
     * Inserts a new user in the db.
     *
     * @param user the user object to insert.
     */
    public static void insertUser(User user) {
        
        String defaultPassword = encryptPassword(user.getPassword());
        
        String insert = "INSERT INTO user (f_name, l_name,dot_number,img_path,status, "
                + "profile_decr,last_login_date, last_update, birthday_date, gender, role_id, password) "
                + "VALUES ('" + user.getF_name() + "','"
                + user.getL_name() + "', '"
                + user.getDot_number() + "', '"
                + user.getImg_path() + "', '"
                + user.getStatus() + "', '"
                + user.getProfile_decr() + "', "
                + user.getLast_login_date() + ", "
                + user.getLast_update() + ", "
                + user.getBirthday() + ", '"
                + user.getGender() + "', '"
                + user.getRole() + "', '"
                + defaultPassword
                + "')";
        insertDB(insert);
    }

    /**
     * Removes a user from the user table.
     *
     * TODO add cascading delete feature
     *
     * @param user_id the user to delete
     */
    public static void deleteUser(int user_id) {
        String delete = "DELETE FROM user "
                + "WHERE user_id = '"
                + user_id + "'";
        deleteRecord(delete);
    }

    /**
     * @param courseID the course you want
     * @return a list of read-only users for course
     */
    public static List<User> getReadOnly(int courseID) {
        String query = "SELECT * FROM read_only r " + "INNER JOIN user u ON "
                + "r.user_id=u.user_id " + "WHERE course_id ='" + courseID
                + "'";
        return loadUsers(queryDB(query));
    }

    /**
     * @param courseID the course you want
     * @return a list of student users in a course
     */
    public static List<User> getCourseRoster(int courseID) {
        String query = "SELECT * FROM enrolled e " + "INNER JOIN user u ON "
                + "e.user_id=u.user_id " + "WHERE course_id ='" + courseID
                + "'";
        return loadUsers(queryDB(query));
    }

    /**
     * @param role the role you want
     * @return return a list of user with a given role
     */
    public static List<User> getUserRole(int role) {

        String query = "SELECT * FROM user WHERE role_id = '" + role + "'";
        return loadUsers(queryDB(query));
    }

    /**
     * @param user_id the user id you want
     * @return a user with the matching id
     */
    public static User getUserById(int user_id) {
        String query = "SELECT * FROM user WHERE user_id = '"
                + user_id + "'";
        return loadUsers(queryDB(query)).get(0);
    }

    public static List<Integer> getStudentsThatDidNotAnswer(int assignment_id, int course_id){ 
        
        String query = "SELECT * FROM assignment_viewed av INNER JOIN enrolled e "
                + "ON av.user_id=e.user_id "
                + "LEFT JOIN question_response qr ON e.user_id=qr.user_id "
                +"WHERE e.course_id='"+course_id+"' AND qr.multiple_Choice_response IS NULL AND "
                + "av.assignment_id='" +assignment_id+"'";
        
        List<Integer> badStudents = new ArrayList<Integer>();
        
        ResultSet rs = queryDB(query);
        try {
            while (rs.next()) {
                badStudents.add(rs.getInt("user_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return badStudents;
    }

    
    
    
    /**
     * Helper for loading users in to lists
     */
    private static List<User> loadUsers(ResultSet rs) {
        List<User> users = new ArrayList<User>();

        try {
            while (rs.next()) {
                users.add(new User(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
}
