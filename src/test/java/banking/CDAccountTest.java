package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDAccountTest {

    public static final String FIRST_CD_APR = "0.3";
    public static final String FIRST_CD_AMOUNT = "2000";

    CDAccount firstCdAccount = new CDAccount(FIRST_CD_APR, FIRST_CD_AMOUNT);

    @Test
    public void cd_account_exists_and_apr_is_correct() {
        assertEquals(FIRST_CD_APR, firstCdAccount.getCdApr());
    }

    @Test
    public void cd_account_exists_and_initial_amount_is_correct() {
        assertEquals(FIRST_CD_AMOUNT, firstCdAccount.getCdInitialAmount());
    }
}
