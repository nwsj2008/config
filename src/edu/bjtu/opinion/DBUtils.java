package edu.bjtu.opinion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ch
 * 
 */
public class DBUtils {
	private String url = "";
	private String username = "";
	private String password = "";
	private Connection connection = null;
	private Statement statement = null;
	private static DBUtils instance = null;

	private final int ItemPerPage = 30;

	private DBUtils() {
		BufferedReader reader = null;
		try {
			String filename = System.getProperty("user.dir") + "\\db";
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			url = line.substring(line.indexOf('#') + 1);
			line = reader.readLine();
			username = line.substring(line.indexOf('#') + 1);
			line = reader.readLine();
			password = line.substring(line.indexOf('#') + 1);
			reader.close();
			reader = null;
		} catch (Exception e) {
			e.printStackTrace();
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				reader = null;
			}
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				connection = null;
			}
		}
	}

	public static DBUtils getInstance() {
		if (instance == null) {
			instance = new DBUtils();
		}
		return instance;
	}

	/**
	 * get pages number
	 * 
	 * @param type
	 *          0:bbs post, 1: bbs reply, 2: news post, 3:news reply
	 * @param postid
	 * @return
	 */
	public int getPages(int type, String postid) {
		int page = 0;

		ResultSet rs = null;
		StringBuilder sb = new StringBuilder("select count(*) as count from ");
		if (type == 0) {
			sb.append("tb_parser_bbs");
		} else if (type == 1) {
			sb.append("tb_re_bbs where idofcard='")
					.append(postid).append("'");
		} else if (type == 2) {
			sb.append("tb_parser_news");
		} else if (type == 3) {
			sb.append("tb_re_news where idofnews='")
					.append(postid).append("'");
		}

		try {
			if (statement == null) {
				statement = connection.createStatement();
			}
			rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				int total = rs.getInt("count");
				if(total != 0){
					page = total / ItemPerPage + 1;
				}				
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}


	public List<String[]> getPosts(int type, int page) {
		List<String[]> list = new ArrayList<String[]>();

		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		if (type == 0) {
			sb.append("tb_parser_bbs");
		} else if (type == 2) {
			sb.append("tb_parser_news");
		}
		sb.append(" order by id desc limit ").append(page * ItemPerPage)
				.append(",").append(ItemPerPage);

		try {
			if (statement == null) {
				statement = connection.createStatement();
			}
			rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				String[] texts = new String[6];
				if (type == 0) {
					texts[0] = rs.getString("id");
					texts[1] = rs.getString("topic");
					texts[2] = rs.getString("author");
					texts[3] = rs.getString("releasetime");
					texts[4] = rs.getString("content");
					//texts[5] = rs.getString("renum");
					texts[5] = rs.getString("url");
				} else if (type == 2) {
					texts[0] = rs.getString("id");
					texts[1] = rs.getString("title");
					texts[2] = rs.getString("author");
					texts[3] = rs.getString("releasetime");
					texts[4] = rs.getString("content");
					//texts[5] = rs.getString("renum");
					texts[5] = rs.getString("url");
				}
				list.add(texts);
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<String[]> getReplies(int type, String postid, int page) {
		List<String[]> list = new ArrayList<String[]>();

		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		if (type == 1) {
			sb.append("tb_re_bbs").append(" where idofcard='").append(postid).append("'");
		} else if (type == 3) {
			sb.append("tb_re_news").append(" where idofnews='").append(postid).append("'");
		}
		sb.append(" order by id desc limit ").append(page * ItemPerPage)
				.append(",").append(ItemPerPage);

		try {
			if (statement == null) {
				statement = connection.createStatement();
			}
			rs = statement.executeQuery(sb.toString());
			while (rs.next()) {
				String[] texts = new String[6];
				texts[0] = rs.getString("id");
				texts[1] = rs.getString("retitle");
				texts[2] = rs.getString("reauthor");
				texts[3] = rs.getString("retime");
				texts[4] = rs.getString("recontent");
				texts[5] = rs.getString("url");
				list.add(texts);
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
