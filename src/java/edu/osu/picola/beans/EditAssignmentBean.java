package edu.osu.picola.beans;

import edu.osu.picola.dataobjects.Assignment;
import java.io.Serializable;
import java.util.Date;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.event.TabChangeEvent;

public class EditAssignmentBean implements Serializable, ActionListener {
    
    
    public void onTabChange(TabChangeEvent event) {
         System.out.println("EditAssignmentBean: onTabChange");
    }

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        System.out.println("EditAssignmentBean: processAction");
    }
}
