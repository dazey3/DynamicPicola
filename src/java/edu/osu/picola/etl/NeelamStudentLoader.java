package edu.osu.picola.etl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;


import edu.osu.picola.dao.CourseDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Course;
import edu.osu.picola.dataobjects.User;

public class NeelamStudentLoader extends ArrayList<User> {

	private static final long serialVersionUID = -2400116405088692380L;
	private String filePath;
	private Course course;
	private int semester_code;

	public NeelamStudentLoader(String filePath, int semester_code) {
		super();
		this.filePath = filePath;
		this.semester_code = semester_code;
		loadUsers();
	}

	private void loadUsers() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			loadCourse(br, semester_code);
			loadStudents(br);
		} catch (FileNotFoundException e) {
			System.out.println("No file found: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO issue: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public void insertCourse() {
		CourseDAO.insertCourse(course);
		course = CourseDAO.getCourseByTitle(course.getCourse_title());
//		System.out.println("course id = "+course.getCourse_id());
	}

	/**
	 * populates the enrolled table
	 */
	public void populateRoster() throws SQLException {
		int new_student = 0;
		int enrolled_student = 0;
		for (int i = 0; i < size();i++) {
			/* insert if user isn't in the db */
			if (!UserDAO.userExists(get(i))) {
				UserDAO.insertUser(get(i));
				new_student++;
			}
			
			/* enroll student */
			User temp = UserDAO.getUserByNameDotNumber(get(i));
			UserDAO.enrollUser(course.getCourse_id(), temp.getUser_id());
			enrolled_student++;
		}
		System.out.println("added "+new_student+" new students.");
		System.out.println("enrolled "+enrolled_student+" students.");
	}
	
	public Course getCourse() {
		return course;
	}
	
	private void loadCourse(BufferedReader br, int semester_code)
			throws IOException {
		String line = br.readLine();
		StringTokenizer token = new StringTokenizer(line);
		/* skip header */
		for (int i = 0; i < 3; i++) {
			token.nextToken();
		}

		/* read course name */
		String course_name = token.nextToken() + " " + token.nextToken() + " "
				+ token.nextToken();

		/* read course description */
		String course_descr = token.nextToken() + " " + token.nextToken() + " "
				+ token.nextToken();

		course = new Course(course_name, course_descr, semester_code);

		/* skip empty line */
		br.readLine();
		/* skip column header */
		br.readLine();
	}

	private void loadStudents(BufferedReader br) throws IOException {
		String line = br.readLine();
		while (line != null) {
			StringTokenizer token = new StringTokenizer(line);
			
			/* sends things to lower case */
			String f_name = token.nextToken().toLowerCase();
			String l_name = token.nextToken().toLowerCase();
			
			/* handles middle names currently not elegant */
			if (token.countTokens()+ 2 == 6) {
				token.nextToken();
				token.nextToken();
			} else if (token.countTokens()+2 == 5){
				token.nextToken();
			} else {
				token.nextToken();
				token.nextToken();
				token.nextToken();
				token.nextToken();
			}
			
			String dotNumber = token.nextToken();

			dotNumber = dotNumber.substring(dotNumber.indexOf(".") + 1);
			dotNumber = dotNumber.substring(0, dotNumber.indexOf("@"));
			
			// TODO note that gender isn't specified and will default to male
			User student = new User(f_name, l_name, dotNumber, "male", User.STUDENT, "X");
			add(student);

			line = br.readLine();
		}
	}
}
