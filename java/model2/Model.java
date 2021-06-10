package model2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private UserInfoDAO userInfoDAO;
	private UserDAO userDAO;
	private PostDAO postDAO;
	private FriendsDAO friendsDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");

			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);

			userDAO = new UserDAO(pool, "users_table");
			setPostDAO(new PostDAO(pool, "posts_table"));
			setUserInfoDAO(new UserInfoDAO(pool, "tp_user_info"));
			setFriendsDAO(new FriendsDAO(pool, "tp_friends"));
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	public FriendsDAO getFriendsDAO() {
		return friendsDAO;
	}

	public void setFriendsDAO(FriendsDAO friendsDAO) {
		this.friendsDAO = friendsDAO;
	}

	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	public PostDAO getPostDAO() {
		return postDAO;
	}

	public void setPostDAO(PostDAO postDAO) {
		this.postDAO = postDAO;
	}
	public UserDAO getUserDAO()  { return userDAO; }
}
