package edu.osu.picola.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a base  class for handling
 * constructor and all basic SQL methods 
 */
public class DAO {

	/**
	 * @param query a String representing the SQL query
	 * @return the ResultSet of the query
	 */
	protected static ResultSet queryDB(String query) {
		ResultSet rs = null;
		Connection conn = MySQLDBConnection.getConnection();
		if (conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
			} catch (SQLException e) {
				System.out.println("query : " + query + " failed!");
				e.printStackTrace();
			}
		} else {
			System.out.println("Connection is null!");
		}
		return rs;
	}

	/**
	 * This method inserts new records into db.
	 * @param insert a String representing insert 
	 * statement
	 */
	protected static void insertDB(String insert) {
		Connection conn = MySQLDBConnection.getConnection();
		if (conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(insert);
				pst.execute();
			} catch (SQLException e) {
				System.out.println("insert : " + insert+ " failed!");
				e.printStackTrace();
			}
		} else {
			System.out.println("Connection is null!");
		}
	}
	
	/**
	 * This method updates a record in the db.
	 * @param update a String representation of
	 * the update statement
	 */
	protected static void updateDB(String update) {
		Connection conn = MySQLDBConnection.getConnection();
		if (conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(update);
				pst.executeUpdate();
			} catch (SQLException e) {
				System.out.println("update : " + update+ " failed!");
				e.printStackTrace();
			}
		} else {
			System.out.println("Connection is null!");
		}
	}
	
	/**
	 * This method updates a record in the db.
	 * @param delete a String representation of
	 * the update statement
	 */
	protected static void deleteRecord(String delete) {
		Connection conn = MySQLDBConnection.getConnection();
		if (conn != null) {
			try {
				PreparedStatement pst = conn.prepareStatement(delete);
				pst.execute();
			} catch (SQLException e) {
				System.out.println("delete : " + delete + " failed!");
				e.printStackTrace();
			}
		} else {
			System.out.println("Connection is null!");
		}
	}
}
