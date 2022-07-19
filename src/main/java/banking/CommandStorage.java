package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

    protected List<String> invalidCommands;
    protected List<String> validCommands;

    public CommandStorage() {
        invalidCommands = new ArrayList<>();
        validCommands = new ArrayList<>();
    }

    public List<String> getInvalidCommands() {
        return this.invalidCommands;
    }

    public List<String> getValidCommands() {
        return this.validCommands;
    }

    public void addInvalidCommand(String command) {
        invalidCommands.add(command);
    }

    public void addValidCommand(String command) {
        validCommands.add(command);
    }
}
