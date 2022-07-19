package banking;

public class ValidateTransferCommand {

    Bank bank;

    public ValidateTransferCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        return validateTransferCommandLength(command)
                && validateTransferIdsNotTheSame(command)
                && validateTransferAmountFormat(command)
                && validateFromAccountCanWithdraw(command)
                && validateWithdrawAmount(command)
                && validateDepositAmount(command);
    }

    private boolean validateFromAccountCanWithdraw(String command) {
        String[] result = command.split(" ", 5);
        String fromId = result[1];

        if (isAccountChecking(fromId)) {
            return true;
        } else if (isAccountSavings(fromId)) {
            return bank.getAccounts().get(fromId).getCanWithdraw();
        } else {
            return false;
        }
    }

    private boolean validateTransferIdsNotTheSame(String command) {
        String[] result = command.split(" ", 5);
        String withdrawId = result[1];
        String depositId = result[2];

        return !withdrawId.equals(depositId);

    }

    private boolean validateTransferAmountFormat(String command) {
        String[] result = command.split(" ", 5);
        String amount = result[3];

        try {
            Double.parseDouble(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private boolean validateDepositAmount(String command) {
        String[] result = command.split(" ", 5);
        String depositId = result[2];

        return isAccountChecking(depositId) || isAccountSavings(depositId);
    }

    private boolean validateWithdrawAmount(String command) {
        String[] result = command.split(" ", 5);
        String fromId = result[1];

        if (isAccountChecking(fromId)) {
            double amount = Double.parseDouble(result[3]);
            return !(amount < 0) && !(amount > 400);
        } else {
            double amount = Double.parseDouble(result[3]);
            return !(amount < 0) && !(amount > 1000);
        }
    }

    private boolean isAccountSavings(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof SavingsAccount);
    }

    private boolean isAccountChecking(String id) {
        Account account = bank.getAccounts().get(id);
        return (account instanceof CheckingAccount);
    }

    private boolean validateTransferCommandLength(String command) {
        String[] result = command.split(" ", 5);
        return result.length == 4;
    }
}
