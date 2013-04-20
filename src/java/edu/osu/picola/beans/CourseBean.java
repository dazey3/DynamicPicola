package edu.osu.picola.beans;

import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.CourseDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Course;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author akers.79
 */
@ManagedBean(name = "courseBean")
@SessionScoped
public class CourseBean {
    // TODO list course

    private Course course;
    private int courseId;

    public void edit(ActionEvent event) {
        courseId = (Integer) event.getComponent().getAttributes().get("cid");
        setCourseId(courseId);
    }

    public int getcourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        course = CourseDAO.getCourseById(courseId);
    }

    public Course getCourse() {
        return course;
    }

    public List<Assignment> getAssignments() {
        return AssignmentDAO.getAssignmentByCourse(courseId);
    }
}
