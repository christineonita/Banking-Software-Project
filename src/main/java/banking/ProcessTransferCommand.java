package banking;

public class ProcessTransferCommand {

    Bank bank;

    public ProcessTransferCommand(Bank bank) {
        this.bank = bank;
    }

    public void process(String transferCommand) {
        String[] result = transferCommand.split(" ", 5);
        String amount = result[3];

        String fromId = result[1];
        String toId = result[2];

        double amountToWithdraw = Double.parseDouble(amount);

        double fromAccountBalance = bank.getAccounts().get(fromId).getBalance();

        if (amountToWithdraw >= fromAccountBalance) {
            bank.getAccounts().get(toId).deposit(fromAccountBalance);
            bank.getAccounts().get(fromId).withdraw(amountToWithdraw);
        } else {
            bank.getAccounts().get(toId).deposit(amountToWithdraw);
            bank.getAccounts().get(fromId).withdraw(amountToWithdraw);
        }
    }
}
