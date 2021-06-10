package model2;

// import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean2.ItemBean;

public class ItemDAO extends GenericDAO<ItemBean> {
	public ItemDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(ItemBean.class, tableName, cp);
	}

	public void addToTop(ItemBean item) throws RollbackException {
		try {
			Transaction.begin();

			// Get item at top of list
			ItemBean[] a = match(MatchArg.min("position"));

			ItemBean topBean;
			if (a.length == 0) {
				topBean = null;
			} else {
				topBean = a[0];
			}

			int newPos;
			if (topBean == null) {
				// List is empty...just add it with position = 1
				newPos = 1;
			} else {
				// Create the new item with position one less than the top
				// bean's position
				newPos = topBean.getPosition() - 1;
			}

			item.setPosition(newPos);

			// Create a new ItemBean in the database with the next id number
			// Note that GenericDAO.create() will use auto-increment when
			// the primary key field is an int or a long.
			create(item);

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void addToBottom(ItemBean item) throws RollbackException {
		try {
			Transaction.begin();

			// Get item at bottom of list
			ItemBean[] a = match(MatchArg.max("position"));

			ItemBean bottomBean;
			if (a.length == 0) {
				bottomBean = null;
			} else {
				bottomBean = a[0];
			}

			int newPos;
			if (bottomBean == null) {
				// List is empty...just add it with position = 1
				newPos = 1;
			} else {
				// New item's position is one less than the top bean's position
				newPos = bottomBean.getPosition() + 1;
			}

			item.setPosition(newPos);

			// Create a new ItemBean in the database with the next id number
			// Note that GenericDAO.create() will use auto-increment when
			// the primary key field is an int or a long.
			create(item);

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public ItemBean[] getItems(String userName) throws RollbackException {
		ItemBean[] items = match(MatchArg.equals("userName", userName));
		
		// Arrays.sort(items, (ItemBean i1, ItemBean i2) -> i1.getPosition() - i2.getPosition());

		return items;
	}
	
    public void delete(int id, String userName) throws RollbackException {
        if (!Transaction.isActive()) {
            try {
                Transaction.begin();
                delete(id, userName);
                Transaction.commit();
                return;
            } finally {
                if (Transaction.isActive()) {
                    Transaction.rollback();
                }
            }
        }
        
        ItemBean b = read(id);
        
        if (b == null) {
            throw new RollbackException("There is no item on the list with id = " + id);
        }
        
        if (!userName.equals(b.getUserName())) {
            throw new RollbackException("This item is not on your list: id = " + id);
        }
        
        delete(id);
    }

    public void deleteItems(String userName) throws RollbackException {
        if (!Transaction.isActive()) {
            try {
                Transaction.begin();
                deleteItems(userName);
                Transaction.commit();
                return;
            } finally {
                if (Transaction.isActive()) {
                    Transaction.rollback();
                }
            }
        }
        
        ItemBean[] a = getItems(userName);
        for (ItemBean b : a) {
            delete(b.getId());
        }
    }
}
