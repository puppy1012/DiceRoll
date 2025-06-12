package CodeTest.seungheyonLee.Account.Repository;

import CodeTest.seungheyonLee.Account.Entity.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    private static AccountRepositoryImpl instance;

    private AccountRepositoryImpl() {}

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepositoryImpl();
        }
        return instance;
    }
    private static final Map<Integer, Account> accountHashMap = new HashMap<>();

    @Override
    public int saveAccount(Account account) {
        System.out.println("계정정보를 저장중");
        int accountUniqueId=(int)account.getId();
        accountHashMap.put(accountUniqueId, account);
        return accountUniqueId;
    }

    @Override
    public Optional<Account> findByUserId(String userId) {
        return accountHashMap.values().stream()
                .filter(account -> account.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return Optional.ofNullable(accountHashMap.get(id));
    }
}
