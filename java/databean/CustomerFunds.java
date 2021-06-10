package databean;

public class CustomerFunds {
    private int id;
    private String customer_email;
    private String fund_name;
    private int number_of_shares;
    private int total_value;
    private int price;

    public CustomerFunds(int id, String customer_email, String fund_name, int number_of_shares, int total_value) {
        this.setId(id);
        this.setCustomer_email(customer_email);
        this.setFund_name(fund_name);
        this.setNumber_of_shares(number_of_shares);
        this.setTotal_value(total_value);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public int getTotal_value() {
        return total_value;
    }

    public void setTotal_value(int total_value) {
        this.total_value = total_value;
    }

    public int getNumber_of_shares() {
        return number_of_shares;
    }

    public void setNumber_of_shares(int number_of_shares) {
        this.number_of_shares = number_of_shares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

    
}