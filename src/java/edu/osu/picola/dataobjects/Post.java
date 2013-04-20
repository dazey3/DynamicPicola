package edu.osu.picola.dataobjects;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.primefaces.component.panel.Panel;

/**
 * This class models a user's
 * post in the pi-cola system.
 * @author akers.79
 */
public class Post implements Serializable{
	private int post_id;
	private String post_text;
	private Timestamp post_time;
	private boolean MP_bit;
	private boolean BP_bit;
	private boolean summary_bit;
	private boolean is_flagged;
	private int user_id;
	private int group_id;
	
	/**
	 * Constructs a post object from resultSet's
	 * current cursor location.
	 * @param rs ResultSet containing data from
	 * post table
	 */
	public Post(ResultSet rs) {
		try {
			BP_bit = rs.getBoolean("BP_bit");
			group_id = rs.getInt("group_id");
			is_flagged = rs.getBoolean("is_flagged");
			MP_bit = rs.getBoolean("MP_bit");
			post_id=rs.getInt("post_id");
			post_text=rs.getString("post_text");
			post_time=rs.getTimestamp("post_time");
			summary_bit=rs.getBoolean("summary_bit");
			user_id=rs.getInt("user_id");
		} catch (SQLException e) {
			System.out.println("Failed to read in post!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs a post object from field data
	 * @param post_id
	 * @param post_text
	 * @param post_time
	 * @param mp_bit
	 * @param bp_bit
	 * @param summary_bit
	 * @param is_flagged
	 * @param user_id
	 * @param group_id
	 */
	public Post(String post_text, Timestamp post_time, boolean mp_bit, boolean bp_bit, boolean summary_bit, int user_id, int group_id) {
		post_id = -1;
		this.post_text = post_text;
		this.post_time = post_time;
		this.MP_bit = mp_bit;
		this.BP_bit = bp_bit;
		this.summary_bit = summary_bit;
		is_flagged = false;
		this.user_id = user_id;
		this.group_id = group_id;
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

        public boolean getPost_mp() {
		return MP_bit;
	}
        
        public boolean getPost_bp() {
		return BP_bit;
	}
        
        public boolean getPost_summary() {
		return summary_bit;
	}
        
        public boolean getPost_isflaged() {
		return is_flagged;
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

	public void setMP_bit(boolean mP_bit) {
		MP_bit = mP_bit;
	}

	public boolean isBP_bit() {
		return BP_bit;
	}

	public void setBP_bit(boolean bP_bit) {
			BP_bit = bP_bit;
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
        
        public void toggleFlag(){
            this.is_flagged = !this.is_flagged;
        }
        @Override
        public String toString(){
            return this.post_text;
        }
}
