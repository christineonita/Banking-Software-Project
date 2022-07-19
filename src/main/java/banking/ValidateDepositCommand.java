package banking;

public class ValidateDepositCommand {

    Bank bank;

    public ValidateDepositCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        return validateDepositCommandLength(command)
                && validateDepositAmountIsANumber(command)
                && validateDepositAmount(command);
    }

    private boolean validateDepositAmountIsANumber(String depositCommand) {
        String[] result = depositCommand.split(" ", 3);
        String depositAmount = result[2];
        try {
            Double.parseDouble(depositAmount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateDepositAmount(String command) {
        String[] result = command.split(" ", 3);
        String id = result[1];
        if (isAccountChecking(id)) {
            return validateDepositCheckingAmountValue(command);
        } else if (isAccountSavings(id)) {
            return validateDepositSavingsAmountValue(command);
        } else {
            return false;
        }
    }

    private boolean isAccountChecking(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof CheckingAccount);
    }

    private boolean isAccountSavings(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof SavingsAccount);
    }

    private boolean validateDepositCommandLength(String depositCommand) {
        String[] result = depositCommand.split(" ", 4);
        return result.length == 3;
    }

    private boolean validateDepositSavingsAmountValue(String depositCommand) {
        String[] result = depositCommand.split(" ", 3);
        double savingsAmount = Double.parseDouble(result[2]);
        return !(savingsAmount < 0) && !(savingsAmount > 2500);
    }

    private boolean validateDepositCheckingAmountValue(String depositCommand) {
        String[] result = depositCommand.split(" ", 3);
        double checkingAmount = Double.parseDouble(result[2]);
        return !(checkingAmount < 0) && !(checkingAmount > 1000);
    }
}
