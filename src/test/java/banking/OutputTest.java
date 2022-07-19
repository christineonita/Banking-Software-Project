package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {

    Bank bank;
    MasterControl masterControl;
    private List<String> input;

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @BeforeEach
    void setUp() {
        bank = new Bank();
        masterControl = new MasterControl(bank, new ValidateCommand(bank), new ProcessCommand(bank), new CommandStorage());
        input = new ArrayList<>();
    }

    @Test
    void check_output_after_creating_one_checking_account() {
        input.add("create checking 14305883 0.7");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("Checking 14305883 0.00 0.70", actual);
    }

    @Test
    void check_that_output_is_empty_after_creating_one_checking_account_and_passing_one_month_without_depositing() {
        input.add("cReAte chEckIng 14305883 0.7");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_one_checking_account_and_depositing() {
        input.add("cReAte chEckIng 14305883 0.7");
        input.add("deposit 14305883 985");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 14305883 985.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 985", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_checking_account_and_depositing_and_passing_five_months() {
        input.add("cReAte chEckIng 14305883 0.7");
        input.add("deposit 14305883 985");
        input.add("pass 5");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 14305883 987.87 0.70", actual.get(0));
        assertEquals("deposit 14305883 985", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_checking_account_and_depositing_and_passing_sixty_one_months_invalid() {
        input.add("cReAte chEckIng 14305883 0.7");
        input.add("deposit 14305883 900");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 14305883 900.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 900", actual.get(1));
        assertEquals("pass 61", actual.get(2));
    }

    @Test
    void check_output_after_creating_one_checking_account_and_depositing_and_withdrawing() {
        input.add("cReAte chEckIng 14305883 0.7");
        input.add("deposit 14305883 985");
        input.add("withdraw 14305883 65");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 14305883 920.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 985", actual.get(1));
        assertEquals("withdraw 14305883 65", actual.get(2));
    }

    @Test
    void check_output_after_creating_one_savings_account() {
        input.add("create savings 14305882 0.8");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("Savings 14305882 0.00 0.80", actual);
    }

    @Test
    void check_that_output_is_empty_after_creating_one_savings_account_and_passing_one_month_without_depositing() {
        input.add("create savings 14305882 0.8");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_one_savings_account_and_depositing() {
        input.add("create savings 14305882 0.8");
        input.add("deposit 14305882 1350");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Savings 14305882 1350.00 0.80", actual.get(0));
        assertEquals("deposit 14305882 1350", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_savings_account_and_depositing_and_passing_sixty_one_months_invalid() {
        input.add("create savings 14305882 0.8");
        input.add("deposit 14305882 1350");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Savings 14305882 1350.00 0.80", actual.get(0));
        assertEquals("deposit 14305882 1350", actual.get(1));
        assertEquals("pass 61", actual.get(2));
    }

    @Test
    void check_output_after_creating_one_savings_account_and_depositing_and_withdrawing() {
        input.add("create savings 14305882 0.8");
        input.add("deposit 14305882 1350");
        input.add("withdraw 14305882 900");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Savings 14305882 450.00 0.80", actual.get(0));
        assertEquals("deposit 14305882 1350", actual.get(1));
        assertEquals("withdraw 14305882 900", actual.get(2));
    }

    @Test
    void check_output_after_creating_one_savings_account_and_depositing_and_withdrawing_a_second_time_without_passing_time_invalid() {
        input.add("create savings 14305882 0.8");
        input.add("deposit 14305882 1350");
        input.add("withdraw 14305882 900");
        input.add("withdraw 14305882 95");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Savings 14305882 450.00 0.80", actual.get(0));
        assertEquals("deposit 14305882 1350", actual.get(1));
        assertEquals("withdraw 14305882 900", actual.get(2));
        assertEquals("withdraw 14305882 95", actual.get(3));
    }

    @Test
    void check_output_after_creating_one_savings_account_and_depositing_and_withdrawing_a_second_time_after_passing_time() {
        input.add("create savings 14305882 0.8");
        input.add("deposit 14305882 1350");
        input.add("withdraw 14305882 900");
        input.add("pass 1");
        input.add("withdraw 14305882 95");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Savings 14305882 355.30 0.80", actual.get(0));
        assertEquals("deposit 14305882 1350", actual.get(1));
        assertEquals("withdraw 14305882 900", actual.get(2));
        assertEquals("withdraw 14305882 95", actual.get(3));
    }

    @Test
    void check_output_after_creating_one_cd_account() {
        input.add("create cd 14305884 0.4 5000");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("Cd 14305884 5000.00 0.40", actual);
    }

    @Test
    void check_output_after_creating_one_cd_account_and_withdraw_without_passing_time() {
        input.add("create cd 14305884 0.4 5000");
        input.add("withdraw 14305884 5000");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 5000.00 0.40", actual.get(0));
        assertEquals("withdraw 14305884 5000", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_withdraw_after_passing_less_than_twelve_months_invalid() {
        input.add("create cd 14305884 0.4 5000");
        input.add("pass 10");
        input.add("withdraw 14305884 5067.101835284545");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 5067.10 0.40", actual.get(0));
        assertEquals("withdraw 14305884 5067.101835284545", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_withdraw_after_passing_exactly_twelve_months() {
        input.add("create cd 14305884 0.4 5000");
        input.add("pass 12");
        input.add("withdraw 14305884 5080.629881676047");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 0.00 0.40", actual.get(0));
        assertEquals("withdraw 14305884 5080.629881676047", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_withdraw_after_passing_twenty_one_months() {
        input.add("create cd 14305884 0.4 5000");
        input.add("pass 21");
        input.add("withdraw 14305884 5141.954431594858");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 0.00 0.40", actual.get(0));
        assertEquals("withdraw 14305884 5141.954431594858", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_withdraw_amount_less_than_balance_after_passing_more_than_twelve_months_invalid() {
        input.add("create cd 14305884 0.4 5000");
        input.add("pass 19");
        input.add("withdraw 14305884 4500");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 5128.26 0.40", actual.get(0));
        assertEquals("withdraw 14305884 4500", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_passing_more_than_sixty_months_invalid() {
        input.add("create cd 14305884 0.4 9000");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 9000.00 0.40", actual.get(0));
        assertEquals("pass 61", actual.get(1));
    }

    @Test
    void check_output_after_creating_one_cd_account_and_passing_one_month_after_full_withdrawal_so_it_closes() {
        input.add("create cd 14305884 0.4 9000");
        input.add("pass 20");
        input.add("withdraw 14305884 9243.187563291838");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_two_checking_accounts() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 14305883 0.00 0.70", actual.get(0));
        assertEquals("Checking 24305883 0.00 0.20", actual.get(1));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_passing_one_month_without_depositing_so_they_close() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_depositing_in_one_then_passing_one_month_so_the_other_closes() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 102");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 14305883 102.05 0.70", actual.get(0));
        assertEquals("deposit 14305883 102", actual.get(1));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_depositing_in_both() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 102");
        input.add("deposit 24305883 55.99");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Checking 14305883 102.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 102", actual.get(1));
        assertEquals("Checking 24305883 55.99 0.20", actual.get(2));
        assertEquals("deposit 24305883 55.99", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_depositing_in_both_and_passing_five_months() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 102");
        input.add("deposit 24305883 155.99");
        input.add("pass 5");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Checking 14305883 102.29 0.70", actual.get(0));
        assertEquals("deposit 14305883 102", actual.get(1));
        assertEquals("Checking 24305883 156.12 0.20", actual.get(2));
        assertEquals("deposit 24305883 155.99", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_depositing_in_both_and_passing_sixty_one_months_invalid() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 950");
        input.add("deposit 24305883 630");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 14305883 950.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 950", actual.get(1));
        assertEquals("Checking 24305883 630.00 0.20", actual.get(2));
        assertEquals("deposit 24305883 630", actual.get(3));
        assertEquals("pass 61", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_depositing_in_both_then_withdrawing() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 102");
        input.add("deposit 24305883 55.99");
        input.add("withdraw 14305883 10");
        input.add("withdraw 24305883 5.6");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 14305883 92.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 102", actual.get(1));
        assertEquals("withdraw 14305883 10", actual.get(2));
        assertEquals("Checking 24305883 50.39 0.20", actual.get(3));
        assertEquals("deposit 24305883 55.99", actual.get(4));
        assertEquals("withdraw 24305883 5.6", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_transferring_after_depositing_in_one() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 430");
        input.add("transfer 14305883 24305883 250");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 14305883 180.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 430", actual.get(1));
        assertEquals("transfer 14305883 24305883 250", actual.get(2));
        assertEquals("Checking 24305883 250.00 0.20", actual.get(3));
        assertEquals("transfer 14305883 24305883 250", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_passing_time_after_depositing_in_one_and_transferring() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 430");
        input.add("transfer 14305883 24305883 250");
        input.add("pass 8");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 14305883 180.84 0.70", actual.get(0));
        assertEquals("deposit 14305883 430", actual.get(1));
        assertEquals("transfer 14305883 24305883 250", actual.get(2));
        assertEquals("Checking 24305883 250.33 0.20", actual.get(3));
        assertEquals("transfer 14305883 24305883 250", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_transferring_after_depositing_in_both() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 400");
        input.add("deposit 24305883 500");
        input.add("transfer 14305883 24305883 100");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 14305883 300.00 0.70", actual.get(0));
        assertEquals("deposit 14305883 400", actual.get(1));
        assertEquals("transfer 14305883 24305883 100", actual.get(2));
        assertEquals("Checking 24305883 600.00 0.20", actual.get(3));
        assertEquals("deposit 24305883 500", actual.get(4));
        assertEquals("transfer 14305883 24305883 100", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_checking_accounts_and_pass_time_after_depositing_in_both_and_transferring() {
        input.add("create checking 14305883 0.7");
        input.add("create checking 24305883 0.2");
        input.add("deposit 14305883 400");
        input.add("deposit 24305883 500");
        input.add("transfer 14305883 24305883 100");
        input.add("pass 32");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 14305883 305.65 0.70", actual.get(0));
        assertEquals("deposit 14305883 400", actual.get(1));
        assertEquals("transfer 14305883 24305883 100", actual.get(2));
        assertEquals("Checking 24305883 603.20 0.20", actual.get(3));
        assertEquals("deposit 24305883 500", actual.get(4));
        assertEquals("transfer 14305883 24305883 100", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_savings_accounts() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Savings 14305882 0.00 0.90", actual.get(0));
        assertEquals("Savings 24305882 0.00 0.50", actual.get(1));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_passing_time_without_depositing_so_they_close() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_one_then_passing_time_so_the_other_closes() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 24305882 2400");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Savings 24305882 2401.00 0.50", actual.get(0));
        assertEquals("deposit 24305882 2400", actual.get(1));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Savings 14305882 1300.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("Savings 24305882 2400.00 0.50", actual.get(2));
        assertEquals("deposit 24305882 2400", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_passing_time() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("pass 6");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Savings 14305882 1305.86 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("Savings 24305882 2406.00 0.50", actual.get(2));
        assertEquals("deposit 24305882 2400", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_passing_sixty_one_months_invalid() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 14305882 1300.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("Savings 24305882 2400.00 0.50", actual.get(2));
        assertEquals("deposit 24305882 2400", actual.get(3));
        assertEquals("pass 61", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_withdrawing() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("withdraw 14305882 300");
        input.add("withdraw 24305882 400");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 14305882 1000.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("withdraw 14305882 300", actual.get(2));
        assertEquals("Savings 24305882 2000.00 0.50", actual.get(3));
        assertEquals("deposit 24305882 2400", actual.get(4));
        assertEquals("withdraw 24305882 400", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_one_then_transferring() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("transfer 14305882 24305882 300");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 14305882 1000.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("Savings 24305882 300.00 0.50", actual.get(3));
        assertEquals("transfer 14305882 24305882 300", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_passing_time_after_depositing_in_one_then_transferring() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("transfer 14305882 24305882 300");
        input.add("pass 14");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 14305882 1010.55 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("Savings 24305882 301.75 0.50", actual.get(3));
        assertEquals("transfer 14305882 24305882 300", actual.get(4));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_transferring() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("transfer 14305882 24305882 300");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 14305882 1000.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("Savings 24305882 2700.00 0.50", actual.get(3));
        assertEquals("deposit 24305882 2400", actual.get(4));
        assertEquals("transfer 14305882 24305882 300", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_passing_time_after_depositing_in_both_then_transferring() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("transfer 14305882 24305882 300");
        input.add("pass 9");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 14305882 1006.77 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("Savings 24305882 2710.14 0.50", actual.get(3));
        assertEquals("deposit 24305882 2400", actual.get(4));
        assertEquals("transfer 14305882 24305882 300", actual.get(5));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_transferring_a_second_time_without_passing_time_invalid() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("transfer 14305882 24305882 300");
        input.add("transfer 14305882 24305882 500");

        List<String> actual = masterControl.start(input);

        assertEquals(7, actual.size());
        assertEquals("Savings 14305882 1000.00 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("Savings 24305882 2700.00 0.50", actual.get(3));
        assertEquals("deposit 24305882 2400", actual.get(4));
        assertEquals("transfer 14305882 24305882 300", actual.get(5));
        assertEquals("transfer 14305882 24305882 500", actual.get(6));
    }

    @Test
    void check_output_after_creating_two_savings_accounts_and_depositing_in_both_then_transferring_a_second_after_passing_time() {
        input.add("create savings 14305882 0.9");
        input.add("create savings 24305882 0.5");
        input.add("deposit 14305882 1300");
        input.add("deposit 24305882 2400");
        input.add("transfer 14305882 24305882 300");
        input.add("pass 1");
        input.add("transfer 14305882 24305882 500");

        List<String> actual = masterControl.start(input);

        assertEquals(8, actual.size());
        assertEquals("Savings 14305882 500.75 0.90", actual.get(0));
        assertEquals("deposit 14305882 1300", actual.get(1));
        assertEquals("transfer 14305882 24305882 300", actual.get(2));
        assertEquals("transfer 14305882 24305882 500", actual.get(3));
        assertEquals("Savings 24305882 3201.12 0.50", actual.get(4));
        assertEquals("deposit 24305882 2400", actual.get(5));
        assertEquals("transfer 14305882 24305882 300", actual.get(6));
        assertEquals("transfer 14305882 24305882 500", actual.get(7));
    }

    @Test
    void check_output_after_creating_two_cd_accounts() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 14305884 8000.00 0.50", actual.get(0));
        assertEquals("Cd 24305884 9000.00 0.60", actual.get(1));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_without_passing_time_invalid() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("withdraw 14305884 8000");
        input.add("withdraw 24305884 9000");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Cd 14305884 8000.00 0.50", actual.get(0));
        assertEquals("Cd 24305884 9000.00 0.60", actual.get(1));
        assertEquals("withdraw 14305884 8000", actual.get(2));
        assertEquals("withdraw 24305884 9000", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_after_passing_less_than_twelve_months_invalid() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 10");
        input.add("withdraw 14305884 8134.4224063620995");
        input.add("withdraw 24305884 9181.766166592453");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Cd 14305884 8134.42 0.50", actual.get(0));
        assertEquals("Cd 24305884 9181.76 0.60", actual.get(1));
        assertEquals("withdraw 14305884 8134.4224063620995", actual.get(2));
        assertEquals("withdraw 24305884 9181.766166592453", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_after_passing_exactly_twelve_months() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 12");
        input.add("withdraw 14305884 8161.576723016895");
        input.add("withdraw 24305884 9218.557567934533");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Cd 14305884 0.00 0.50", actual.get(0));
        assertEquals("withdraw 14305884 8161.576723016895", actual.get(1));
        assertEquals("Cd 24305884 0.00 0.60", actual.get(2));
        assertEquals("withdraw 24305884 9218.557567934533", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_after_passing_more_than_twelve_months() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 21");
        input.add("withdraw 14305884 8284.897276243093");
        input.add("withdraw 24305884 9385.951788584487");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Cd 14305884 0.00 0.50", actual.get(0));
        assertEquals("withdraw 14305884 8284.897276243093", actual.get(1));
        assertEquals("Cd 24305884 0.00 0.60", actual.get(2));
        assertEquals("withdraw 24305884 9385.951788584487", actual.get(3));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_after_passing_sixty_one_months_invalid() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 61");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Cd 14305884 8000.00 0.50", actual.get(0));
        assertEquals("Cd 24305884 9000.00 0.60", actual.get(1));
        assertEquals("pass 61", actual.get(2));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_from_one_after_passing_more_than_twelve_months_then_pass_time_so_it_closes() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 21");
        input.add("withdraw 14305884 8284.897276243093");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(1, actual.size());
        assertEquals("Cd 24305884 9404.73 0.60", actual.get(0));
    }

    @Test
    void check_output_after_creating_two_cd_accounts_and_withdrawing_from_both_after_passing_more_than_twelve_months_then_pass_time_so_they_close() {
        input.add("create cd 14305884 0.5 8000");
        input.add("create cd 24305884 0.6 9000");
        input.add("pass 21");
        input.add("withdraw 14305884 8284.897276243093");
        input.add("withdraw 24305884 9385.951788584487");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(0, actual.size());
    }

    @Test
    void check_output_after_creating_one_checking_one_savings_and_one_cd_account() {
        input.add("create checking 14305883 0.7");
        input.add("create savings 14305882 0.8");
        input.add("create cd 14305884 0.4 5000");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 14305883 0.00 0.70", actual.get(0));
        assertEquals("Savings 14305882 0.00 0.80", actual.get(1));
        assertEquals("Cd 14305884 5000.00 0.40", actual.get(2));
    }

    @Test
    void check_output_after_creating_one_checking_one_savings_and_one_cd_account_and_passing_one_month_without_depositing_so_checking_and_savings_close() {
        input.add("create checking 14305883 0.7");
        input.add("create savings 14305882 0.8");
        input.add("create cd 14305884 0.4 5000");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("Cd 14305884 5006.67 0.40", actual);
    }

    @Test
    void check_output_after_creating_one_checking_one_savings_and_one_cd_account_then_withdrawing_from_cd_without_passing_time() {
        input.add("create checking 14305883 0.7");
        input.add("create savings 14305882 0.8");
        input.add("create cd 14305884 0.4 5000");
        input.add("withdraw 14305884 5000");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Checking 14305883 0.00 0.70", actual.get(0));
        assertEquals("Savings 14305882 0.00 0.80", actual.get(1));
        assertEquals("Cd 14305884 5000.00 0.40", actual.get(2));
        assertEquals("withdraw 14305884 5000", actual.get(3));
    }

    @Test
    void check_output_after_creating_one_checking_one_savings_and_one_cd_account_then_withdrawing_from_cd_after_passing_more_than_twelve_months() {
        input.add("create checking 14305883 0.7");
        input.add("create savings 14305882 0.8");
        input.add("create cd 14305884 0.4 5000");
        input.add("deposit 14305883 400");
        input.add("deposit 14305882 500");
        input.add("pass 13");
        input.add("withdraw 14305884 5087.407442690951");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Checking 14305883 403.04 0.70", actual.get(0));
        assertEquals("deposit 14305883 400", actual.get(1));
        assertEquals("Savings 14305882 504.35 0.80", actual.get(2));
        assertEquals("deposit 14305882 500", actual.get(3));
        assertEquals("Cd 14305884 0.00 0.40", actual.get(4));
        assertEquals("withdraw 14305884 5087.407442690951", actual.get(5));
    }

    @Test
    void check_output_after_creating_one_checking_one_savings_and_one_cd_account_then_withdrawing_from_cd_after_passing_more_than_twelve_months_then_passing_one_month_so_that_cd_closes() {
        input.add("create checking 14305883 0.7");
        input.add("create savings 14305882 0.8");
        input.add("create cd 14305884 0.4 5000");
        input.add("deposit 14305883 400");
        input.add("deposit 14305882 500");
        input.add("pass 13");
        input.add("withdraw 14305884 5087.407442690951");
        input.add("pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Checking 14305883 403.27 0.70", actual.get(0));
        assertEquals("deposit 14305883 400", actual.get(1));
        assertEquals("Savings 14305882 504.68 0.80", actual.get(2));
        assertEquals("deposit 14305882 500", actual.get(3));
    }
}
