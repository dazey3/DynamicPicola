package edu.osu.picola.beans;

import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.CourseDAO;
import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dao.UserDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Course;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

public class AssignmentMenuBean implements Serializable {
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
        private List<Group> list;

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

        public List<Group> getList() {
            return list;
        }

        public void setList(List<Group> list) {
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
     
    public void updateModel(){
        System.out.println("ASSIGNMENT_MENU_BEAN: Course selection changed: ");
        MenuTab tab;
        menus = new ArrayList<MenuTab>();
        System.out.println(selectedCourse);
        System.out.println(selectedCourse.getCourse_title());
        List<Assignment> assignments = AssignmentDAO.getAssignmentByCourse(selectedCourse.getCourse_id());
        System.out.println("GOT HERE 2!");
        for(int assignment_count = 0; assignment_count < assignments.size(); assignment_count++){
               System.out.println("Assignment: " + assignments.get(assignment_count).getAssignment_id());
               tab = new MenuTab();       
               List<Group> groups = GroupDAO.getAllGroupsAssignedAssignment(assignments.get(assignment_count).getAssignment_id());
               for(Group g : groups){
                   System.out.println("Group: " + g);
               }
               tab.setAssignment(assignments.get(assignment_count));
               tab.setTitle("Assignment "+ assignments.get(assignment_count).getAssignment_id());
               tab.setList(groups);
               menus.add(tab);
       }
       System.out.println("GOT HERE 3!");
    }
    private List<Course> courses;
    
    public List<Course> getCourses(){
        this.courses = new ArrayList<Course>();
        if(LoginBean.user != null){
            System.out.println("COURSE_SELECTION_BEAN: getCourses(" + LoginBean.user.getUser_id() + ")");
            List<Course> list = CourseDAO.getCourseTaughtBy(LoginBean.user.getUser_id());
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
            List<Course> list = CourseDAO.getCourseTaughtBy(LoginBean.user.getUser_id());
            this.courses = new ArrayList<Course>();
            for (Course foo : list) {
                System.out.println("Enrolled course: " + foo.getCourse_title());
                this.courses.add(foo);
            }
            if(this.courses.size() > 0){
                this.selectedCourse = this.courses.get(0);
            }
        }
        else{
            this.courses = new ArrayList<Course>();
        }
        updateModel();
    }
}