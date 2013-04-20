package edu.osu.picola.beans;

import edu.osu.picola.beans.AssignmentMenuBean.MenuTab;
import edu.osu.picola.dao.AssignmentDAO;
import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dao.QuestionDAO;
import edu.osu.picola.dataobjects.Assignment;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.Post;
import edu.osu.picola.dataobjects.Question;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author dazey.3
 */
public class GroupSelectedBean implements Serializable, ActionListener{
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("Date", "User", "Post");
    private String columnTemplate = "Date User Post"; 
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private boolean assignmentShow;
    private boolean groupShow;
    private Post selectedPost;

    public Post getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Post selectedPost) {
        this.selectedPost = selectedPost;
    }
    
    public boolean isAssignmentShow() {
        return assignmentShow;
    }

    public void setAssignmentShow(boolean assignmentShow) {
        this.assignmentShow = assignmentShow;
    }

    public boolean isGroupShow() {
        return groupShow;
    }

    public void setGroupShow(boolean groupShow) {
        this.groupShow = groupShow;
    }
    
    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
    
    //-----Group View Information-----
    private List<Post> currentGroupDiscussion;
    private String instructorPost;
    private Group selection;
    //private Assignment assignmentSelection;
    
    //-----Assignment View Information
    private String assignment_descr;
    private Date indivdual_start_date;
    private Date indivdual_end_date;
    private Date BP_start_date;
    private Date BP_end_date;
    private Date MP_start_date;
    private Date MP_end_date;
    private int assignment_id;
    
    private String initquestion;
    private String answer_to_question_explanation;
    private String multiple_choice_answer;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String option_e;
    
    private String bpQuestion;
    private String bpQuestionDesc;
    private String mpQuestion;
    private String mpQuestionDesc;
    private Assignment selectedAssignment;

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }
    
    public String getAssignment_descr() {
        return assignment_descr;
    }

    public Date getIndivdual_start_date() {
        return indivdual_start_date;
    }

    public Date getIndivdual_end_date() {
        return indivdual_end_date;
    }

    public Date getBP_start_date() {
        return BP_start_date;
    }

    public Date getBP_end_date() {
        return BP_end_date;
    }

    public Date getMP_start_date() {
        return MP_start_date;
    }

    public Date getMP_end_date() {
        return MP_end_date;
    }

    public String getInitquestion() {
        return initquestion;
    }

    public String getAnswer_to_question_explanation() {
        return answer_to_question_explanation;
    }

    public String getMultiple_choice_answer() {
        return multiple_choice_answer;
    }

    public String getOption_a() {
        return option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public String getOption_e() {
        return option_e;
    }

    public String getBpQuestion() {
        return bpQuestion;
    }

    public String getBpQuestionDesc() {
        return bpQuestionDesc;
    }
    
    public String getMpQuestion() {
        return bpQuestion;
    }

    public String getMpQuestionDesc() {
        return mpQuestionDesc;
    }
    
    public GroupSelectedBean() {
        currentGroupDiscussion = new ArrayList<Post>();  
          
        if(selection != null){
            this.currentGroupDiscussion = PostDAO.getPostByGroupId(selection.getGroup_id());
        }
        createColumns();
    }
    
    public List<Post> getCurrentGroupDiscussion() {
        return currentGroupDiscussion;
    }

    public void setCurrentGroupDiscussion(List<Post> currentGroupDiscussion) {
        this.currentGroupDiscussion = currentGroupDiscussion;
    }

    public String getInstructorPost() {
        return instructorPost;
    }

    public void setInstructorPost(String instructorPost) {
        this.instructorPost = instructorPost;
    }

    public Group getSelection() {
        return selection;
    }

    public void setSelection(Group selection) {
        this.selection = selection;
    }
   
    public boolean showSelection(){
        return (this.selection!=null);
    }
/*
    public Assignment getAssignmentSelection() {
        return assignmentSelection;
    }

    public void setAssignmentSelection(Assignment assignmentSelection) {
        this.assignmentSelection = assignmentSelection;
    }
*/       
    public void showAssignment(){
        
    }
    
    public void showGroup(){
       
    }
    
    public void changeFlag(Post post){
        System.out.println("Current Post Flag: " + post.getPost_isflaged());
        PostDAO.updatePost(post);
    }
    
    public void createGroups(ActionEvent ae){
        System.out.println("GroupSelectedBean: createGroups");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Complete: ", "Created Groups!"));
    }
    
    public void removePost(ActionEvent ae){
        System.out.println("GroupSelectedBean: RemovePost: " + this.selectedPost.getPost_text());
        PostDAO.removePost(selectedPost.getPost_id());
        this.currentGroupDiscussion = PostDAO.getPostByGroupId(selection.getGroup_id());
        selectedPost = null;
//        System.out.println("GroupSelectedBean: removePost!");
//        CommandLink itm = (CommandLink)ae.getSource();
//        Post toRemove = (Post)itm.getValue();
//        System.out.println("GroupSelectedBean: Post to Remove: " + toRemove.getPost_text());
//        PostDAO.removePost(toRemove.getPost_id());
//        System.out.println("GroupSelectedBean: Post Removed!");
//        this.currentGroupDiscussion = PostDAO.getPostByGroupId(selection.getGroup_id());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Complete: ", "Post Removed!"));
    }
    
    public void updateAssignment(ActionEvent ae){
        System.out.println("GroupSelectedBean: updateAssignment!");
        this.indivdual_end_date.getTime();
        
        Question init = QuestionDAO.getInitQuestion(this.selectedAssignment.getAssignment_id());
        List<Question> bps = QuestionDAO.getBPQuestionByAssignment(this.selectedAssignment.getAssignment_id());
        List<Question> mps = QuestionDAO.getMPQuestionByAssignment(this.selectedAssignment.getAssignment_id());
        
        this.selectedAssignment.setAssignment_descr(this.assignment_descr);
        this.selectedAssignment.setIndividual_start_date(new Timestamp(this.indivdual_start_date.getTime()));
        this.selectedAssignment.setIndividual_end_date(new Timestamp(this.indivdual_end_date.getTime()));
        this.selectedAssignment.setBP_start_date(new Timestamp(this.BP_start_date.getTime()));
        this.selectedAssignment.setBP_end_date(new Timestamp(this.BP_end_date.getTime()));
        this.selectedAssignment.setMP_start_date(new Timestamp(this.MP_start_date.getTime()));
        this.selectedAssignment.setMP_end_date(new Timestamp(this.MP_end_date.getTime()));
        
        
        String answer = "";
        
        switch(this.multiple_choice_answer.charAt(0)){
            case 'a':
                answer = this.option_a;
                break;
            case 'b':
                answer = this.option_b;
                break;
            case 'c':
                answer = this.option_c;
                break;
            case 'd':
                answer = this.option_d;
                break;
            case 'e':
                answer = this.option_e;
                break;
        }
        
        init.setOption_a(this.option_a);
        init.setOption_b(this.option_b);
        init.setOption_c(this.option_c);
        init.setOption_d(this.option_d);
        init.setOption_e(this.option_e);
        init.setMultiple_choice_answer(answer);
        init.setAnswer_to_question_explanation(this.answer_to_question_explanation);
        init.setQuestion(this.initquestion);
        
        if(bps.size()>0){
            bps.get(0).setQuestion(this.bpQuestion);
            bps.get(0).setAnswer_to_question_explanation(this.bpQuestionDesc);
            QuestionDAO.updateQuestion(bps.get(0), this.selectedAssignment.getAssignment_id());
        }
        if(mps.size()>0){
            mps.get(0).setQuestion(this.mpQuestion);
            mps.get(0).setAnswer_to_question_explanation(this.mpQuestionDesc);
            QuestionDAO.updateQuestion(mps.get(0), this.selectedAssignment.getAssignment_id());
        }
        
        AssignmentDAO.updateAssignment(this.selectedAssignment);
        QuestionDAO.updateQuestion(init, this.selectedAssignment.getAssignment_id());
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Complete: ", "Changes Saved!"));
    }
   
    public void onTabChange(TabChangeEvent event) {
        System.out.println("ON TAB CHANGE EVENT");
        
        System.out.println("AssignmentID: " + ((MenuTab)event.getData()).getAssignment().getAssignment_id());
        System.out.println();
        this.selectedAssignment = ((MenuTab)event.getData()).getAssignment();
       
        
        this.assignment_descr = this.selectedAssignment.getAssignment_descr();
        this.indivdual_start_date = this.selectedAssignment.getIndividual_start_date();
        this.indivdual_end_date = this.selectedAssignment.getIndividual_end_date();
        this.BP_start_date = this.selectedAssignment.getBP_start_date();
        this.BP_end_date = this.selectedAssignment.getBP_end_date();
        this.MP_start_date = this.selectedAssignment.getMP_start_date();
        this.MP_end_date = this.selectedAssignment.getMP_end_date();
    
        Question init = QuestionDAO.getInitQuestion(this.selectedAssignment.getAssignment_id());
        List<Question> bp = QuestionDAO.getBPQuestionByAssignment(this.selectedAssignment.getAssignment_id());
        List<Question> mp = QuestionDAO.getMPQuestionByAssignment(this.selectedAssignment.getAssignment_id());
        
        System.out.println("Number of BP Questions: " + bp.size());
        System.out.println("Number of MP Questions: " + mp.size());
        
        if(init != null){
            this.initquestion = init.getQuestion();
            this.answer_to_question_explanation = init.getAnswer_to_question_explanation();
            this.multiple_choice_answer = init.getMultiple_choice_answer();
            this.option_a = init.getOption_a();
            this.option_b = init.getOption_b();
            this.option_c = init.getOption_c();
            this.option_d = init.getOption_d();
            this.option_e = init.getOption_e();
        }
        
    
        if(bp.size()>0){
            this.bpQuestion = bp.get(0).getQuestion();
            this.bpQuestionDesc = bp.get(0).getAnswer_to_question_explanation();
        }
        if(mp.size()>0){
            this.mpQuestion = mp.get(0).getQuestion();
            this.mpQuestionDesc = mp.get(0).getAnswer_to_question_explanation();
        }
        
        this.assignmentShow = true;
        this.groupShow = false;
        System.out.println();
        clearSelection();
    }

    @Override
    public void processAction(ActionEvent ae) throws AbortProcessingException 
    {
        clearSelection();
        CommandLink itm = (CommandLink)ae.getSource();
        this.selection = (Group)itm.getValue();
        this.assignmentShow = false;
        this.groupShow = true;
        this.currentGroupDiscussion = PostDAO.getPostByGroupId(selection.getGroup_id());
        System.out.println("GROUPSELECTEDBEAN: processAction occurred!!!!");
        System.out.println("GROUP SELECTED: " + selection);
    }
    
    private void clearSelection(){
        System.out.println("CLEARING GROUP SELECTION!");
        this.selection = null;
        this.currentGroupDiscussion = new ArrayList<Post>();
        this.instructorPost = "";
    }
    
    static public class ColumnModel implements Serializable {  
        private String header;  
        private String property;  
  
        public ColumnModel(String header, String property) {  
            this.header = header;  
            this.property = property;  
        }  
        public String getHeader() {  
            return header;  
        }  
        public String getProperty() {  
            return property;  
        }  
    }
    
    public void createColumns() {  
        String[] columnKeys = columnTemplate.split(" ");  
        columns.clear();        
          
        for(String columnKey : columnKeys) {  
            String key = columnKey.trim();  
              
            if(VALID_COLUMN_KEYS.contains(key)) {  
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));  
            }  
        }
    }
    public boolean instructorSubmitPost(){
        System.out.println("GroupSelectedBean: Instructor Submitting Post");
        if(this.instructorPost.length() > 0){
            System.out.println("User: " + LoginBean.user.getF_name());
            System.out.println("Post: " + this.instructorPost);
            System.out.println("Group: " + this.selection);
            System.out.println();
            
            Post toSubmit = new Post(this.instructorPost, new Timestamp(System.currentTimeMillis()), false, true, false, LoginBean.user.getUser_id(),this.selection.getGroup_id());
            PostDAO.insertPost(toSubmit);
            this.currentGroupDiscussion = PostDAO.getPostByGroupId(selection.getGroup_id());
            this.instructorPost = "";
            return true;
        }
        return false;
    }
}
