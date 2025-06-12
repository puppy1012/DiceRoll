package Account.Repository;

import Account.Entity.Account;

import java.util.Optional;

public interface AccountRepository {
    int saveAccount(Account account);
    Optional<Account> findByUserId(String userId);
    Optional<Account> findById(Integer id);
}
