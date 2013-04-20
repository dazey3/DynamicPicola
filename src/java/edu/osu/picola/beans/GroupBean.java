/*
 * @ Yan Xu
 */
package edu.osu.picola.beans;

import edu.osu.picola.dao.GroupDAO;
import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dataobjects.Group;
import edu.osu.picola.dataobjects.Post;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author xuquentin
 */
@ManagedBean(name="groupBean")
@SessionScoped
public class GroupBean {
    private int groupId;
    private Group group;
    
    
    public void viewGroup(ActionEvent event){
        //String ehruser = (String) event.getComponent().getAttributes().get("ehrid");
        //System.out.println("ehrusermap="+ehruser);
        groupId = (Integer)event.getComponent().getAttributes().get("gid");
        setGroupId(groupId);
    }

    
    public void setGroupId(int groupId) {
        this.groupId = groupId;
        group = GroupDAO.getGroupMembers(groupId);
    }
    
    public int getGroupId(){
        return groupId;
    }
    
    public Group getGroup(){
        return group;
    }
    
    public List<Post> getPosts(){
        return PostDAO.getPostByGroupId(groupId);
    }
    
}
