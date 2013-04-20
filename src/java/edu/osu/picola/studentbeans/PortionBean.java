/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.studentbeans;

import edu.osu.picola.beans.LoginBean;
import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dao.MCResponseDAO;
import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.MCResponse;
import edu.osu.picola.dataobjects.Post;
import edu.osu.picola.dataobjects.Question;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.commandlink.CommandLink;


/**
 *
 * @author Karl
 */


public class PortionBean implements Serializable, ActionListener {
    private Question currentQuestion;
    private String question;
    private String selectedAnswer;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String option_e;
    private Group currentGroup;
    private List<Post> currentGroupDiscussion;
    private Post selectedPost;
    private String studentPost;
    private boolean summaryBit;

    public boolean isSummaryBit() {
        return summaryBit;
    }

    public void setSummaryBit(boolean summaryBit) {
        this.summaryBit = summaryBit;
    }
    
    public String getStudentPost() {
        return studentPost;
    }

    public void setStudentPost(String studentPost) {
        this.studentPost = studentPost;
    }
    
    public PortionBean() {
        currentGroupDiscussion = new ArrayList<Post>(); 
    }

    public Post getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Post selectedPost) {
        this.selectedPost = selectedPost;
    }

    public List<Post> getCurrentGroupDiscussion() {
        return currentGroupDiscussion;
    }

    public void setCurrentGroupDiscussion(List<Post> currentGroupDiscussion) {
        this.currentGroupDiscussion = currentGroupDiscussion;
    }
    
    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }
    
    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getOption_e() {
        return option_e;
    }

    public void setOption_e(String option_e) {
        this.option_e = option_e;
    }
    
    public void tabChangeListener(AjaxBehaviorEvent evt){
        clearSelection();
   }
    
    private void clearSelection(){
        System.out.println("CLEARING GROUP SELECTION!");
        this.currentQuestion = null;
        this.selectedAnswer = null;
        this.question = null;
        this.option_a = null;
        this.option_b = null;
        this.option_c = null;
        this.option_d = null;
        this.option_e = null;
        this.currentGroup = null;
        this.currentGroupDiscussion = null;
        this.studentPost = "";
    }
    
    @Override
    public void processAction(ActionEvent ae) throws AbortProcessingException 
    {
        
                clearSelection();
                CommandLink itm = (CommandLink)ae.getSource();
                String menu_name = itm.getValue().toString();
                this.currentQuestion = (Question)itm.getValue();
                this.option_a = currentQuestion.getOption_a();
                this.option_b = currentQuestion.getOption_b();
                this.option_c = currentQuestion.getOption_c();
                this.option_d = currentQuestion.getOption_d();
                this.option_e = currentQuestion.getOption_e();
                this.question = currentQuestion.getQuestion();
                System.out.println(">>> Current Question: " + this.option_a);
                System.out.println("GROUPSELECTEDBEAN: processAction occurred!!!!");
                
        
    }
    
    public boolean studentSubmitPost(){
        
        System.out.println("Portion Bean: Submitting Post");
        if(this.studentPost.length() > 0){
            System.out.println("User: " + LoginBean.user.getF_name());
            System.out.println("Post: " + this.studentPost);
            System.out.println("Group: " + this.currentGroup);
            System.out.println();
            
            Post toSubmit = new Post(this.studentPost, new Timestamp(System.currentTimeMillis()), currentQuestion.isIs_mp_question(), currentQuestion.isIs_bp_question(), summaryBit, LoginBean.user.getUser_id(),this.currentGroup.getGroup_id());
            PostDAO.insertPost(toSubmit);
            this.currentGroupDiscussion = PostDAO.getPostByGroupId(currentGroup.getGroup_id());
            this.studentPost = "";
            this.summaryBit = false;
            return true;
        }
        return false;
    }
    
    public void submitAnswer(ActionEvent ae)  {
        
        System.out.println("PORTION BEAN: SUBMITANSWER: Selected answer: " + this.selectedAnswer);
        
        System.out.println(LoginBean.user.getUser_id());
        
        AssignmentDAO.getAssignmentByQuestion(currentQuestion.getQuestion_id());
        
        //System.out.println(AssignmentDAO.getAssignmentByQuestion(currentQuestion.getQuestion_id()).getAssignment_id());
        System.out.println(currentQuestion.getQuestion_id());
        System.out.println(selectedAnswer.charAt(0));
        
        MCResponse m = new MCResponse(LoginBean.user.getUser_id(), 
                AssignmentDAO.getAssignmentByQuestion(currentQuestion.getQuestion_id()).getAssignment_id(), 
                currentQuestion.getQuestion_id(), 
                selectedAnswer.charAt(0));
        
        //if response is in database, update. If not, insert. In the future, remove update, and replace with error.
        
        
        MCResponseDAO.updateMCResponse(m);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Your choice has been submitted.", "Successful Submission!!")); 
        
    }
    
    public boolean renderIndiv() {
        if(currentQuestion == null) {
            return false;
        }
        System.out.println("RENDER INDIV! Result is: " + (!currentQuestion.isIs_bp_question() && !currentQuestion.isIs_mp_question()));
        if(!currentQuestion.isIs_bp_question() && !currentQuestion.isIs_mp_question()) {
            this.currentGroup=null;
            return true;
        } 
            return false;
    }
    
    public boolean renderBP() {
        System.out.println("PortionBean: RenderBP: " + currentQuestion);
        if(currentQuestion == null) {
            return false;
        }
        System.out.println("PortionBean: RenderBP: CurrentQuestion ID: " + currentQuestion.getQuestion_id());
        System.out.println("PortionBean: PRENDER BP! Result is: " + (currentQuestion.isIs_bp_question()));
        if(currentQuestion.isIs_bp_question()) {
            
            if(GroupDAO.isUserInGroup(LoginBean.user.getUser_id(), AssignmentDAO.getAssignmentByQuestion(this.currentQuestion.getQuestion_id()).getAssignment_id())) {
                System.out.println("PortionBean: Passed Check");
                this.currentGroup = GroupDAO.getGroupByUserAndAssignment(LoginBean.user.getUser_id(), AssignmentDAO.getAssignmentByQuestion(this.currentQuestion.getQuestion_id()).getAssignment_id());
                this.currentGroupDiscussion = PostDAO.getBpPostInGroup(currentGroup.getGroup_id());
                System.out.println(">>> Current Group: " + this.currentGroup.getGroup_id());
                
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public boolean renderMP() {
        System.out.println("PortionBean: RenderMP: " + currentQuestion);
         if(currentQuestion == null) {
            return false;
        }
        System.out.println("PortionBean: RenderMP: CurrentQuestion ID: " + currentQuestion.getQuestion_id());
        if(currentQuestion.isIs_mp_question()) {
            
            if(GroupDAO.isUserInGroup(LoginBean.user.getUser_id(), AssignmentDAO.getAssignmentByQuestion(this.currentQuestion.getQuestion_id()).getAssignment_id())) {
                System.out.println("PortionBean: RenderMP: IsUserInGroup == true");
                this.currentGroup = GroupDAO.getGroupByUserAndAssignment(LoginBean.user.getUser_id(), AssignmentDAO.getAssignmentByQuestion(this.currentQuestion.getQuestion_id()).getAssignment_id());
                this.currentGroupDiscussion = PostDAO.getMpPostInGroup(currentGroup.getGroup_id());
                System.out.println(">>> Current Group: " + this.currentGroup.getGroup_id());
                
            } else {
                return false;
            }
            return true;
        } 
        return false;
    }
}
