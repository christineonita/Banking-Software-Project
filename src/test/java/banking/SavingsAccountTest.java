package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {

    public static final String FIRST_SAVINGS_APR = "0.5";

    SavingsAccount savings = new SavingsAccount(FIRST_SAVINGS_APR);

    @Test
    public void savings_account_exists() {
        assertEquals(FIRST_SAVINGS_APR, savings.getSavingsApr());
    }

    @Test
    public void savings_deposit_once() {
        savings.deposit(102);
        assertEquals(102, savings.getBalance());

    }

    @Test
    public void savings_deposit_twice() {
        savings.deposit(10);
        assertEquals(10, savings.getBalance());
        savings.deposit(15);
        assertEquals(25, savings.getBalance());

    }

    @Test
    public void savings_withdraw_once() {
        savings.deposit(42);
        assertEquals(42, savings.getBalance());

    }

    @Test
    public void savings_withdraw_twice() {
        savings.deposit(75);
        savings.withdraw(5);
        assertEquals(70, savings.getBalance());
        savings.withdraw(45);
        assertEquals(25, savings.getBalance());

    }

    @Test
    public void savings_withdraw_more_than_balance() {
        savings.deposit(10);
        savings.withdraw(55);
        assertEquals(0, savings.getBalance());
    }

    @Test
    public void savings_withdraw_entire_balance() {
        savings.deposit(79);
        savings.withdraw(79);
        assertEquals(0, savings.getBalance());
    }
}
