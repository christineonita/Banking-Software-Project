package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessCreateCommandTest {

    Bank bank;
    ProcessCommand processCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void check_that_checking_account_exists_after_creation() {
        processCommand.process("create checking 14305883 0.6");
        assertTrue(bank.accountExistsById("14305883"));
    }

    @Test
    void check_that_checking_account_balance_is_zero_after_creation() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void check_that_checking_account_apr_is_correct_after_creation() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0.6, bank.getAccounts().get("14305883").getApr());
    }

    @Test
    void check_that_savings_account_exists_after_creation() {
        processCommand.process("create savings 14305882 0.2");
        assertTrue(bank.accountExistsById("14305882"));
    }

    @Test
    void check_that_savings_account_balance_is_zero_after_creation() {
        processCommand.process("create savings 14305882 0.2");
        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void check_that_savings_account_apr_is_correct_after_creation() {
        processCommand.process("create savings 14305882 0.2");
        assertEquals(0.2, bank.getAccounts().get("14305882").getApr());
    }

    @Test
    void check_that_cd_account_exists_after_creation() {
        processCommand.process("create cd 14305884 0.3 2500");
        assertTrue(bank.accountExistsById("14305884"));
    }

    @Test
    void check_that_cd_account_balance_is_correct_after_creation() {
        processCommand.process("create cd 14305884 0.3 2500");
        assertEquals(2500, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void check_that_cd_account_apr_is_correct_after_creation() {
        processCommand.process("create cd 14305884 0.3 2500");
        assertEquals(0.3, bank.getAccounts().get("14305884").getApr());
    }

    @Test
    void create_two_checking_accounts_and_check_if_they_exist() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create checking 24305883 0.7");
        assertTrue(bank.accountExistsById("14305883"));
        assertTrue(bank.accountExistsById("24305883"));
    }

    @Test
    void create_two_checking_accounts_and_check_if_the_balances_are_both_zero() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        processCommand.process("create checking 24305883 0.7");
        assertEquals(0, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void create_two_checking_accounts_and_check_if_the_aprs_are_both_correct() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0.6, bank.getAccounts().get("14305883").getApr());
        processCommand.process("create checking 24305883 0.7");
        assertEquals(0.7, bank.getAccounts().get("24305883").getApr());
    }

    @Test
    void create_two_savings_accounts_and_check_if_they_exist() {
        processCommand.process("create savings 14305882 0.2");
        processCommand.process("create savings 24305882 0.1");
        assertTrue(bank.accountExistsById("14305882"));
        assertTrue(bank.accountExistsById("24305882"));
    }

    @Test
    void create_two_savings_accounts_and_check_if_the_balances_are_both_zero() {
        processCommand.process("create savings 14305882 0.2");
        assertEquals(0, bank.getAccounts().get("14305882").getBalance());
        processCommand.process("create savings 24305882 0.1");
        assertEquals(0, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void create_two_savings_accounts_and_check_if_the_aprs_are_both_correct() {
        processCommand.process("create savings 14305882 0.2");
        assertEquals(0.2, bank.getAccounts().get("14305882").getApr());
        processCommand.process("create savings 24305882 0.1");
        assertEquals(0.1, bank.getAccounts().get("24305882").getApr());
    }

    @Test
    void create_two_cd_accounts_and_check_if_they_exist() {
        processCommand.process("create cd 14305884 0.3 2500");
        processCommand.process("create cd 24305884 0.9 4000");
        assertTrue(bank.accountExistsById("14305884"));
        assertTrue(bank.accountExistsById("24305884"));
    }

    @Test
    void create_two_cd_accounts_and_check_if_both_aprs_are_correct() {
        processCommand.process("create cd 14305884 0.3 2500");
        assertEquals(0.3, bank.getAccounts().get("14305884").getApr());
        processCommand.process("create cd 24305884 0.9 4000");
        assertEquals(0.9, bank.getAccounts().get("24305884").getApr());
    }

    @Test
    void create_two_cd_accounts_and_check_if_both_balances_are_correct() {
        processCommand.process("create cd 14305884 0.3 2500");
        assertEquals(2500, bank.getAccounts().get("14305884").getBalance());
        processCommand.process("create cd 24305884 0.9 4000");
        assertEquals(4000, bank.getAccounts().get("24305884").getBalance());
    }

    @Test
    void create_one_checking_account_and_one_savings_account_then_check_if_they_exist() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create savings 24305882 0.1");
        assertTrue(bank.accountExistsById("14305883"));
        assertTrue(bank.accountExistsById("24305882"));
    }

    @Test
    void create_one_checking_account_and_one_savings_account_then_check_if_both_balances_are_zero() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0, bank.getAccounts().get("14305883").getBalance());
        processCommand.process("create savings 24305882 0.1");
        assertEquals(0, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void create_one_checking_account_and_one_savings_account_then_check_if_the_aprs_are_both_correct() {
        processCommand.process("create checking 14305883 0.6");
        assertEquals(0.6, bank.getAccounts().get("14305883").getApr());
        processCommand.process("create savings 24305882 0.1");
        assertEquals(0.1, bank.getAccounts().get("24305882").getApr());
    }
}