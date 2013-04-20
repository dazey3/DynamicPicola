package edu.osu.picola.dao;

import static edu.osu.picola.dao.DAO.queryDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.osu.picola.dataobjects.Course;

/**
 * This class performs SQL statements related to Course data.
 * 
 * @author akers.79
 */
public class CourseDAO extends DAO {

	/**
	 * inserts a course into the db
	 * 
	 * @param course
	 *            the course to be added
	 */
	public static void insertCourse(Course course) {
		String insert = "INSERT INTO course (course_id, course_title, course_descr, semester_id)"
				+ " VALUES('"
				+ course.getCourse_id()
				+ "', '"
				+ course.getCourse_title()
				+ "', '"
				+ course.getCourse_descr()
				+ "', '" + course.getSemester_id() + "')";
		insertDB(insert);
	}

	public static Course getCourseById(int course_id) {
		String query = "SELECT * FROM course WHERE course_id = '" + course_id
				+ "'";
		return loadCourse(queryDB(query)).get(0);
	}

	public static void deleteCourse(int course_id) {
		// TODO delete student roster
		// TODO delete teaches
		// TODO delete assignment
		// TODO MORE STUFF

		String delete = "DELETE FROM course WHERE course_id='" + course_id
				+ "'";
		deleteRecord(delete);
	}

	public static Course getCourseByTitle(String courseTitle) {
		String query = "SELECT * FROM course WHERE course_title = '"
				+ courseTitle + "'";

		return loadCourse(queryDB(query)).get(0);
	}

	/**
	 * @param user_id
	 *            the user you want
	 * @return list of all course a student has taken
	 */
	public static List<Course> getStudentCourse(int user_id) {
		String query = "SELECT * FROM enrolled e INNER JOIN course c on"
                        + " e.course_id =c.course_id WHERE user_id = '" + user_id
				+ "'";
		return loadCourse(queryDB(query));
	}

	/**
	 * @param user_id
	 *            the user you want
	 * @param semester_id
	 *            the semester you want
	 * @return a list of course the user is enrolled in a given semester
	 */
	public static List<Course> getStudentCourse(int user_id, int semester_id) {
		String query = "SELECT * FROM enrolled, WHERE user_id = '" + user_id
				+ "' AND semester_id = '" + semester_id + "'";
		return loadCourse(queryDB(query));
	}

	/**
	 * @param user_id
	 *            the user you want
	 * @return a list of course taught by a user
	 */
	public static List<Course> getCourseTaughtBy(int user_id) {
		String query = "SELECT * FROM teaches t INNER JOIN course c on t.course_id =c.course_id " 
                        + "WHERE user_id = '" + user_id
				+ "'";
		return loadCourse(queryDB(query));
	}

	/**
	 * Helper that loads ResultSet into a list of Courses.
	 * 
	 * @param rs
	 *            a ResultSet from a query
	 * @return a list of courses
	 */
	private static List<Course> loadCourse(ResultSet rs) {
		List<Course> courses = new ArrayList<Course>();
		try {
			while (rs.next()) {
				courses.add(new Course(rs));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return courses;
	}
}
