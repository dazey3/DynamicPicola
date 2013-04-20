/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.businesslogic;

import edu.osu.picola.dao.CourseDAO;
import edu.osu.picola.dataobjects.Course;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CourseConverter implements Converter {

    // Actions ------------------------------------------------------------------------------------
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Course to the actual Course object.
        Course convertedCourse = CourseDAO.getCourseByTitle(value);
        System.out.println("Converted String (" + value + ") to Course Object ("+ convertedCourse.getCourse_title() +")");
        System.out.println();
        return convertedCourse;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Course object to its unique String representation.
        return ((Course) value).getCourse_title();
    }

}