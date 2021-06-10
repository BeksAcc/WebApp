package databean;

public class DataDeposit {
    private String amount;
    private String email;

    public DataDeposit(String amount, String email) {
        this.setAmount(amount);
        this.setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
}