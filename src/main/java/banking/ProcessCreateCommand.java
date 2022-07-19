package banking;

public class ProcessCreateCommand {

    protected Bank bank;

    public ProcessCreateCommand(Bank bank) {
        this.bank = bank;
    }

    public void process(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String accountType = result[1];
        accountType = accountType.toLowerCase();
        String id = result[2];
        String apr = result[3];

        switch (accountType) {
            case "checking":
                createCheckingAccount(id, apr);
                break;
            case "savings":
                createSavingsAccount(id, apr);
                break;
            case "cd":
                String amount = result[4];
                createCdAccount(id, apr, amount);
                break;
            default:
                break;
        }

    }

    private void createCheckingAccount(String id, String apr) {
        CheckingAccount checking = new CheckingAccount(apr);
        bank.addAccount(id, checking);
    }

    private void createSavingsAccount(String id, String apr) {
        SavingsAccount savings = new SavingsAccount(apr);
        bank.addAccount(id, savings);
    }

    private void createCdAccount(String id, String apr, String amount) {
        CDAccount cd = new CDAccount(apr, amount);
        bank.addAccount(id, cd);
    }
}
