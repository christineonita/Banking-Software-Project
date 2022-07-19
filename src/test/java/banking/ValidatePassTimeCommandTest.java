package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatePassTimeCommandTest {

    Bank bank;
    ValidateCommand commandValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidateCommand(bank);
    }

    @Test
    void valid_pass_time_command() {
        boolean actual = commandValidator.validateCommand("pass 3");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_exhibiting_case_insensitivity() {
        boolean actual = commandValidator.validateCommand("pASs 2");
        assertTrue(actual);
    }

    @Test
    void invalid_pass_time_command_with_typo_in_pass() {
        boolean actual = commandValidator.validateCommand("passe 2");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_with_command_length_less_than_2() {
        boolean actual = commandValidator.validateCommand("pass");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_with_command_length_greater_than_2() {
        boolean actual = commandValidator.validateCommand("pass 3 6");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_where_month_is_not_an_integer() {
        boolean actual = commandValidator.validateCommand("pass 5.7");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_where_month_is_not_a_number() {
        boolean actual = commandValidator.validateCommand("pass gr9e%n");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_where_month_is_less_than_1() {
        boolean actual = commandValidator.validateCommand("pass 0");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_where_month_is_greater_than_60() {
        boolean actual = commandValidator.validateCommand("pass 61");
        assertFalse(actual);
    }

    @Test
    void invalid_pass_time_command_where_month_is_negative() {
        boolean actual = commandValidator.validateCommand("pass -1");
        assertFalse(actual);
    }

    @Test
    void valid_pass_time_command_where_month_is_1() {
        boolean actual = commandValidator.validateCommand("pass 1");
        assertTrue(actual);
    }

    @Test
    void valid_pass_time_command_where_month_is_60() {
        boolean actual = commandValidator.validateCommand("pass 60");
        assertTrue(actual);
    }
}
