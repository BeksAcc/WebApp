package databean;

public class Customer {
    private String customer_email;
    private String address;
    private int balance = 0;
    private String name;
    private String password;

    public Customer(String customer_email, String password){
        this.customer_email = customer_email;
        this.password = password;
    }

    public Customer(String customer_email, String address, int balance, String name) {
        this.balance = balance;
        this.customer_email = customer_email;
        this.name = name;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(String customer_email, String address, String password, String name , int balance) {
        this.setPassword(password);
        this.customer_email = customer_email;
        this.name = name;
        this.address=  address;
        this.balance = balance;
    }

    public Customer(String customer_email, String address, String password, String name) {
        this.setPassword(password);
        this.customer_email = customer_email;
        this.name = name;
        this.address=  address;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    
}