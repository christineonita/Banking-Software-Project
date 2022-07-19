package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateDepositCommandTest {

    private static final String FIRST_SAVINGS_APR = "0.2";
    private static final String FIRST_CHECKING_APR = "0.3";
    private static final String FIRST_CD_APR = "0.7";
    private static final String FIRST_CD_AMOUNT = "2000";

    CheckingAccount firstCheckingAccount = new CheckingAccount(FIRST_CHECKING_APR);
    SavingsAccount firstSavingsAccount = new SavingsAccount(FIRST_SAVINGS_APR);
    CDAccount firstCdAccount = new CDAccount(FIRST_CD_APR, FIRST_CD_AMOUNT);

    ValidateCommand commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidateCommand(bank);
        bank.addAccount("14305882", firstSavingsAccount);
        bank.addAccount("14305883", firstCheckingAccount);
        bank.addAccount("14305884", firstCdAccount);
    }

    @Test
    void invalid_deposit_command_with_no_arguments() {
        boolean actual = commandValidator.validateCommand("deposit");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_command_with_more_than_two_arguments() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 125 88");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_command_with_amount_not_a_number() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 r2d2");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_command_when_account_does_not_exist() {
        boolean actual = commandValidator.validateCommand("deposit 14305887 125");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_into_cd_account() {
        boolean actual = commandValidator.validateCommand("deposit 14305884 500");
        assertFalse(actual);
    }

    @Test
    void valid_deposit_command_where_bank_contains_savings_account() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 700");
        assertTrue(actual);
    }

    @Test
    void typo_in_word_deposit_for_savings() {
        boolean actual = commandValidator.validateCommand("depos1t 14305882 700");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_savings_amount_not_a_number() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 7p0");
        assertFalse(actual);
    }

    @Test
    void exhibit_deposit_savings_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("DePosiT 14305882 700");
        assertTrue(actual);
    }

    @Test
    void deposit_savings_command_length_not_3() {
        boolean actual = commandValidator.validateCommand("deposit 14305882");
        assertFalse(actual);
    }

    @Test
    void deposit_savings_amount_greater_than_2500() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 2500.01");
        assertFalse(actual);
    }

    @Test
    void deposit_savings_amount_equal_to_2500() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 2500");
        assertTrue(actual);
    }

    @Test
    void deposit_savings_amount_equal_to_0() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 0");
        assertTrue(actual);
    }

    @Test
    void deposit_savings_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("deposit 14305882 -2400");
        assertFalse(actual);
    }

    @Test
    void valid_deposit_command_where_bank_contains_checking_account() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 700");
        assertTrue(actual);
    }

    @Test
    void typo_in_word_deposit_for_checking() {
        boolean actual = commandValidator.validateCommand("depos1t 14305883 700");
        assertFalse(actual);
    }

    @Test
    void invalid_deposit_checking_amount_not_a_number() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 1#");
        assertFalse(actual);
    }

    @Test
    void exhibit_deposit_checking_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("DePosiT 14305883 700");
        assertTrue(actual);
    }

    @Test
    void deposit_checking_command_length_not_3() {
        boolean actual = commandValidator.validateCommand("deposit 14305883");
        assertFalse(actual);
    }

    @Test
    void deposit_checking_amount_greater_than_1000() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 1000.01");
        assertFalse(actual);
    }

    @Test
    void deposit_checking_amount_equal_to_1000() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 1000");
        assertTrue(actual);
    }

    @Test
    void deposit_checking_amount_equal_to_0() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 0");
        assertTrue(actual);
    }

    @Test
    void deposit_checking_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("deposit 14305883 -900");
        assertFalse(actual);
    }
}