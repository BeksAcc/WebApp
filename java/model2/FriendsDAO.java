package model2;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;

import databean2.Friends;

public class FriendsDAO  extends GenericDAO<Friends>  {
	public FriendsDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(Friends.class, tableName, cp);
	}
}
