package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessDepositCommandTest {

    Bank bank;
    ProcessCommand processCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void deposit_whole_number_once_in_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 125");
        assertEquals(125, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void deposit_amount_with_decimal_once_in_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 265.987");
        assertEquals(265.987, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void deposit_twice_in_the_same_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 125");
        processCommand.process("deposit 14305883 200.43");
        assertEquals(325.43, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void deposit_whole_number_once_in_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 209");
        assertEquals(209, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void deposit_amount_with_decimal_once_in_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 49.76334");
        assertEquals(49.76334, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void deposit_twice_in_the_same_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 20.769");
        processCommand.process("deposit 14305882 101");
        assertEquals(121.769, bank.getAccounts().get("14305882").getBalance());
    }
}
