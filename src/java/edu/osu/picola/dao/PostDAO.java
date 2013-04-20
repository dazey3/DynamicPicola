package edu.osu.picola.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.osu.picola.dataobjects.Post;

/**
 * This class is responsible for handling
 * all SQL directly related to the post
 * table.
 * 
 * TODO add queries based off time
 * @author akers.79
 */
public class PostDAO extends DAO {

	public static Post getPostById(int post_id) {
		String query = "SELECT * FROM post "
					 + "WHERE post_id = '"
					 + post_id + "'";
		return loadPosts(queryDB(query)).get(0);
	}

	/**
	 * @param user_id
	 *            the user who made the posts
	 * @return a list of Posts made by a specific user
	 */
	public static List<Post> getPostByUserId(int user_id) {
		String query = "SELECT * FROM post "
				     + "WHERE user_id = '"
				     + user_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}

	/**
	 * @param group_id
	 *            the group who posts you want
	 * @return a list of post made by a group
	 */
	public static List<Post> getPostByGroupId(int group_id) {
		String query = "SELECT * FROM post WHERE group_id = '"
				     + group_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}

	/**
	 * @param user_id
	 *            the student you want
	 * @param group_id
	 *            the group you want
	 * @return a list of Posts made by one user in one group
	 */
	public static List<Post> getPostByUserId(int user_id, int group_id) {
		String query = "SELECT * FROM post"
				     + "WHERE user_id = '"
				     + user_id + "' "
				     + "AND group_id = '" 
				     + group_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}

	/**
	 * @return a list of all Posts that have been flagged
	 */
	public static List<Post> getFlaggedPosts() {
		String query = "SELECT * FROM post WHERE is_flagged is true";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}

	/**
	 * @return a list of all Posts that have been flagged
	 */
	public static List<Post> getSummaryPostInGroup(int group_id) {
		String query = "SELECT * FROM post "
					 + "WHERE summary_bit is true "
					 + "AND group_id = '"
				     + group_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}

	/**
	 * @param group_id the group you want
	 * @return list of all posts from the mp portion
	 * of a group's discussion
	 */
	public static List<Post> getMpPostInGroup(int group_id) {
		String query = "SELECT * FROM post "
			         + "WHERE MP_bit is true "
			         + "AND group_id = '"
			         + group_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}
	
	/**
	 * @param group_id the group you want
	 * @return list of all posts from the bp portion
	 * of a group's discussion
	 */
	public static List<Post> getBpPostInGroup(int group_id) {
		String query = "SELECT * FROM post "
				     + "WHERE BP_bit is true "
				     + "AND group_id = '"
				     + group_id + "'";
		ResultSet rs = queryDB(query);
		return loadPosts(rs);
	}
	
	public static void insertPost(Post post){
		String insert = "INSERT INTO post (post_text, post_time, MP_bit, BP_bit, summary_bit, is_flagged, user_id,group_id)"
			+ "VALUES ('" + post.getPost_text()
			+ "', '" + post.getPost_time()
			+ "', " + post.isMP_bit()
			+ ", " + post.isBP_bit()
			+ ", " + post.isSummary_bit()
			+ ", " + post.isIs_flagged()
			+ ", '" + post.getUser_id()
			+ "', '" + post.getGroup_id()+"')";
		insertDB(insert);
	}

	public static void updatePost(Post post) {
		String update = "UPDATE post SET post_text = '" + post.getPost_text() 
					  + "', post_time = '" + post.getPost_time()
					  + "', MP_bit = " + post.isMP_bit()
					  + ", BP_bit = " + post.isBP_bit()
					  + ", summary_bit = " + post.isSummary_bit()
					  + ", is_flagged = " + post.isIs_flagged()
					  + ", user_id = '" + post.getUser_id()
					  + "', group_id ='" + post.getGroup_id()
					  + "' WHERE post_id='" + post.getPost_id() + "'";
		updateDB(update);
	}
	
	/**
	 * Removes a single post
	 * 
	 * @param post_id
	 *            the id for the post to delete
	 */
	public static void removePost(int post_id) {
		String delete = "DELETE FROM post "
				      + "WHERE post_id = '"
				      + post_id + "'";
		deleteRecord(delete);
	}

	public static void removeAllUsersPosts(int user_id) {
		String delete = "DELETE FROM post"
				      + "WHERE user_id = '"
				      + user_id + "'";
		deleteRecord(delete);
	}

	public static void removeAllGroupPost(int group_id) {
		String delete = "DELETE FROM post "
					  + "WHERE group_id = '" 
					  + group_id + "'";
		deleteRecord(delete);
	}

	public static void removeAllUsersPostInGroup(int user_id, int group_id) {
		String delete = "DELETE FROM post "
			          + "WHERE group_id = '"
			          + group_id
				      + "' AND user_id = '"
				      + user_id + "'";
		deleteRecord(delete);
	}
	
	private static List<Post> loadPosts(ResultSet rs) {
		List<Post> posts = new ArrayList<Post>();
		try {
			while (rs.next()) {
				posts.add(new Post(rs));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return posts;
	}
}
