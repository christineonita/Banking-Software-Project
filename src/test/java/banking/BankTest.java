package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private static final String FIRST_SAVINGS_ID = "14305882";
    private static final String FIRST_SAVINGS_APR = "0.2";
    private static final String SECOND_SAVINGS_ID = "24305882";
    private static final String SECOND_SAVINGS_APR = "0.5";

    private static final String FIRST_CHECKING_ID = "14305883";
    private static final String FIRST_CHECKING_APR = "0.3";
    private static final String SECOND_CHECKING_ID = "24305883";
    private static final String SECOND_CHECKING_APR = "0.4";

    private static final String FIRST_CD_ID = "14305884";
    private static final String FIRST_CD_APR = "0.7";
    private static final String FIRST_CD_AMOUNT = "2000";

    private static final String SECOND_CD_ID = "24305884";
    private static final String SECOND_CD_APR = "0.9";
    private static final String SECOND_CD_AMOUNT = "4000";

    Bank bank;
    CheckingAccount firstCheckingAccount = new CheckingAccount(FIRST_CHECKING_APR);
    CheckingAccount secondCheckingAccount = new CheckingAccount(SECOND_CHECKING_APR);

    SavingsAccount firstSavingsAccount = new SavingsAccount(FIRST_SAVINGS_APR);
    SavingsAccount secondSavingsAccount = new SavingsAccount(SECOND_SAVINGS_APR);

    CDAccount firstCdAccount = new CDAccount(FIRST_CD_APR, FIRST_CD_AMOUNT);
    CDAccount secondCdAccount = new CDAccount(SECOND_CD_APR, SECOND_CD_AMOUNT);

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_accounts_initially() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_one_checking_account_to_bank() {
        bank.addAccount(FIRST_CHECKING_ID, firstCheckingAccount);
        assertEquals(firstCheckingAccount, bank.getAccounts().get(FIRST_CHECKING_ID));
    }

    @Test
    void add_two_checking_accounts_to_bank() {
        bank.addAccount(FIRST_CHECKING_ID, firstCheckingAccount);
        bank.addAccount(SECOND_CHECKING_ID, secondCheckingAccount);
        assertEquals(firstCheckingAccount, bank.getAccounts().get(FIRST_CHECKING_ID));
        assertEquals(secondCheckingAccount, bank.getAccounts().get(SECOND_CHECKING_ID));
    }

    @Test
    void add_one_checking_account_to_bank_then_close_it() {
        bank.addAccount(FIRST_CHECKING_ID, firstCheckingAccount);
        assertTrue(bank.accountExistsById(FIRST_CHECKING_ID));
        bank.closeAccount(FIRST_CHECKING_ID);
        assertFalse(bank.accountExistsById(FIRST_CHECKING_ID));
    }

    @Test
    void add_one_savings_account_to_bank() {
        bank.addAccount(FIRST_SAVINGS_ID, firstSavingsAccount);
        assertEquals(firstSavingsAccount, bank.getAccounts().get(FIRST_SAVINGS_ID));
    }

    @Test
    void add_two_savings_accounts_to_bank() {
        bank.addAccount(FIRST_SAVINGS_ID, firstSavingsAccount);
        bank.addAccount(SECOND_SAVINGS_ID, secondSavingsAccount);
        assertEquals(firstSavingsAccount, bank.getAccounts().get(FIRST_SAVINGS_ID));
        assertEquals(secondSavingsAccount, bank.getAccounts().get(SECOND_SAVINGS_ID));
    }

    @Test
    void add_one_savings_account_to_bank_then_close_it() {
        bank.addAccount(FIRST_SAVINGS_ID, firstSavingsAccount);
        assertTrue(bank.accountExistsById(FIRST_SAVINGS_ID));
        bank.closeAccount(FIRST_SAVINGS_ID);
        assertFalse(bank.accountExistsById(FIRST_SAVINGS_ID));
    }

    @Test
    void add_one_cd_account_to_bank() {
        bank.addAccount(FIRST_CD_ID, firstCdAccount);
        assertEquals(firstCdAccount, bank.getAccounts().get(FIRST_CD_ID));
    }

    @Test
    void add_two_cd_accounts_to_bank() {
        bank.addAccount(FIRST_CD_ID, firstCdAccount);
        bank.addAccount(SECOND_CD_ID, secondCdAccount);
        assertEquals(firstCdAccount, bank.getAccounts().get(FIRST_CD_ID));
        assertEquals(secondCdAccount, bank.getAccounts().get(SECOND_CD_ID));
    }

    @Test
    void add_one_cd_account_to_bank_then_close_it() {
        bank.addAccount(FIRST_CD_ID, firstCdAccount);
        assertTrue(bank.accountExistsById(FIRST_CD_ID));
        bank.closeAccount(FIRST_CD_ID);
        assertFalse(bank.accountExistsById(FIRST_CD_ID));
    }
}