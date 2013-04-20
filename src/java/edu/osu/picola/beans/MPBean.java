
package edu.osu.picola.beans;

import edu.osu.picola.dataobjects.Question;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandlink.CommandLink;


public class MPBean implements ActionListener {
    private String Question;
    private String Desription;
    
    private Question currentMPSelected;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getDesription() {
        return Desription;
    }

    public void setDesription(String Desription) {
        this.Desription = Desription;
    }
    
    @Override
    public void processAction(ActionEvent evt){
        Object obj = evt.getSource();
        
        this.currentMPSelected = (Question)((CommandLink)obj).getValue();
    }
    
    public void setMPSelection(Question q){
        
    }
}
