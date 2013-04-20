package edu.osu.picola.beans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

@ManagedBean(name="testBean")
@SessionScoped
public class TestBean implements Serializable, ActionListener{
    private Date indivdual_start_date;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    

    public Date getIndivdual_start_date() {
        return indivdual_start_date;
    }

    public void setIndivdual_start_date(Date indivdual_start_date) {      
        this.indivdual_start_date = new Date(indivdual_start_date.getTime());
    }
    
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        System.out.println("This is a Test. This is only a test.");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"This is a Test", "Only a test."));  
    }
}
