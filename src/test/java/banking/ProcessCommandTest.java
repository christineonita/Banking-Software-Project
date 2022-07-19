package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessCommandTest {

    Bank bank;
    ProcessCommand processCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void process_create_checking() {
        processCommand.process("create checking 14305883 0.9");
        assertTrue(bank.accountExistsById("14305883"));
    }

    @Test
    void process_create_checking_and_deposit() {
        processCommand.process("create checking 14305883 0.9");
        processCommand.process("deposit 14305883 400");
        assertEquals(400, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void process_create_checking_and_deposit_then_pass_time() {
        processCommand.process("create checking 14305883 0.9");
        processCommand.process("deposit 14305883 400");
        processCommand.process("pass 4");
        assertEquals(4, bank.getAccounts().get("14305883").getMonth());
        assertEquals(401.20135067512655, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void process_create_checking_and_deposit_then_withdraw() {
        processCommand.process("create checking 14305883 0.9");
        processCommand.process("deposit 14305883 400");
        processCommand.process("withdraw 14305883 100");
        assertEquals(300, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void process_create_savings() {
        processCommand.process("create savings 14305882 0.9");
        assertTrue(bank.accountExistsById("14305882"));
    }

    @Test
    void process_create_savings_and_deposit() {
        processCommand.process("create savings 14305882 0.9");
        processCommand.process("deposit 14305882 1500");
        assertEquals(1500, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void process_create_savings_and_deposit_then_pass_time() {
        processCommand.process("create savings 14305882 0.9");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 7");
        assertEquals(7, bank.getAccounts().get("14305882").getMonth());
        assertEquals(1507.8927409150563, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void process_create_savings_and_deposit_then_withdraw() {
        processCommand.process("create savings 14305882 0.9");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("withdraw 14305882 100");
        assertEquals(1400, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void process_create_transfer_from_savings_to_checking() {
        processCommand.process("create savings 14305882 0.9");
        processCommand.process("create checking 14305883 0.9");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("transfer 14305882 14305883 100");
        assertEquals(1400, bank.getAccounts().get("14305882").getBalance());
        assertEquals(100, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void process_create_cd() {
        processCommand.process("create cd 14305884 0.9 7500");
        assertTrue(bank.accountExistsById("14305884"));
    }

    @Test
    void process_create_cd_then_pass_time() {
        processCommand.process("create cd 14305884 0.9 7500");
        processCommand.process("pass 40");
        assertEquals(8455.846055369351, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void process_create_cd_then_pass_time_then_full_withdrawal() {
        processCommand.process("create cd 14305884 0.9 7500");
        processCommand.process("pass 40");
        processCommand.process("withdraw 14305884 8455.846055369351");
        assertEquals(0, bank.getAccounts().get("14305884").getBalance());
    }
}
