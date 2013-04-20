package edu.osu.picola.beans;

import edu.osu.picola.dao.MCResponseDAO;
import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.MCResponse;
import edu.osu.picola.dataobjects.Post;
import edu.osu.picola.dataobjects.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandlink.CommandLink;


public class ViewProfileBean implements ActionListener, Serializable{
    private String FName;
    private String description;
    private String picLink;
    private Date b_day;
    private String gender;
    private Date updateDate;
    private List<Post> postActivity;
    //private List<MCResponse> userResponses;
    

    public String getFName() {
        return FName;
    }

    public String getDescription() {
        return description;
    }

    public String getPicLink() {
        if(picLink == null || picLink.length() == 0 || picLink.equals("none")) this.picLink = "http://www.blirt-magazine.com/login/prof_pics/default.jpg";
        return picLink;
    }

    public Date getB_day() {
        return b_day;
    }

    public String getGender() {
        return gender;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public List<Post> getPostActivity() {
        return postActivity;
    }
    
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        System.out.println("ViewProfileBean: ProcessAction");
        CommandLink itm = (CommandLink)event.getSource();
        User student = (User)itm.getValue();
        
        System.out.println("Students name: " + student.getF_name());
        this.FName = student.getF_name();
        this.description = student.getProfile_decr();
        this.picLink = student.getImg_path();
        this.b_day = student.getBirthday();
        this.gender = student.getGender();
        this.updateDate = student.getLast_update();
        
        this.postActivity = PostDAO.getPostByUserId(student.getUser_id());
        //this.userResponses = MCResponseDAO.getOneStudentMCResponse(user_id, assignment_id, question_id);
        System.out.println("ViewProfileBean: ProcessAction: ImagePath" + this.picLink);
        System.out.println();
    }
}
