package banking;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private Map<String, Account> accounts;
    private Map<String, Account> accountsInInsertionORCreationOrder;

    Bank() {
        accounts = new ConcurrentHashMap<>();
        accountsInInsertionORCreationOrder = new LinkedHashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public Map<String, Account> getAccountsInInsertedOrder() {
        return accountsInInsertionORCreationOrder;
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
        accountsInInsertionORCreationOrder.put(id, account);
    }

    public boolean accountExistsById(String id) {
        return accounts.get(id) != null;
    }

    public void closeAccount(String id) {
        accounts.remove(id);
        accountsInInsertionORCreationOrder.remove(id);
    }
}