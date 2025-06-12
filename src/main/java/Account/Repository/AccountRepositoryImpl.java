package Account.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import Account.entity.Account;

public class AccountRepositoryImpl implements AccountRepository{
	private static AccountRepositoryImpl instance;
	
	private AccountRepositoryImpl() {}
	
	public static AccountRepositoryImpl getInstance() {
		if(instance == null) {
			instance = new AccountRepositoryImpl();
		}
		
		return instance;
	}
	
	private static final Map<Integer, Account> accountHashMap = new HashMap<>();

	@Override
	public int save(Account account) {
		System.out.println("계정 정보를 저장하시겠습니까?" + account);
		int accountUniqueId = (int) account.getId();
		accountHashMap.put(accountUniqueId, account);
		return accountUniqueId;
	}

	@Override
	public Optional<Account> findByUserId(String userId) {
		// accountHashManp에서 모든 Account 객체를 꺼내기
		Collection<Account> accounts = accountHashMap.values();
		// stream으로 하나씩 순회
		Stream<Account> stream = accounts.stream();
		// userId가 같은지 걸러내기
		Stream<Account> filtered = stream.filter(account -> account.getUserId().equals(userId));
		//계정 하나만 가져오기(중복된 계정은 가져오지 않음)
		Optional<Account> result = filtered.findFirst();
		return result;
	}

	@Override
	public Optional<Account> findById(Integer id) {
		return Optional.ofNullable(accountHashMap.get(id));
	}
	
	
}
