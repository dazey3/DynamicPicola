package edu.osu.picola.studentbeans;

import edu.osu.picola.beans.LoginBean;
import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.CourseDAO;
import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dao.QuestionDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Course;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.Question;
import edu.osu.picola.dataobjects.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

public class StudentAssignmentMenuBean implements Serializable {
    public static Course selectedCourse;

    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
   
    public class MenuTab implements Serializable {

        private Assignment assignment;
        //display
        private String title;
        private List<Question> list;

        public Assignment getAssignment() {
            return assignment;
        }

        public void setAssignment(Assignment assignment) {
            this.assignment = assignment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Question> getList() {
            return list;
        }

        public void setList(List<Question> list) {
            this.list = list;
        }
       
    }

    private List<MenuTab> menus = new ArrayList<MenuTab>();

    public void setMenus(List<MenuTab> menus){
        this.menus = menus;
    }
    
    public List<MenuTab> getMenus() {
        return menus;
    }
    
    public boolean showSelection(){
        return(selectedCourse != null);
    }
    
    public List<User> getUsers(){
        if(this.selectedCourse == null){return new ArrayList<User>();}
        System.out.println("COURSE_SELECTION_BEAN:CURRENTLY SELECTED COURSE: " + this.selectedCourse.getCourse_id());
        List<User> users = UserDAO.getCourseRoster(this.selectedCourse.getCourse_id());
        if(users == null){ return (new ArrayList<User>());}
        return users;
    }
     
   // public boolean isUserInGroupForAssignment() {
        /* If there isn't a group selected, the thing won't try to render it in a 'rendered' attribute. */
        //return this.groupselected != null;
        
    //}
    
    public void updateModel(){
        System.out.println("STUDENT_ASSIGNMENT_MENU_BEAN: Course selection changed: ");
        MenuTab tab;
        menus = new ArrayList<MenuTab>();
        System.out.println(selectedCourse);
        System.out.println(selectedCourse.getCourse_title());
        List<Assignment> assignments = AssignmentDAO.getAssignmentByCourse(selectedCourse.getCourse_id());
        System.out.println("GOT HERE 2!");
        
        for(int assignment_count = 0; assignment_count < assignments.size(); assignment_count++){
            
               System.out.println("Assignment: " + assignments.get(assignment_count).getAssignment_id());
               System.out.println("User: " + LoginBean.user.getUser_id());
               
        
               tab = new MenuTab();       
               List<Question> questions = QuestionDAO.getQuestionByAssignment(assignments.get(assignment_count).getAssignment_id());
               for(Question q : questions){
                   System.out.println("Question: " + q);
               }
               tab.setAssignment(assignments.get(assignment_count));
               tab.setTitle("Assignment "+ assignments.get(assignment_count).getAssignment_id());
               tab.setList(questions);
               menus.add(tab);
       }
       System.out.println("GOT HERE 3!");
    }
    private List<Course> courses;
    
    public List<Course> getCourses(){
        this.courses = new ArrayList<Course>();
        if(LoginBean.user != null){
            System.out.println("COURSE_SELECTION_BEAN: getCourses(" + LoginBean.user.getUser_id() + ")");
            List<Course> list = CourseDAO.getStudentCourse(LoginBean.user.getUser_id());
            for (Course foo : list) {
                this.courses.add(foo);
            }
        }
        return this.courses;
    }   
    
    @PostConstruct
    public void initModel() {
        System.out.println("COURSE_SELECTION_BEAN:initModel");
        if(LoginBean.user != null){
            System.out.println("COURSE_SELECTION_BEAN: getCourses(" + LoginBean.user.getUser_id() + ")");
            List<Course> list = CourseDAO.getStudentCourse(LoginBean.user.getUser_id());
            this.courses = new ArrayList<Course>();
            for (Course foo : list) {
                System.out.println("Enrolled course: " + foo.getCourse_title());
                this.courses.add(foo);
            }
            if(this.courses.size() > 0){
                System.out.println("Setting default Selected Course: " + this.courses.get(0).getCourse_title());
                this.selectedCourse = this.courses.get(0);
            }
        }
        else{
            this.courses = new ArrayList<Course>();
        }
        updateModel();
    }
}