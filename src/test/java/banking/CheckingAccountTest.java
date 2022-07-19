package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountTest {

    private static final String FIRST_CHECKING_APR = "0.01";

    CheckingAccount checking = new CheckingAccount(FIRST_CHECKING_APR);

    @Test
    public void checking_account_exists() {
        assertEquals(FIRST_CHECKING_APR, checking.getCheckingApr());
    }

    @Test
    public void checking_deposit_once() {
        checking.deposit(89);
        assertEquals(89, checking.getBalance());
    }

    @Test
    public void checking_deposit_twice() {
        checking.deposit(756);
        assertEquals(756, checking.getBalance());
        checking.deposit(144);
        assertEquals(900, checking.getBalance());
    }

    @Test
    public void checking_withdraw_once() {
        checking.deposit(89);
        checking.withdraw(65);
        assertEquals(24, checking.getBalance());
    }

    @Test
    public void checking_withdraw_twice() {
        checking.deposit(900);
        checking.withdraw(5);
        assertEquals(895, checking.getBalance());
        checking.withdraw(700);
        assertEquals(195, checking.getBalance());
    }

    @Test
    public void checking_withdraw_more_than_balance() {
        checking.deposit(10);
        checking.withdraw(20);
        assertEquals(0, checking.getBalance());
    }

    @Test
    public void checking_withdraw_entire_balance() {
        checking.deposit(89);
        checking.withdraw(89);
        assertEquals(0, checking.getBalance());
    }
}
