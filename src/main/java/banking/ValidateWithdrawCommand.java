package banking;

public class ValidateWithdrawCommand {

    Bank bank;

    public ValidateWithdrawCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        return validateWithdrawCommandLength(command)
                && validateCanWithdraw(command)
                && validateWithdrawAmountIsANumber(command)
                && validateWithdrawAmount(command);
    }

    private boolean validateWithdrawAmountIsANumber(String withdrawCommand) {
        String[] result = withdrawCommand.split(" ", 3);
        String withdrawAmount = result[2];
        try {
            Double.parseDouble(withdrawAmount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCanWithdraw(String command) {
        String[] result = command.split(" ", 3);
        String id = result[1];
        if (isAccountChecking(id)) {
            return true;
        } else if (isAccountSavings(id)) {
            return bank.getAccounts().get(id).getCanWithdraw();
        } else if (isAccountCd(id)) {
            return bank.getAccounts().get(id).getMonth() >= 12;
        } else {
            return false;
        }
    }

    private boolean validateWithdrawAmount(String command) {
        String[] result = command.split(" ", 3);
        String id = result[1];
        if (isAccountChecking(id)) {
            return validateWithdrawCheckingAmountValue(command);
        } else if (isAccountSavings(id)) {
            return validateWithdrawSavingsAmountValue(command);
        } else {
            return validateWithdrawCdAmountValue(command);
        }
    }

    private boolean validateWithdrawCdAmountValue(String command) {
        String[] result = command.split(" ", 3);
        String id = result[1];
        double cdWithdrawAmount = Double.parseDouble(result[2]);
        double cdBalance = bank.getAccounts().get(id).getBalance();
        return cdWithdrawAmount >= cdBalance;
    }

    private boolean validateWithdrawSavingsAmountValue(String depositCommand) {
        String[] result = depositCommand.split(" ", 3);
        double savingsWithdrawAmount = Double.parseDouble(result[2]);
        return !(savingsWithdrawAmount < 0) && !(savingsWithdrawAmount > 1000);
    }

    private boolean validateWithdrawCheckingAmountValue(String depositCommand) {
        String[] result = depositCommand.split(" ", 3);
        double checkingWithdrawAmount = Double.parseDouble(result[2]);
        return !(checkingWithdrawAmount < 0) && !(checkingWithdrawAmount > 400);
    }

    private boolean isAccountChecking(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof CheckingAccount);
    }

    private boolean isAccountSavings(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof SavingsAccount);
    }

    private boolean isAccountCd(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof CDAccount);
    }

    private boolean validateWithdrawCommandLength(String command) {
        String[] result = command.split(" ", 4);
        return result.length == 3;
    }
}
