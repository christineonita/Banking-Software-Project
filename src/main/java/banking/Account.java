package banking;

abstract class Account {
    protected boolean canWithdraw;
    private double accountBalance;
    private double accountApr;
    private int monthsSinceCreation;

    Account(double accountBalance, double apr, int month) {
        this.accountBalance = accountBalance;
        this.accountApr = apr;
        this.monthsSinceCreation = month;
        this.canWithdraw = true;
    }

    public double getBalance() {
        return accountBalance;
    }

    public double getApr() {
        return accountApr;
    }

    public int getMonth() {
        return monthsSinceCreation;
    }

    public boolean getCanWithdraw() {
        return canWithdraw;
    }

    public void passTimeAddMonths(int addMonths) {
        monthsSinceCreation += addMonths;
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > accountBalance) {
            accountBalance = 0.0;
        } else {
            accountBalance -= withdrawAmount;
        }
        this.canWithdraw = false;
    }

    public void deposit(double depositAmount) {
        accountBalance += depositAmount;
    }

    public void calculateCheckingAndSavingsAprAndBalance(double apr) {
        double valueToUseTCalculateApr = (apr / 100) / 12;
        accountBalance = accountBalance + (getBalance() * (valueToUseTCalculateApr));
    }

    public void calculateCdAprAndBalance(double apr) {
        double valueToUseTCalculateApr = (apr / 100) / 12;
        accountBalance = accountBalance * Math.pow((1 + valueToUseTCalculateApr), 4);
    }
}

