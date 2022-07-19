package banking;

public class ValidatePassTimeCommand {

    Bank bank;

    public ValidatePassTimeCommand(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        return validatePassTimeCommandLength(command)
                && validateMonthIsInteger(command)
                && validateMonthValue(command);
    }

    private boolean validateMonthValue(String passTimeCommand) {
        String[] result = passTimeCommand.split(" ", 4);
        String months = result[1];

        int numOfMonthsToPass = Integer.parseInt(months);

        return !(numOfMonthsToPass < 1) && !(numOfMonthsToPass > 60);
    }

    private boolean validateMonthIsInteger(String passTimeCommand) {
        String[] result = passTimeCommand.split(" ", 4);
        String months = result[1];
        try {
            Integer.parseInt(months);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validatePassTimeCommandLength(String passTimeCommand) {
        String[] result = passTimeCommand.split(" ", 4);
        return result.length == 2;
    }
}
