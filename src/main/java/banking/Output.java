package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Output {

    Bank bank;
    CommandStorage commandStorage;
    private List<String> output = new ArrayList<>();

    public Output(Bank bank, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandStorage = commandStorage;
    }

    public List<String> stateHistoryAndInvalidCommands() {
        for (Map.Entry<String, Account> e : bank.getAccountsInInsertedOrder().entrySet()) {
            String id = e.getKey();
            Account account = e.getValue();
            if (account instanceof CheckingAccount) {
                output.add("Checking " + id + " " + truncateToTwoDecimalPlaces(account.getBalance()) + " " + truncateToTwoDecimalPlaces(account.getApr()));
                addHistory(id, output);
            } else if (account instanceof SavingsAccount) {
                output.add("Savings " + id + " " + truncateToTwoDecimalPlaces(account.getBalance()) + " " + truncateToTwoDecimalPlaces(account.getApr()));
                addHistory(id, output);
            } else {
                output.add("Cd " + id + " " + truncateToTwoDecimalPlaces(account.getBalance()) + " " + truncateToTwoDecimalPlaces(account.getApr()));
                addHistory(id, output);
            }
        }
        addInvalidCommandsToOutput(output);
        return output;
    }

    private void addInvalidCommandsToOutput(List<String> listToUpdate) {
        listToUpdate.addAll(commandStorage.getInvalidCommands());
    }

    private void addHistory(String id, List<String> listToUpdate) {
        for (String validCommand : commandStorage.getValidCommands()) {
            String validCommandToLowerCase = validCommand.toLowerCase();
            if (!validCommandToLowerCase.contains("create") && validCommandToLowerCase.contains(id)) {
                listToUpdate.add(validCommand);
            }
        }
    }

    private String truncateToTwoDecimalPlaces(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(number);
    }
}
