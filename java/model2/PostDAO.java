package model2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean2.Friends;
import databean2.PostBean;

public class PostDAO extends GenericDAO<PostBean> {
	public PostDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(PostBean.class, tableName, cp);
	}
	static void reverse(PostBean[] a)
    {
        Collections.reverse(Arrays.asList(a));
        System.out.println(Arrays.asList(a));
    }
	public PostBean[] getItems() throws RollbackException {
		PostBean[] items = match();
		
		reverse(items);

		return items;
	}
	public PostBean[] getItems(Friends[] friends, String user) throws RollbackException {
		PostBean[] items = match();
		PostBean[] new_item = filter(items,friends,user);
		reverse(new_item);

		return new_item;
	}
	public PostBean[] filter(PostBean[] items, Friends[] friends, String user){
		ArrayList<PostBean> cache = new ArrayList<>();
		for(int x = 0;x < items.length;x++){
			if(items[x].getUser_id().equals(user)){
				cache.add(items[x]);
				continue;
			}
			for(int y = 0;y < friends.length;y++){
				if(friends[y] == null) continue;
				if(items[x].getUser_id().equals(friends[y].getFriendsUserName()) ||
					items[x].getUser_id().equals(friends[y].getUserName())){
					cache.add(items[x]);
				}
			}
			
		}
		PostBean[] new_items = new PostBean[cache.size()];
		for(int x = 0;x < new_items.length;x++){
			new_items[x] = cache.get(x);
		}
		return new_items;
	}
	public PostBean[] getItemsBy(String key,String sortBy, Friends[] friends, String user) throws RollbackException {
		PostBean[] items = match(MatchArg.contains(sortBy, key));
		
		PostBean[] new_item = filter(items,friends,user);
		reverse(new_item);

		return new_item;
	}
}
