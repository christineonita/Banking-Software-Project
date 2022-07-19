package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateCreateCommandTest {

    private static final String FIRST_SAVINGS_APR = "0.2";
    private static final String FIRST_CHECKING_APR = "0.3";
    private static final String FIRST_CD_APR = "0.7";
    private static final String FIRST_CD_AMOUNT = "2000";

    ValidateCommand commandValidator;
    Bank bank;

    CheckingAccount firstCheckingAccount = new CheckingAccount(FIRST_CHECKING_APR);
    SavingsAccount firstSavingsAccount = new SavingsAccount(FIRST_SAVINGS_APR);
    CDAccount firstCdAccount = new CDAccount(FIRST_CD_APR, FIRST_CD_AMOUNT);

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidateCommand(bank);
    }

    @Test
    void valid_create_checking_command_when_bank_does_not_already_have_checking_account() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 0.6");
        assertTrue(actual);
    }

    @Test
    void invalid_create_command_with_account_type_not_checking_savings_or_cd() {
        boolean actual = commandValidator.validateCommand("create house 14305883 0.6");
        assertFalse(actual);
    }

    @Test
    void invalid_create_command_with_no_arguments() {
        boolean actual = commandValidator.validateCommand("create");
        assertFalse(actual);
    }

    @Test
    void invalid_create_command_with_only_one_argument() {
        boolean actual = commandValidator.validateCommand("create checking");
        assertFalse(actual);
    }

    @Test
    void duplicate_checking_id_is_invalid() {
        bank.addAccount("14305883", firstCheckingAccount);
        boolean actual = commandValidator.validateCommand("create checking 14305883 0.6");
        assertFalse(actual);
    }

    @Test
    void typo_in_word_create_for_checking() {
        boolean actual = commandValidator.validateCommand("crea7e checking 14305883 0.6");
        assertFalse(actual);
    }

    @Test
    void exhibit_create_checking_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("crEaTe chEckInG 14305883 0.6");
        assertTrue(actual);
    }

    @Test
    void check_if_checking_id_length_is_eight() {
        boolean actual = commandValidator.validateCommand("create checking 1430588 0.6");
        assertFalse(actual);
    }

    @Test
    void check_if_checking_id_is_a_number() {
        boolean actual = commandValidator.validateCommand("create checking 1430abcd 0.6");
        assertFalse(actual);
    }

    @Test
    void invalid_checking_apr_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 0.h");
        assertFalse(actual);
    }

    @Test
    void check_if_checking_apr_is_less_than_zero() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 -0.01");
        assertFalse(actual);
    }

    @Test
    void check_if_checking_apr_is_greater_than_ten() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 10.01");
        assertFalse(actual);
    }

    @Test
    void check_if_checking_apr_is_zero() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 0");
        assertTrue(actual);
    }

    @Test
    void check_if_checking_apr_is_ten() {
        boolean actual = commandValidator.validateCommand("create checking 14305883 10");
        assertTrue(actual);
    }

    @Test
    void check_if_create_checking_command_length_is_not_4() {
        boolean actual = commandValidator.validateCommand("create checking 14305883");
        assertFalse(actual);
    }


    @Test
    void valid_create_savings_command_when_bank_does_not_already_have_savings_account() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 0.2");
        assertTrue(actual);
    }

    @Test
    void duplicate_savings_id_is_invalid() {
        bank.addAccount("14305882", firstSavingsAccount);
        boolean actual = commandValidator.validateCommand("create savings 14305882 0.3");
        assertFalse(actual);
    }

    @Test
    void typo_in_word_create_for_savings() {
        boolean actual = commandValidator.validateCommand("cr9ate savings 14305882 0.2");
        assertFalse(actual);
    }

    @Test
    void exhibit_create_savings_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("crEaTe SaVInGs 14305882 0.2");
        assertTrue(actual);
    }

    @Test
    void check_if_savings_id_length_is_eight() {
        boolean actual = commandValidator.validateCommand("create savings 1430588 0.2");
        assertFalse(actual);
    }

    @Test
    void check_if_savings_id_is_a_number() {
        boolean actual = commandValidator.validateCommand("create savings 1430abcd 0.2");
        assertFalse(actual);
    }

    @Test
    void invalid_savings_apr_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 [.w");
        assertFalse(actual);
    }

    @Test
    void check_if_savings_apr_is_less_than_zero() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 -0.01");
        assertFalse(actual);
    }

    @Test
    void check_if_savings_apr_is_greater_than_ten() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 10.01");
        assertFalse(actual);
    }

    @Test
    void check_if_savings_apr_is_equal_to_zero() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 0");
        assertTrue(actual);
    }

    @Test
    void check_if_savings_apr_is_equal_to_ten() {
        boolean actual = commandValidator.validateCommand("create savings 14305882 10");
        assertTrue(actual);
    }

    @Test
    void check_if_create_savings_command_length_is_not_4() {
        boolean actual = commandValidator.validateCommand("create savings 14305882");
        assertFalse(actual);
    }

    @Test
    void valid_create_cd_command_when_bank_does_not_already_have_cd_account() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 2500");
        assertTrue(actual);
    }

    @Test
    void duplicate_cd_id_is_invalid() {
        bank.addAccount("14305884", firstCdAccount);
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 2500");
        assertFalse(actual);
    }

    @Test
    void typo_in_word_create_for_cd() {
        boolean actual = commandValidator.validateCommand("c5eate cd 14305884 0.2 2500");
        assertFalse(actual);
    }

    @Test
    void exhibit_create_cd_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("cReaTe cD 14305884 0.2 2500");
        assertTrue(actual);
    }

    @Test
    void check_if_cd_id_length_is_eight() {
        boolean actual = commandValidator.validateCommand("create cd 1430588 0.2 2500");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_id_is_a_number() {
        boolean actual = commandValidator.validateCommand("create cd 1430abcd 0.2 2500");
        assertFalse(actual);
    }

    @Test
    void invalid_cd_apr_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 [.w 2500");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_amount_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 2p0");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_apr_is_less_than_zero() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 -0.01 2500");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_apr_is_greater_than_ten() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 10.01 2500");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_apr_is_equal_to_zero() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0 2500");
        assertTrue(actual);
    }

    @Test
    void check_if_cd_apr_is_equal_to_ten() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 10 2500");
        assertTrue(actual);
    }

    @Test
    void check_if_cd_amount_is_equal_to_one_thousand() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 1000");
        assertTrue(actual);
    }

    @Test
    void check_if_cd_amount_is_equal_to_ten_thousand() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 10000");
        assertTrue(actual);
    }

    @Test
    void check_if_cd_amount_is_less_than_one_thousand() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 999.99");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_amount_is_more_than_ten_thousand() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 10000.01");
        assertFalse(actual);
    }

    @Test
    void check_if_cd_amount_is_negative() {
        boolean actual = commandValidator.validateCommand("create cd 14305884 0.2 -150");
        assertFalse(actual);
    }

    @Test
    void check_if_create_cd_command_length_is_not_5() {
        boolean actual = commandValidator.validateCommand("create cd 14305884");
        assertFalse(actual);
    }
}
