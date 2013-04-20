package edu.osu.picola.dataobjects;

import java.util.ArrayList;

public class Group extends ArrayList<User>{
	
	private static final long serialVersionUID = 1L;
	private int group_id;
	
	public Group(){
		super();
	}
	
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
	public int getGroup_id() {
		return group_id;
	}
	
	public Group(int group_id) {
		super();
		this.group_id = group_id;
	}
        
        @Override
        public String toString(){
            return ("Group#: " + this.group_id);
        }
}
