package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateTransferCommandTest {

    private static final String FIRST_SAVINGS_APR = "0.2";
    private static final String FIRST_CHECKING_APR = "0.3";
    private static final String SECOND_SAVINGS_APR = "0.6";
    private static final String SECOND_CHECKING_APR = "0.7";
    private static final String CD_APR = "0.4";
    private static final String CD_AMOUNT = "2000";
    Bank bank;
    ValidateCommand commandValidator;
    ProcessCommand processCommand;

    CheckingAccount firstCheckingAccount = new CheckingAccount(FIRST_CHECKING_APR);
    SavingsAccount firstSavingsAccount = new SavingsAccount(FIRST_SAVINGS_APR);

    CheckingAccount secondCheckingAccount = new CheckingAccount(SECOND_CHECKING_APR);
    SavingsAccount secondSavingsAccount = new SavingsAccount(SECOND_SAVINGS_APR);

    CDAccount cdAccount = new CDAccount(CD_APR, CD_AMOUNT);

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidateCommand(bank);
        processCommand = new ProcessCommand(bank);
        bank.addAccount("14305882", firstSavingsAccount);
        bank.addAccount("14305883", firstCheckingAccount);
        bank.addAccount("24305882", secondSavingsAccount);
        bank.addAccount("24305883", secondCheckingAccount);
        bank.addAccount("14305884", cdAccount);
    }

    @Test
    void invalid_transfer_from_account_to_itself() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305882 50.54");
        assertFalse(actual);
    }

    @Test
    void valid_transfer_command_with_decimal() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 50.54");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_checking() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_checking_where_amount_is_zero() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_checking_with_max_savings_withdraw_amount_1000() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_checking_with_max_checking_deposit_amount_1000() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 1000");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_checking_to_savings_with_max_checking_withdraw_amount_400() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 400");
        assertTrue(actual);
    }

    @Test
    void invalid_transfer_command_from_checking_to_savings_with_max_savings_deposit_amount_2500() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 2500");
        assertFalse(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_savings() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_savings_to_savings_where_amount_is_zero() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_checking_to_savings() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 250");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_checking_to_savings_where_amount_is_zero() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 0");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_checking_to_checking() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 24305883 250");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_from_checking_to_checking_where_amount_is_zero() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 24305883 0");
        assertTrue(actual);
    }

    @Test
    void transfer_command_with_typo_in_transfer() {
        boolean actual = commandValidator.validateCommand("tranfger 14305883 14305882 250");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_command_length_less_than_four() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 250");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_command_length_greater_than_four() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 250 90");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_amount_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 8gh6");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_from_account_does_not_exist() {
        boolean actual = commandValidator.validateCommand("transfer 14305887 14305883 500");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_to_account_does_not_exist() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305887 500");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_neither_accounts_exist() {
        boolean actual = commandValidator.validateCommand("transfer 14305887 14305888 500");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_from_account_is_a_cd() {
        boolean actual = commandValidator.validateCommand("transfer 14305884 14305883 500");
        assertFalse(actual);
    }

    @Test
    void transfer_command_where_to_account_is_a_cd() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305884 500");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_checking_command_with_more_amount_than_savings_can_withdraw() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 1000.01");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_checking_command_with_more_amount_than_checking_can_deposit() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 1000.01");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_checking_command_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 -250");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_savings_command_with_more_amount_than_checking_can_withdraw() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 400.01");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_savings_command_with_more_amount_than_savings_can_deposit() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 2500.01");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_savings_command_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 14305882 -250");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_savings_command_with_more_amount_than_savings_can_withdraw() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 1000.01");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_savings_command_with_more_amount_than_savings_can_deposit() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 2500.01");
        assertFalse(actual);
    }

    @Test
    void transfer_savings_to_savings_command_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 -250");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_checking_command_with_more_amount_than_checking_can_withdraw() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 24305883 400.01");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_checking_command_with_more_amount_than_checking_can_deposit() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 24305883 1000.01");
        assertFalse(actual);
    }

    @Test
    void transfer_checking_to_checking_command_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("transfer 14305883 24305883 -250");
        assertFalse(actual);
    }

    @Test
    void invalid_transfer_from_savings_to_checking_more_than_once_a_month_ie_without_passing_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 14305883 400");
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertFalse(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_after_passing_one_month() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 14305883 400");
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_after_passing_sixty_months() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 14305883 400");
        processCommand.process("pass 60");
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_after_passing_multiple_months_and_again_after_passing_multiple_months_a_second_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 14305883 400");
        processCommand.process("pass 6");
        processCommand.process("transfer 14305882 14305883 450");
        processCommand.process("pass 16");
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_checking_after_passing_one_months_and_again_after_passing_one_month_a_second_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 14305883 400");
        processCommand.process("pass 1");
        processCommand.process("transfer 14305882 14305883 450");
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("transfer 14305882 14305883 500");
        assertTrue(actual);
    }

    @Test
    void invalid_transfer_from_savings_to_savings_more_than_once_a_month_ie_without_passing_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 24305882 400");
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertFalse(actual);
    }

    @Test
    void valid_transfer_from_savings_to_savings_after_passing_one_month() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 24305882 400");
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_savings_after_passing_sixty_months() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 24305882 400");
        processCommand.process("pass 60");
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_savings_after_passing_multiple_months_and_again_after_passing_multiple_months_a_second_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 24305882 400");
        processCommand.process("pass 6");
        processCommand.process("transfer 14305882 24305882 500");
        processCommand.process("pass 17");
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_from_savings_to_savings_after_passing_one_month_and_again_after_passing_one_month_a_second_time() {
        processCommand.process("deposit 14305882 2000");
        processCommand.process("transfer 14305882 24305882 400");
        processCommand.process("pass 1");
        processCommand.process("transfer 14305882 24305882 500");
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("transfer 14305882 24305882 500");
        assertTrue(actual);
    }
}
