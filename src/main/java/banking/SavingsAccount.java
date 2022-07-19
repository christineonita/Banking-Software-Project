package banking;

public class SavingsAccount extends Account {
    private String savingsApr;

    public SavingsAccount(String savingsApr) {
        super(0, Double.parseDouble(savingsApr), 0);
        this.savingsApr = savingsApr;
    }

    public String getSavingsApr() {
        return savingsApr;
    }
}
