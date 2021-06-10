package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import databean.Customer;
import databean.CustomerFunds;
import databean.DataDeposit;
import databean.Fund;
import databean.User;

public class ItemDao {

    private final List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver =  "com.mysql.jdbc.Driver";
	private String jdbcURL = "jdbc:mysql://localhost:3306/test";
    private String tableName = "hw5_funds";
    
    private synchronized void releaseConnection(final Connection con) {
        connectionPool.add(con);
    }

    public ArrayList<CustomerFunds> get_funds(Customer user) throws MyDAOException{
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * from hw5_customer_funds where customer_email = ?");
            preparedStatement.setString(1, user.getCustomer_email());
            ResultSet result = preparedStatement.executeQuery();

            ArrayList<CustomerFunds> new_funds = new ArrayList<>();

            while(result.next()){
                PreparedStatement pst = con.prepareStatement("SELECT * from hw5_funds where fund_name = ?");
                pst.setString(1, result.getString("fund_name"));
                ResultSet rs = pst.executeQuery();
                CustomerFunds customerFunds = new CustomerFunds(result.getInt("id"), result.getString("customer_email"), result.getString("fund_name"), result.getInt("number_of_shares"),result.getInt("total_value"));

                if(rs.next())
                    customerFunds.setPrice(rs.getInt("current_price"));
                
                new_funds.add(customerFunds);

            }
            result.close();

            releaseConnection(con);
            
            return new_funds;
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                throw new MyDAOException(exception.toString());
            }
            throw new MyDAOException(e);
        }
    }

    public Customer refund(int id, Customer customer) throws MyDAOException{
        Connection con = null;
		try {
            con = getConnection();

            final PreparedStatement obj = con.prepareStatement("SELECT * FROM hw5_customer_funds WHERE id = ?");

            obj.setInt(1, id);

            ResultSet rs = obj.executeQuery();
            
			final PreparedStatement pstmt = con
                    .prepareStatement("DELETE FROM hw5_customer_funds WHERE id = ?");
            
            final PreparedStatement pstmt2 = con
                .prepareStatement("UPDATE hw5_customer_info SET balance=? WHERE customer_email = ? ;");

            pstmt2.setString(2, customer.getCustomer_email());
            if(rs.next())
                pstmt2.setInt(1, customer.getBalance() - rs.getInt("total_value"));
            else
                pstmt2.setInt(1, customer.getBalance());
            
            int c = pstmt2.executeUpdate();
            if (c != 1)
                throw new SQLException("Insert updated " + c + " rows");
            customer.setBalance(customer.getBalance() - rs.getInt("total_value"));
            
            pstmt2.close();

            pstmt.setInt(1, id);
            
            pstmt.execute();

            pstmt.close();

            releaseConnection(con);
            
            return customer;
             
		} catch (final Exception e) {
			try {
				if (con != null)
					con.close();
            } catch (final SQLException e2) { /* ignore */
            }		
            throw new MyDAOException(e);
        }
    }

    public Customer add_deposit(CustomerFunds fund, int current , Customer customer) throws MyDAOException{
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * from hw5_funds where fund_name = ?");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO hw5_customer_funds (customer_email,fund_name, number_of_shares, total_value) values (?, ?, ?, ?)");
            final PreparedStatement updateStatement = con
                .prepareStatement("UPDATE hw5_customer_info SET balance=? WHERE customer_email = ? ;");

            pst.setString(1, fund.getFund_name());
            updateStatement.setString(2, fund.getCustomer_email());


            ResultSet rs = pst.executeQuery();

            int total = fund.getNumber_of_shares();

            if(rs.next()) total *= rs.getInt("current_price");

            updateStatement.setInt(1, total + current);

            updateStatement.executeUpdate();
            
            pstmt.setString(1, fund.getCustomer_email());
            pstmt.setString(2, fund.getFund_name());
            pstmt.setInt(3, fund.getNumber_of_shares());
            pstmt.setInt(4,  total);

            Customer new_customer = new Customer(customer.getCustomer_email(),customer.getAddress(),current + total ,customer.getName());

            int count = pstmt.executeUpdate();
            if (count != 1)
                throw new SQLException("Insert updated " + count + " rows");

            pst.close();
            pstmt.close();
            updateStatement.close();

            releaseConnection(con);
            
            return new_customer;
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
            }
            throw new MyDAOException(e);
        }
    }

    public Fund read_fund(Fund fund) throws MyDAOException {
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * from hw5_funds where fund_name=?");
            preparedStatement.setString(1, fund.getName());
            ResultSet result = preparedStatement.executeQuery();
            
            Fund new_fund = null;
            if(result.next()) new_fund = new Fund(result.getString("fund_name"),result.getInt("current_price"));

            result.close();

            releaseConnection(con);
            return new_fund;
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
            }
            throw new MyDAOException(e);
        }
    }

    public void add_fund(Fund fund) throws MyDAOException{
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO hw5_funds (fund_name, current_price) values (? , ?)");
            pstmt.setString(1, fund.getName());
            pstmt.setInt(2, fund.getPrice());

            int count = pstmt.executeUpdate();
            if (count != 1)
                throw new SQLException("Insert updated " + count + " rows");

            pstmt.close();

			releaseConnection(con);
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
            }
            throw new MyDAOException(e);
        }
    }
    public void setDeposit(DataDeposit deposit) throws MyDAOException {
        Connection con = null;

        try{
            con = getConnection();
            final PreparedStatement pstmt = con
					.prepareStatement("UPDATE hw5_customer_info SET balance=? WHERE customer_email = ? ;");

            pstmt.setString(1, deposit.getEmail());
            pstmt.setString(2, deposit.getAmount());

            int count = pstmt.executeUpdate();
            if (count != 1)
                throw new SQLException("Insert updated " + count + " rows");

            pstmt.close();

			releaseConnection(con);
        }
        catch(Exception e){
            try{
                if(con != null) con.close();
            }catch(SQLException exception){
                
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