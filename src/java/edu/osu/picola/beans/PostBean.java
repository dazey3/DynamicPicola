/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.osu.picola.beans;

import edu.osu.picola.businesslogic.PostHandler;
import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dataobjects.Post;
import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author akers.79
 */
@ManagedBean(name="postBean")
@SessionScoped
public class PostBean {

    private int post_id;
    private String post_text;
    private Timestamp post_time;
    private boolean MP_bit;
    private boolean BP_bit;
    private boolean summary_bit;
    private boolean is_flagged;
    private int user_id;
    private int group_id;
    private Post post;

    public void create(ActionEvent event){
        user_id=(Integer)event.getComponent().getAttributes().get("uid");
        group_id=(Integer)event.getComponent().getAttributes().get("gid");
        setBP_bit(true);
        setMP_bit(false);
        setSummary_bit(false);
        createPost();
    }
    
    public void viewPost(ActionEvent event){
        post_id = (Integer)event.getComponent().getAttributes().get("pid");
        setPost(post_id);
        setPost_text(post.getPost_text());
        setPost_time(post.getPost_time());
        setMP_bit(post.getPost_mp());
        setBP_bit(post.getPost_bp());
        setSummary_bit(post.getPost_summary());
        setIs_flagged(post.getPost_isflaged());
        setUser_id(post.getUser_id());
        setGroup_id(post.getGroup_id());
    }
    public void setPost(int postid){
        post_id = postid;
        post=PostDAO.getPostById(post_id);
    }
    
    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public Timestamp getPost_time() {
        return post_time;
    }

    public void setPost_time(Timestamp post_time) {
        this.post_time = post_time;
    }

    public boolean isMP_bit() {
        return MP_bit;
    }

    public void setMP_bit(boolean MP_bit) {
        this.MP_bit = MP_bit;
    }

    public boolean isBP_bit() {
        return BP_bit;
    }

    public void setBP_bit(boolean BP_bit) {
        this.BP_bit = BP_bit;
    }

    public boolean isSummary_bit() {
        return summary_bit;
    }

    public void setSummary_bit(boolean summary_bit) {
        this.summary_bit = summary_bit;
    }

    public boolean isIs_flagged() {
        return is_flagged;
    }

    public void setIs_flagged(boolean is_flagged) {
        this.is_flagged = is_flagged;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public void createPost() {
        PostHandler.createPost(user_id, group_id, post_text, summary_bit, MP_bit, BP_bit);
    }
    
    public void editPost() {
        PostHandler.editPost(post_id, post_text, is_flagged);
    }
    
    public void deletePost() {
        PostHandler.deletePost(post_id);
    }
    
}
