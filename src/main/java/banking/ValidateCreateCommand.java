package banking;

public class ValidateCreateCommand {

    Bank bank;


    public ValidateCreateCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] result = command.split(" ", 5);
        if (result.length == 1) {
            return false;
        } else {
            String accountType = result[1];
            accountType = accountType.toLowerCase();
            switch (accountType) {
                case "checking":
                case "savings":
                    return validateCreateCheckingOrSavingsCommandLength(command) && validateCreateCheckingOrSavings(command);
                case "cd":
                    return validateCreateCdCommandLength(command) && validateCreateCD(command);
                default:
                    return false;
            }
        }
    }

    private boolean validateIdDoesNotAlreadyExist(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String id = result[2];
        return bank.getAccounts().get(id) == null;
    }

    private boolean validateIdLength(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String id = result[2];
        return id.length() == 8;
    }

    private boolean validateIdIsANumber(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String id = result[2];
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateAPrValue(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        double apr = Double.parseDouble(result[3]);
        return !(apr < 0) && !(apr > 10);
    }

    private boolean validateCreateCheckingOrSavingsCommandLength(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        return result.length == 4;
    }

    private boolean validateCreateCdCommandLength(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        return result.length == 5;
    }

    private boolean validateCdAmountValue(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        double amount = Double.parseDouble(result[4]);
        return !(amount < 1000) && !(amount > 10000);
    }

    private boolean validateCdAmountIsANumber(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String amount = result[4];
        try {
            Double.parseDouble(amount);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateAprIsANumber(String createCommand) {
        String[] result = createCommand.split(" ", 5);
        String apr = result[3];
        try {
            Double.parseDouble(apr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCreateCheckingOrSavings(String command) {
        return validateIdDoesNotAlreadyExist(command)
                && validateIdLength(command)
                && validateIdIsANumber(command)
                && validateAprIsANumber(command)
                && validateAPrValue(command);
    }

    private boolean validateCreateCD(String command) {
        return validateIdDoesNotAlreadyExist(command)
                && validateIdLength(command)
                && validateIdIsANumber(command)
                && validateAprIsANumber(command)
                && validateAPrValue(command)
                && validateCdAmountIsANumber(command)
                && validateCdAmountValue(command);
    }
}
