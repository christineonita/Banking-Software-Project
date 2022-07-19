package banking;

import java.util.List;

public class MasterControl {

    Bank bank;
    ValidateCommand validateCommand;
    ProcessCommand processCommand;
    CommandStorage commandStorage;
    Output output;


    public MasterControl(Bank bank, ValidateCommand validateCommand, ProcessCommand processCommand, CommandStorage commandStorage) {
        this.bank = bank;
        this.validateCommand = validateCommand;
        this.processCommand = processCommand;
        this.commandStorage = commandStorage;
        this.output = new Output(bank, commandStorage);
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (validateCommand.validateCommand(command)) {
                commandStorage.addValidCommand(command);
                processCommand.process(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return output.stateHistoryAndInvalidCommands();
    }
}
