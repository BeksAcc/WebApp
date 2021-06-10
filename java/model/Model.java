// package model;


// import javax.servlet.ServletConfig;
// import javax.servlet.ServletException;

// import org.genericdao.ConnectionPool;
// import org.genericdao.DAOException;



// public class Model {
//     private UserDao userDao;
//     private ItemDAO itemDaO;

//     public Model(ServletConfig config) throws ServletException {
//         try {
//             String jdbcDriver = config.getInitParameter("jdbcDriverName");
//             String jdbcURL = config.getInitParameter("jdbcURL");

//             ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);

//             this.setUserDao(new UserDao(pool, "todolist_users"));
//             this.setItemDaO(new ItemDAO(pool, "todolist"));
//         } catch (DAOException e) {
//             throw new ServletException(e);
//         }
//     }

//     public ItemDAO getItemDaO() {
//         return itemDaO;
//     }

//     public void setItemDaO(ItemDAO itemDaO) {
//         this.itemDaO = itemDaO;
//     }

//     public UserDao getUserDao() {
//         return userDao;
//     }

//     public void setUserDao(UserDao userDao) {
//         this.userDao = userDao;
//     }
    
// }