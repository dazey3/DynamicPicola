package edu.osu.picola.businesslogic;

import edu.osu.picola.dao.PostDAO;
import edu.osu.picola.dataobjects.Post;
import java.sql.Timestamp;

public class PostHandler {
	public static void createPost(int user_id, int group_id,String postText, boolean isSummary, boolean isMP, boolean isBP) {
		Timestamp postDate = new Timestamp(System.currentTimeMillis());
		Post post = new Post(postText, postDate, isMP, isMP, isSummary, user_id, group_id);
		PostDAO.insertPost(post);
	}
	
	public static void editPost(int post_id,String post_text, boolean isFlagged) {
		Post post = PostDAO.getPostById(post_id);
		post.setPost_text(post_text);
		post.setIs_flagged(isFlagged);
		PostDAO.updatePost(post);
	}
	
	public static void deletePost(int post_id) {
		PostDAO.removePost(post_id);
	}
}
