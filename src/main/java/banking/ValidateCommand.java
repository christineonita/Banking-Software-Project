package banking;

public class ValidateCommand {

    ValidateCreateCommand validateCreateCommand;
    ValidateDepositCommand validateDepositCommand;
    ValidateWithdrawCommand validateWithdrawCommand;
    ValidateTransferCommand validateTransferCommand;
    ValidatePassTimeCommand validatePassTimeCommand;
    Bank bank;

    public ValidateCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validateCommand(String command) {
        String[] result = command.split(" ", 5);
        String commandType = result[0];
        commandType = commandType.toLowerCase();
        switch (commandType) {
            case "create":
                validateCreateCommand = new ValidateCreateCommand(bank);
                return validateCreateCommand.validate(command);
            case "deposit":
                validateDepositCommand = new ValidateDepositCommand(bank);
                return validateDepositCommand.validate(command);
            case "withdraw":
                validateWithdrawCommand = new ValidateWithdrawCommand(bank);
                return validateWithdrawCommand.validate(command);
            case "transfer":
                validateTransferCommand = new ValidateTransferCommand(bank);
                return validateTransferCommand.validate(command);
            case "pass":
                validatePassTimeCommand = new ValidatePassTimeCommand(bank);
                return validatePassTimeCommand.validate(command);
            default:
                return false;
        }
    }
}
