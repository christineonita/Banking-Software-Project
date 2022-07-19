package banking;

public class ProcessDepositCommand {

    private Bank bank;

    public ProcessDepositCommand(Bank bank) {
        this.bank = bank;
    }


    public void process(String depositCommand) {
        String[] result = depositCommand.split(" ", 5);
        String amount = result[2];

        String id = result[1];

        bank.getAccounts().get(id).deposit(Double.parseDouble(amount));
    }
}
