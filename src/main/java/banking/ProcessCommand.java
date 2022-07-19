package banking;

public class ProcessCommand {

    ProcessCreateCommand processCreateCommand;
    ProcessDepositCommand processDepositCommand;
    ProcessWithdrawCommand processWithdrawCommand;
    ProcessTransferCommand processTransferCommand;
    ProcessPassTimeCommand processPassTimeCommand;
    private Bank bank;

    public ProcessCommand(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] result = command.split(" ", 5);
        String commandType = result[0];
        commandType = commandType.toLowerCase();

        switch (commandType) {
            case "create":
                processCreateCommand = new ProcessCreateCommand(bank);
                processCreateCommand.process(command);
                break;
            case "deposit":
                processDepositCommand = new ProcessDepositCommand(bank);
                processDepositCommand.process(command);
                break;
            case "withdraw":
                processWithdrawCommand = new ProcessWithdrawCommand(bank);
                processWithdrawCommand.process(command);
                break;
            case "transfer":
                processTransferCommand = new ProcessTransferCommand(bank);
                processTransferCommand.process(command);
                break;
            case "pass":
                processPassTimeCommand = new ProcessPassTimeCommand(bank);
                processPassTimeCommand.process(command);
                break;
            default:
                break;
        }
    }
}
