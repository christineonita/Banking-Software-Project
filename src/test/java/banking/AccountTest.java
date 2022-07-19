package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {


    private static final String CHECKING_ID = "14305883";
    private static final String CHECKING_APR = "0.01";

    private static final String SAVINGS_ID = "14305882";
    private static final String SAVINGS_APR = "0.5";

    private static final String CD_ID = "14305884";
    private static final String CD_APR = "0.3";
    private static final String CD_AMOUNT = "2000";

    CheckingAccount checking = new CheckingAccount(CHECKING_APR);
    SavingsAccount savings = new SavingsAccount(SAVINGS_APR);
    CDAccount cd = new CDAccount(CD_APR, CD_AMOUNT);

    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(CHECKING_ID, checking);
        bank.addAccount(SAVINGS_ID, savings);
        bank.addAccount(CD_ID, cd);
    }

    @Test
    void create_checking_account_and_make_sure_it_exists() {
        assertTrue(bank.accountExistsById(CHECKING_ID));
    }

    @Test
    void create_checking_account_and_make_sure_balance_is_zero() {
        assertEquals(0, checking.getBalance());
    }

    @Test
    void create_checking_account_and_make_sure_apr_is_correct() {
        assertEquals(0.01, checking.getApr());
    }

    @Test
    void create_savings_account_and_make_sure_it_exists() {
        assertTrue(bank.accountExistsById(SAVINGS_ID));
    }

    @Test
    void create_savings_and_make_sure_balance_is_zero() {
        assertEquals(0, savings.getBalance());
    }

    @Test
    void create_savings_account_and_make_sure_apr_is_correct() {
        assertEquals(0.5, savings.getApr());
    }

    @Test
    void create_cd_account_and_make_sure_it_exists() {
        assertTrue(bank.accountExistsById(CD_ID));
    }

    @Test
    void create_cd_account_and_make_sure_balance_is_correct() {
        assertEquals(2000, cd.getBalance());
    }

    @Test
    void create_cd_account_and_make_sure_apr_is_correct() {
        assertEquals(0.3, cd.getApr());
    }
}
