package banking;

import java.util.Map;

public class ProcessPassTimeCommand {

    Bank bank;

    public ProcessPassTimeCommand(Bank bank) {
        this.bank = bank;
    }

    public void process(String passTimeCommand) {
        String[] result = passTimeCommand.split(" ", 4);
        String month = result[1];

        int numOfMonthsToPass = Integer.parseInt(month);

        for (Map.Entry<String, Account> e : bank.getAccounts().entrySet()) {
            Account value = e.getValue();
            value.passTimeAddMonths(numOfMonthsToPass);
        }

        for (int i = 1; i <= numOfMonthsToPass; i++) {
            for (Map.Entry<String, Account> entry : bank.getAccounts().entrySet()) {
                String id = entry.getKey();
                Account account = entry.getValue();
                if (account.getBalance() == 0) {
                    bank.closeAccount(id);
                }
                if (account.getBalance() < 100) {
                    account.withdraw(25);
                }
                if ((account instanceof CheckingAccount)) {
                    account.calculateCheckingAndSavingsAprAndBalance(account.getApr());
                } else if (account instanceof SavingsAccount) {
                    account.calculateCheckingAndSavingsAprAndBalance(account.getApr());
                    account.canWithdraw = true;
                } else {
                    account.calculateCdAprAndBalance(account.getApr());
                }
            }
        }
    }
}
