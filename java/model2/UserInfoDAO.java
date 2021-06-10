package model2;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

import databean2.UserInfo;

public class UserInfoDAO extends GenericDAO<UserInfo>  {
	public UserInfoDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(UserInfo.class, tableName, cp);
	}

}
