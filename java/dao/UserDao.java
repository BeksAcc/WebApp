package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import databean.Customer;
import databean.User;

public class UserDao {
    private final List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver =  "com.mysql.jdbc.Driver";
	private String jdbcURL = "jdbc:mysql://localhost:3306/test";
    private String tableName = "users";
    
    private synchronized void releaseConnection(final Connection con) {
        connectionPool.add(con);
    }

    public Customer read_customer(User user) throws MyDAOException{
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * from hw5_customer_info where customer_email=?");
            PreparedStatement psts = con.prepareStatement("SELECT * from hw5_customer_credential where email=?");
            psts.setString(1, user.getUserName());
            preparedStatement.setString(1, user.getUserName());
            ResultSet result = preparedStatement.executeQuery();
            ResultSet rst = psts.executeQuery();

            Customer new_user;
            if(!result.next() || !rst.next()) new_user = null;

            else new_user = new Customer(result.getString("customer_email"),result.getString("address"), rst.getString("password"), result.getString("name"), result.getInt("balance"));

            result.close();
            rst.close();

            releaseConnection(con);
            return new_user;
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
            }
            throw new MyDAOException(e);
        }
    }
    public Customer read(User user) throws MyDAOException {
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * from hw5_employee_credential where employee_id=?");
            preparedStatement.setString(1, user.getUserName());
            ResultSet result = preparedStatement.executeQuery();
            
            Customer new_user;
            if(!result.next()) new_user = null;

            else new_user = new Customer(result.getString("employee_id"),result.getString("password"));

            result.close();

            releaseConnection(con);
            return new_user;
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
            }
            throw new MyDAOException(e);
        }
    }

    public void deleteUser(int id) throws MyDAOException{
        Connection con = null;
		try {
            con = getConnection();
            
			final PreparedStatement pstmt = con
                    .prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            
            pstmt.setInt(1, id);

            pstmt.execute();

            pstmt.close();

			releaseConnection(con);
             
		} catch (final Exception e) {
			try {
				if (con != null)
					con.close();
            } catch (final SQLException e2) { /* ignore */
            }		
            throw new MyDAOException(e);
        }
    }

    public Customer createUser(Customer user) throws MyDAOException {
        Connection con = null;
		try {
            con = getConnection();
            
			final PreparedStatement pstmt = con
                    .prepareStatement("INSERT INTO hw5_customer_credential (email,password) VALUES ( ?, ?);");
                    
            final PreparedStatement second_table = con
                    .prepareStatement("INSERT INTO hw5_customer_info (customer_email,address,balance,name) VALUES (? ,?, ?, ?)");

			pstmt.setString(1, user.getCustomer_email());
            pstmt.setString(2, user.getPassword());
            second_table.setString(1, user.getCustomer_email());
            second_table.setString(2, user.getAddress());
            second_table.setInt(3, user.getBalance());
            second_table.setString(4, user.getName());

            int count = pstmt.executeUpdate();
            if (count != 1)
                throw new SQLException("Insert updated " + count + " rows");

            int c2 = second_table.executeUpdate();
            if(c2 != 1)
                throw new SQLException("Insert updated " + c2 + " rows");

            pstmt.close();
            second_table.close();

            releaseConnection(con);
            
            return user;
             
		} catch (final Exception e) {
			try {
				if (con != null)
					con.close();
            } catch (final SQLException e2) { /* ignore */
            }		
            throw new MyDAOException(e);
        }
    }

	private synchronized Connection getConnection() throws MyDAOException {
        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }

        try {
            Class.forName(jdbcDriver);
        } catch (final ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL, "root", "makarenko77");
        } catch (final SQLException e) {
            throw new MyDAOException(e);
        }
    }
}