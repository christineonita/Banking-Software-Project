package banking;

public class ProcessWithdrawCommand {

    public double balance;
    Bank bank;

    public ProcessWithdrawCommand(Bank bank) {
        this.bank = bank;
    }

    public void process(String withdrawCommand) {
        String[] result = withdrawCommand.split(" ", 5);
        String amount = result[2];
        String id = result[1];

        bank.getAccounts().get(id).withdraw(Double.parseDouble(amount));
        balance = bank.getAccounts().get(id).getBalance();
    }
}
