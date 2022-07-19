package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessPassTimeCommandTest {

    Bank bank;
    ProcessCommand processCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        processCommand = new ProcessCommand(bank);
    }

    @Test
    void checking_balance_is_correct_after_passing_one_month_in_checking_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("pass 1");
        assertEquals(750.375, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_passing_time_more_than_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(751.12556259375, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_passing_two_months_in_checking_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("pass 2");
        assertEquals(750.7501875, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_passing_sixty_months_in_checking_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("pass 60");
        assertEquals(772.8351061114834, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_passing_one_month_in_savings_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 1");
        assertEquals(1500.625, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_passing_time_more_than_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 2");
        processCommand.process("pass 3");
        assertEquals(1503.127605251962, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_passing_two_months_in_savings_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 2");
        assertEquals(1501.2502604166666, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_passing_sixty_months_in_savings_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 60");
        assertEquals(1537.964672757493, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void cd_balance_is_correct_after_passing_one_month_in_cd_once() {
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 1");
        assertEquals(1033.7523196373454, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void cd_balance_is_correct_after_passing_time_more_than_once() {
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 3");
        processCommand.process("pass 24");
        assertEquals(2450.447605487465, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void cd_balance_is_correct_after_passing_two_months_in_cd_once() {
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 2");
        assertEquals(1068.6438583555923, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void cd_balance_is_correct_after_passing_sixty_months_in_cd_once() {
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 60");
        assertEquals(7328.073633249646, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void savings_and_checking_balances_are_correct_after_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 2");
        assertEquals(750.7501875, bank.getAccounts().get("14305883").getBalance());
        assertEquals(1501.2502604166666, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_and_checking_balances_are_correct_after_passing_time_more_than_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("pass 2");
        processCommand.process("pass 17");
        assertEquals(757.1571535257103, bank.getAccounts().get("14305883").getBalance());
        assertEquals(1511.9196365686869, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_and_cd_balances_are_correct_after_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 2");
        assertEquals(1501.2502604166666, bank.getAccounts().get("14305882").getBalance());
        assertEquals(1068.6438583555923, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void savings_and_cd_balances_are_correct_after_passing_time_more_than_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 6");
        processCommand.process("pass 38");
        assertEquals(1527.7477973906216, bank.getAccounts().get("14305882").getBalance());
        assertEquals(4308.497758239105, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void checking_and_cd_balances_are_correct_after_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 2");
        assertEquals(750.7501875, bank.getAccounts().get("14305883").getBalance());
        assertEquals(1068.6438583555923, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void checking_and_cd_balances_are_correct_after_passing_time_more_than_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 9");
        processCommand.process("pass 43");
        assertEquals(769.7507096263864, bank.getAccounts().get("14305883").getBalance());
        assertEquals(5618.98467884254, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void savings_and_checking_and_cd_balances_are_correct_after_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 2");
        assertEquals(1501.2502604166666, bank.getAccounts().get("14305882").getBalance());
        assertEquals(750.7501875, bank.getAccounts().get("14305883").getBalance());
        assertEquals(1068.6438583555923, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void savings_and_checking_and_cd_balances_are_correct_after_passing_time_more_than_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 1500");
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 750");
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 11");
        processCommand.process("pass 20");
        assertEquals(1519.4965829144928, bank.getAccounts().get("14305882").getBalance());
        assertEquals(761.7126103851626, bank.getAccounts().get("14305883").getBalance());
        assertEquals(2798.410420533376, bank.getAccounts().get("14305884").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_depositing_less_than_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 95");
        processCommand.process("pass 1");
        assertEquals(70.035, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_depositing_exactly_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 100");
        processCommand.process("pass 1");
        assertEquals(100.05, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void both_checking_balances_are_correct_after_depositing_less_than_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create checking 24305883 0.7");
        processCommand.process("deposit 14305883 95");
        processCommand.process("deposit 24305883 80");
        processCommand.process("pass 1");
        assertEquals(70.035, bank.getAccounts().get("14305883").getBalance());
        assertEquals(55.03208333333333, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void both_checking_balances_are_correct_after_depositing_exactly_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create checking 24305883 0.7");
        processCommand.process("deposit 14305883 100");
        processCommand.process("deposit 24305883 100");
        processCommand.process("pass 1");
        assertEquals(100.05, bank.getAccounts().get("14305883").getBalance());
        assertEquals(100.05833333333334, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_depositing_less_than_100_and_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 90");
        processCommand.process("pass 1");
        assertEquals(65.02708333333334, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_depositing_exactly_100_and_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 100");
        processCommand.process("pass 1");
        assertEquals(100.04166666666667, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void both_savings_balances_are_correct_after_depositing_less_than_100_and_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 14305882 90");
        processCommand.process("deposit 24305882 65");
        processCommand.process("pass 1");
        assertEquals(65.02708333333334, bank.getAccounts().get("14305882").getBalance());
        assertEquals(40.013333333333335, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void both_savings_balances_are_correct_after_depositing_exactly_100_and_passing_time_once() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 14305882 100");
        processCommand.process("deposit 24305882 100");
        processCommand.process("pass 1");
        assertEquals(100.04166666666667, bank.getAccounts().get("14305882").getBalance());
        assertEquals(100.03333333333333, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void checking_and_savings_balances_are_correct_after_depositing_less_than_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305883 95");
        processCommand.process("deposit 14305882 90");
        processCommand.process("pass 1");
        assertEquals(70.035, bank.getAccounts().get("14305883").getBalance());
        assertEquals(65.02708333333334, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void checking_and_savings_balances_are_correct_after_depositing_exactly_100_and_passing_time_once() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305883 100");
        processCommand.process("deposit 14305882 100");
        processCommand.process("pass 1");
        assertEquals(100.05, bank.getAccounts().get("14305883").getBalance());
        assertEquals(100.04166666666667, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_depositing_less_than_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 95");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(20.067546258749996, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void checking_balance_is_correct_after_depositing_exactly_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 100");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(100.1500750125, bank.getAccounts().get("14305883").getBalance());
    }

    @Test
    void both_checking_balances_are_correct_after_depositing_less_than_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create checking 24305883 0.7");
        processCommand.process("deposit 14305883 95");
        processCommand.process("deposit 24305883 80");
        processCommand.process("pass 1");
        processCommand.process("pass 1");
        assertEquals(45.057517499999996, bank.getAccounts().get("14305883").getBalance());
        assertEquals(30.04960204861111, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void both_checking_balances_are_correct_after_depositing_exactly_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create checking 24305883 0.7");
        processCommand.process("deposit 14305883 100");
        processCommand.process("deposit 24305883 100");
        processCommand.process("pass 1");
        processCommand.process("pass 1");
        assertEquals(100.100025, bank.getAccounts().get("14305883").getBalance());
        assertEquals(100.11670069444445, bank.getAccounts().get("24305883").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_depositing_less_than_100_and_passing_time_twice() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 90");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(15.050029518590858, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void savings_balance_is_correct_after_depositing_exactly_100_and_passing_time_twice() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 100");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(100.12505209056714, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void both_savings_balances_are_correct_after_depositing_less_than_100_and_passing_time_twice() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 14305882 90");
        processCommand.process("deposit 24305882 65");
        processCommand.process("pass 1");
        processCommand.process("pass 1");
        assertEquals(40.04376128472222, bank.getAccounts().get("14305882").getBalance());
        assertEquals(15.01833777777778, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void both_savings_balances_are_correct_after_depositing_exactly_100_and_passing_time_twice() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 14305882 100");
        processCommand.process("deposit 24305882 100");
        processCommand.process("pass 1");
        processCommand.process("pass 1");
        assertEquals(100.08335069444445, bank.getAccounts().get("14305882").getBalance());
        assertEquals(100.06667777777777, bank.getAccounts().get("24305882").getBalance());
    }

    @Test
    void checking_and_savings_balances_are_correct_after_depositing_less_than_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305883 95");
        processCommand.process("deposit 14305882 90");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(20.067546258749996, bank.getAccounts().get("14305883").getBalance());
        assertEquals(15.050029518590858, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void checking_and_savings_balances_are_correct_after_depositing_exactly_100_and_passing_time_twice() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305883 100");
        processCommand.process("deposit 14305882 100");
        processCommand.process("pass 1");
        processCommand.process("pass 2");
        assertEquals(100.1500750125, bank.getAccounts().get("14305883").getBalance());
        assertEquals(100.12505209056714, bank.getAccounts().get("14305882").getBalance());
    }

    @Test
    void checking_account_closes_after_passing_one_month_because_no_deposit() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void checking_account_closes_after_depositing_and_passing_a_long_time_because_balance_becomes_zero() {
        processCommand.process("create checking 14305883 0.6");
        processCommand.process("deposit 14305883 90");
        processCommand.process("pass 15");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void savings_account_closes_after_passing_one_month_because_no_deposit() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void savings_account_closes_after_depositing_and_passing_a_long_time_because_balance_becomes_zero() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("deposit 14305882 95");
        processCommand.process("pass 18");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void cd_account_closes_after_withdrawing_and_passing_one_month_because_balance_becomes_zero_after_withdrawal() {
        processCommand.process("create cd 14305884 10 1000");
        processCommand.process("pass 25");
        processCommand.process("withdraw 14305884 2293.044203948511");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_for_two_checking_accounts_and_one_of_them_closes_because_nothing_was_deposited() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create checking 24305883 0.4");
        processCommand.process("deposit 24305883 95");
        processCommand.process("pass 1");
        assertFalse(bank.accountExistsById("14305883"));
        assertTrue(bank.accountExistsById("24305883"));
    }

    @Test
    void pass_a_long_time_for_two_checking_accounts_after_depositing_less_than_100_dollars_and_they_both_close_because_their_balances_are_zero() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create checking 24305883 0.4");
        processCommand.process("deposit 14305883 75");
        processCommand.process("deposit 24305883 95");
        processCommand.process("pass 20");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_for_two_savings_accounts_and_one_of_them_closes_because_nothing_was_deposited() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 24305882 905");
        processCommand.process("pass 1");
        assertFalse(bank.accountExistsById("14305882"));
        assertTrue(bank.accountExistsById("24305882"));
    }

    @Test
    void pass_a_long_time_for_two_savings_accounts_after_depositing_less_than_100_dollars_and_they_both_close_because_their_balances_are_zero() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create savings 24305882 0.4");
        processCommand.process("deposit 14305882 25");
        processCommand.process("deposit 24305882 85");
        processCommand.process("pass 54");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_twelve_months_for_two_cd_accounts_then_withdraw_balance_from_one_of_them_so_it_closes_after_passing_one_month_because_its_balance_is_zero() {
        processCommand.process("create cd 14305884 0.8 1000");
        processCommand.process("create cd 24305884 0.6 1500");
        processCommand.process("pass 12");
        processCommand.process("withdraw 24305884 1536.4262613224225");
        processCommand.process("pass 1");
        assertTrue(bank.accountExistsById("14305884"));
        assertFalse(bank.accountExistsById("24305884"));
    }

    @Test
    void pass_twelve_months_for_two_cd_accounts_then_withdraw_balance_from_both_of_them_so_they_close_after_passing_one_month_because_their_balances_are_zero() {
        processCommand.process("create cd 14305884 0.8 1000");
        processCommand.process("create cd 24305884 0.6 1500");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1032.506496736195");
        processCommand.process("withdraw 24305884 1536.4262613224225");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_for_one_checking_and_one_savings_account_and_savings_closes_because_nothing_was_deposited() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create savings 14305882 0.4");
        processCommand.process("deposit 14305883 95");
        processCommand.process("pass 1");
        assertTrue(bank.accountExistsById("14305883"));
        assertFalse(bank.accountExistsById("14305882"));
    }

    @Test
    void pass_one_month_for_one_checking_and_one_savings_account_and_checking_closes_because_nothing_was_deposited() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create savings 14305882 0.4");
        processCommand.process("deposit 14305882 95");
        processCommand.process("pass 1");
        assertFalse(bank.accountExistsById("14305883"));
        assertTrue(bank.accountExistsById("14305882"));
    }

    @Test
    void pass_a_long_time_for_one_checking_and_one_savings_account_after_depositing_less_than_100_dollars_and_they_both_close_because_their_balances_are_zero() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create savings 14305882 0.4");
        processCommand.process("deposit 14305883 95");
        processCommand.process("deposit 14305882 85");
        processCommand.process("pass 45");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_for_one_checking_and_one_cd_account_and_checking_closes_because_nothing_was_deposited() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("pass 1");
        assertFalse(bank.accountExistsById("14305883"));
        assertTrue(bank.accountExistsById("14305884"));
    }

    @Test
    void pass_twelve_months_for_one_checking_and_one_cd_accounts_then_withdraw_balance_from_cd_which_closes_after_passing_one_month_because_its_balances_is_zero() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("deposit 14305883 500");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1524.1889645028143");
        processCommand.process("pass 1");
        assertTrue(bank.accountExistsById("14305883"));
        assertFalse(bank.accountExistsById("14305884"));
    }

    @Test
    void pass_a_long_time_for_one_checking_and_one_cd_account_after_depositing_less_than_100_dollars_in_checking_then_withdraw_cd_balance_and_they_both_close_because_their_balances_are_zero() {
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("deposit 14305883 30");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1524.1889645028143");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_for_one_savings_and_one_cd_account_and_savings_closes_because_nothing_was_deposited() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("pass 1");
        assertFalse(bank.accountExistsById("14305882"));
        assertTrue(bank.accountExistsById("14305884"));
    }

    @Test
    void pass_twelve_months_for_one_savings_and_one_cd_accounts_then_withdraw_balance_from_cd_which_closes_after_passing_one_month_because_its_balances_is_zero() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("deposit 14305882 500");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1524.1889645028143");
        processCommand.process("pass 1");
        assertTrue(bank.accountExistsById("14305882"));
        assertFalse(bank.accountExistsById("14305884"));
    }

    @Test
    void pass_a_long_time_for_one_savings_and_one_cd_account_after_depositing_less_than_100_dollars_in_savings_then_withdraw_cd_balance_and_they_both_close_because_their_balances_are_zero() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("deposit 14305882 30");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1524.1889645028143");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_a_long_time_for_savings_checking_and_cd_account_after_depositing_less_than_100_dollars_in_savings_and_checking_then_withdraw_cd_balance_and_they_all_close_because_their_balances_are_zero() {
        processCommand.process("create savings 14305882 0.5");
        processCommand.process("create checking 14305883 0.5");
        processCommand.process("create cd 14305884 0.4 1500");
        processCommand.process("deposit 14305882 30");
        processCommand.process("deposit 14305882 45");
        processCommand.process("pass 12");
        processCommand.process("withdraw 14305884 1524.1889645028143");
        processCommand.process("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

}
