package banking;

public class CheckingAccount extends Account {
    private String checkingApr;

    public CheckingAccount(String checkingApr) {
        super(0, Double.parseDouble(checkingApr), 0);
        this.checkingApr = checkingApr;
    }

    public String getCheckingApr() {
        return checkingApr;
    }
}