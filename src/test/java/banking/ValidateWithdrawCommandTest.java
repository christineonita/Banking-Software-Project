package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateWithdrawCommandTest {
    private static final String FIRST_SAVINGS_APR = "0.2";
    private static final String FIRST_CHECKING_APR = "0.3";
    private static final String FIRST_CD_APR = "0.7";
    private static final String FIRST_CD_AMOUNT = "2000";

    CheckingAccount firstCheckingAccount = new CheckingAccount(FIRST_CHECKING_APR);
    SavingsAccount firstSavingsAccount = new SavingsAccount(FIRST_SAVINGS_APR);
    CDAccount firstCdAccount = new CDAccount(FIRST_CD_APR, FIRST_CD_AMOUNT);

    ValidateCommand commandValidator;
    ProcessCommand processCommand;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidateCommand(bank);
        processCommand = new ProcessCommand(bank);
        bank.addAccount("14305882", firstSavingsAccount);
        bank.addAccount("14305883", firstCheckingAccount);
        bank.addAccount("14305884", firstCdAccount);
    }

    @Test
    void invalid_withdraw_command_with_no_arguments() {
        boolean actual = commandValidator.validateCommand("withdraw");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_command_with_more_than_two_arguments() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 125 88");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_command_with_amount_not_a_number() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 r2d2");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_command_when_account_does_not_exist() {
        boolean actual = commandValidator.validateCommand("withdraw 14305887 125");
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_command_where_bank_contains_savings_account() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 700");
        assertTrue(actual);
    }

    @Test
    void typo_in_word_withdraw_for_savings() {
        boolean actual = commandValidator.validateCommand("w!thdr4w 14305882 700");
        assertFalse(actual);
    }

    @Test
    void exhibit_withdraw_savings_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("wiThDRaW 14305882 700");
        assertTrue(actual);
    }

    @Test
    void withdraw_savings_amount_greater_than_1000() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 1000.01");
        assertFalse(actual);
    }

    @Test
    void withdraw_savings_amount_equal_to_1000() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 1000");
        assertTrue(actual);
    }

    @Test
    void withdraw_savings_amount_equal_to_0() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_savings_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("withdraw 14305882 -2400");
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_command_where_bank_contains_checking_account() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 250");
        assertTrue(actual);
    }

    @Test
    void typo_in_word_withdraw_for_checking() {
        boolean actual = commandValidator.validateCommand("w!thdr4w 14305883 250");
        assertFalse(actual);
    }

    @Test
    void exhibit_withdraw_checking_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("wiThDRaW 14305883 250");
        assertTrue(actual);
    }

    @Test
    void withdraw_checking_amount_greater_than_400() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 400.01");
        assertFalse(actual);
    }

    @Test
    void withdraw_checking_amount_equal_to_400() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 400");
        assertTrue(actual);
    }

    @Test
    void withdraw_checking_amount_equal_to_0() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_checking_with_negative_amount() {
        boolean actual = commandValidator.validateCommand("withdraw 14305883 -2400");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_from_cd_after_no_time_has_passed() {
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2000");
        assertFalse(actual);
    }

    @Test
    void withdraw_from_cd_after_only_one_month_has_passed() {
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2004.6707515881947");
        assertFalse(actual);
    }

    @Test
    void withdraw_from_cd_after_only_eleven_months_have_passed() {
        processCommand.process("pass 11");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2051.982428218461");
        assertFalse(actual);
    }

    @Test
    void typo_in_withdraw_from_cd_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("wiyhd5aw 14305884 2056.7745783112355");
        assertFalse(actual);
    }

    @Test
    void typo_in_withdraw_from_cd_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 14");
        boolean actual = commandValidator.validateCommand("wiyhd5aw 14305884 2066.392479047061");
        assertFalse(actual);
    }

    @Test
    void exhibit_withdraw_from_cd_case_insensitivity_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("wIthDrAw 14305884 2056.7745783112355");
        assertTrue(actual);
    }

    @Test
    void exhibit_withdraw_from_cd_case_insensitivity_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 14");
        boolean actual = commandValidator.validateCommand("wIthDrAw 14305884 2066.392479047061");
        assertTrue(actual);
    }

    @Test
    void withdraw_more_than_balance_from_cd_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2060");
        assertTrue(actual);
    }

    @Test
    void withdraw_more_than_balance_from_cd_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 14");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 3000");
        assertTrue(actual);
    }

    @Test
    void withdraw_exactly_balance_from_cd_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2056.7745783112355");
        assertTrue(actual);
    }

    @Test
    void withdraw_exactly_balance_from_cd_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 16");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2076.0553550638624");
        assertTrue(actual);
    }

    @Test
    void withdraw_less_than_balance_from_cd_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2054");
        assertFalse(actual);
    }

    @Test
    void withdraw_less_than_balance_from_cd_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 16");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 2074");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_balance_from_cd_after_exactly_twelve_months_have_passed() {
        processCommand.process("pass 12");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 -205");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_balance_from_cd_after_more_than_twelve_months_have_passed() {
        processCommand.process("pass 16");
        boolean actual = commandValidator.validateCommand("withdraw 14305884 -125");
        assertFalse(actual);
    }

    @Test
    void invalid_withdraw_from_savings_more_than_once_per_month_ie_without_passing_time() {
        processCommand.process("deposit 14305882 1550");
        processCommand.process("withdraw 14305882 500");
        boolean actual = commandValidator.validateCommand("withdraw 14305882 50");
        assertFalse(actual);
    }

    @Test
    void valid_withdraw_from_savings_a_second_time_after_passing_one_month() {
        processCommand.process("deposit 14305882 1550");
        processCommand.process("withdraw 14305882 500");
        processCommand.process("pass 1");
        boolean actual = commandValidator.validateCommand("withdraw 14305882 50");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_from_savings_a_second_time_after_passing_sixty_months() {
        processCommand.process("deposit 14305882 1550");
        processCommand.process("withdraw 14305882 500");
        processCommand.process("pass 60");
        boolean actual = commandValidator.validateCommand("withdraw 14305882 50");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_from_savings_after_passing_time_and_again_after_passing_time_a_second_time() {
        processCommand.process("deposit 14305882 1550");
        processCommand.process("withdraw 14305882 500");
        processCommand.process("pass 1");
        processCommand.process("withdraw 14305882 540");
        processCommand.process("pass 13");
        boolean actual = commandValidator.validateCommand("withdraw 14305882 50");
        assertTrue(actual);
    }
}
