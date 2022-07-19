package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessTransferCommandTest {

    ProcessCommand processCommand;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void transfer_whole_number_amount_from_savings_to_checking() {
        processCommand.process("create savings 14305882 0.6");
        processCommand.process("deposit 14305882 2000");
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("transfer 14305882 14305883 150");
        assertEquals(1850, bank.getAccounts().get("14305882").getBalance());
        assertEquals(150, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void transfer_amount_with_decimal_from_savings_to_checking() {
        processCommand.process("create savings 14305882 0.6");
        processCommand.process("deposit 14305882 2000");
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("transfer 14305882 14305883 250.57");
        assertEquals(1749.43, bank.getAccounts().get("14305882").getBalance());
        assertEquals(250.57, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void transfer_whole_number_amount_from_savings_to_savings() {
        processCommand.process("create savings 14305882 0.6");
        processCommand.process("deposit 14305882 2000");
        processCommand.process("create savings 24305882 0.5");
        processCommand.process("transfer 14305882 24305882 950");
        assertEquals(1050, bank.getAccounts().get("14305882").getBalance());
        assertEquals(950, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void transfer_amount_with_decimal_from_savings_to_savings() {
        processCommand.process("create savings 14305882 0.6");
        processCommand.process("deposit 14305882 2000");
        processCommand.process("create savings 24305882 0.5");
        processCommand.process("transfer 14305882 24305882 756.67");
        assertEquals(1243.33, bank.getAccounts().get("14305882").getBalance());
        assertEquals(756.67, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void transfer_whole_number_amount_from_checking_to_savings() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 950");
        processCommand.process("create savings 14305882 0.3");
        processCommand.process("transfer 14305883 14305882 250");
        assertEquals(700, bank.getAccounts().get("14305883").getBalance());
        assertEquals(250, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void transfer_amount_with_decimal_from_checking_to_savings() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 950");
        processCommand.process("create savings 14305882 0.3");
        processCommand.process("transfer 14305883 14305882 27.54");
        assertEquals(922.46, bank.getAccounts().get("14305883").getBalance());
        assertEquals(27.54, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void transfer_whole_number_amount_from_checking_to_checking() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 950");
        processCommand.process("create checking 24305883 0.2");
        processCommand.process("transfer 14305883 24305883 200");
        assertEquals(750, bank.getAccounts().get("14305883").getBalance());
        assertEquals(200, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void transfer_amount_with_decimal_from_checking_to_checking() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 950");
        processCommand.process("create checking 24305883 0.2");
        processCommand.process("transfer 14305883 24305883 170.23");
        assertEquals(779.77, bank.getAccounts().get("14305883").getBalance());
        assertEquals(170.23, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void transfer_amount_more_than_balance_from_checking_and_deposit_correct_amount_in_target_checking_account() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 350");
        processCommand.process("create checking 24305883 0.2");

        processCommand.process("transfer 14305883 24305883 400");

        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        assertEquals(350, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void transfer_exactly_balance_from_checking_and_deposit_correct_amount_in_target_checking_account() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 50");
        processCommand.process("create checking 24305883 0.2");

        processCommand.process("transfer 14305883 24305883 50");

        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        assertEquals(50, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void transfer_amount_more_than_balance_from_savings_and_deposit_correct_amount_in_target_savings_account() {
        processCommand.process("create savings 14305882 0.4");
        processCommand.process("deposit 14305882 950");
        processCommand.process("create savings 24305882 0.2");

        processCommand.process("transfer 14305882 24305882 1000");

        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
        assertEquals(950, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void transfer_exactly_balance_from_savings_and_deposit_correct_amount_in_target_savings_account() {
        processCommand.process("create savings 14305882 0.4");
        processCommand.process("deposit 14305882 60");
        processCommand.process("create savings 24305882 0.2");

        processCommand.process("transfer 14305882 24305882 60");

        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
        assertEquals(60, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void transfer_amount_more_than_balance_from_checking_and_deposit_correct_amount_in_target_savings_account() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 250");
        processCommand.process("create savings 14305882 0.2");

        processCommand.process("transfer 14305883 14305882 300");

        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        assertEquals(250, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void transfer_exactly_balance_from_checking_and_deposit_correct_amount_in_target_savings_account() {
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305883 240");
        processCommand.process("create savings 14305882 0.2");

        processCommand.process("transfer 14305883 14305882 240");

        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        assertEquals(240, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void transfer_amount_more_than_balance_from_savings_and_deposit_correct_amount_in_target_checking_account() {
        processCommand.process("create savings 14305882 0.2");
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305882 850");

        processCommand.process("transfer 14305882 14305883 1000");

        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
        assertEquals(850, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void transfer_exactly_balance_from_savings_and_deposit_correct_amount_in_target_checking_account() {
        processCommand.process("create savings 14305882 0.2");
        processCommand.process("create checking 14305883 0.4");
        processCommand.process("deposit 14305882 150");

        processCommand.process("transfer 14305882 14305883 150");

        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
        assertEquals(150, bank.getAccounts().get("14305883").getBalance());
    }
}
