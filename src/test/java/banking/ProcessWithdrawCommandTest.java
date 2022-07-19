package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessWithdrawCommandTest {

    Bank bank;
    ProcessCommand processCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void withdraw_whole_number_once_from_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 150");
        processCommand.process("withdraw 14305883 125");
        assertEquals(25, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void withdraw_more_than_balance_from_checking_account_no_overdraft() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 190");
        processCommand.process("withdraw 14305883 200");
        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void withdraw_amount_with_decimal_once_from_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 300");
        processCommand.process("withdraw 14305883 25.07");
        assertEquals(274.93, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void withdraw_twice_from_the_same_checking_account() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 125");
        processCommand.process("withdraw 14305883 20");
        processCommand.process("withdraw 14305883 25");
        assertEquals(80, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void withdraw_whole_number_once_from_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 209");
        processCommand.process("withdraw 14305882 125");
        assertEquals(84, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void withdraw_more_than_balance_from_savings_account_no_overdraft() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 142");
        processCommand.process("withdraw 14305882 160");
        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void withdraw_amount_with_decimal_once_from_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 50");
        processCommand.process("withdraw 14305882 9.74");
        assertEquals(40.26, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void withdraw_twice_from_the_same_savings_account() {
        processCommand.process("create savings 14305882 0.8");
        processCommand.process("deposit 14305882 800.");
        processCommand.process("withdraw 14305882 100");
        processCommand.process("withdraw 14305882 10");
        assertEquals(690, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void withdraw_exactly_balance_from_the_cd_account() {
        processCommand.process("create cd 14305884 0.8 2000");
        processCommand.process("withdraw 14305884 2000");
        assertEquals(0, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void withdraw_more_than_balance_from_the_cd_account_no_overdraft() {
        processCommand.process("create cd 14305884 0.8 2000");
        processCommand.process("withdraw 14305884 2500");
        assertEquals(0, bank.getAccounts().get("14305884").getBalance());
    }
}
