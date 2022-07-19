package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandStorageTest {

    CommandStorage commandStorage;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage();
    }

    @Test
    void check_that_invalid_command_storage_is_empty_initially() {
        assertTrue(commandStorage.invalidCommands.isEmpty());
    }

    @Test
    void store_one_invalid_command() {
        String command = "create checking 1430588 0.6";
        commandStorage.addInvalidCommand(command);
        assertEquals(Arrays.asList(command), commandStorage.getInvalidCommands());
    }

    @Test
    void store_two_invalid_commands() {
        String commandOne = "create checking 1430588 0.6";
        String commandTwo = "deposit 14305882 -2400";
        commandStorage.addInvalidCommand(commandOne);
        commandStorage.addInvalidCommand(commandTwo);
        assertEquals(Arrays.asList(commandOne, commandTwo), commandStorage.getInvalidCommands());
    }

    @Test
    void store_one_valid_command() {
        String command = "create checking 14305883 0.6";
        commandStorage.addValidCommand(command);
        assertEquals(Arrays.asList(command), commandStorage.getValidCommands());
    }

    @Test
    void store_two_valid_commands() {
        String commandOne = "create checking 14305883 0.6";
        String commandTwo = "deposit 14305883 300";
        commandStorage.addValidCommand(commandOne);
        commandStorage.addValidCommand(commandTwo);
        assertEquals(Arrays.asList(commandOne, commandTwo), commandStorage.getValidCommands());
    }
}
