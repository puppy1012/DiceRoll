package Account.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import Account.entity.Account;
import Account.repository.AccountRepository;
import Account.repository.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountService {
	private static AccountServiceImpl instance;
	
	private final AccountRepository accountRepository;
	
	private AccountServiceImpl() {
		this.accountRepository = AccountRepositoryImpl.getInstance();
	}
	
	public static AccountServiceImpl getInstance() {
		if(instance == null) {
			instance = new AccountServiceImpl();
		}
		
		return instance;
	}
	
	private final int PASSWORD_MINIMUM_LENGTH = 4;

	@Override
	public int register() {
		System.out.println("회원 가입을 진행합니다.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String userId;
			
			while(true) {
				System.out.print("아이디를 입력하세요: ");
				userId = br.readLine();
				
				//아이디 중복 확인하기
				Optional<Account> maybeExisting = accountRepository.findByUserId(userId);
				if(maybeExisting.isPresent()) {
					System.out.println("이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.");
				} else {
					break; // 중복이 아니면 루프 탈출
				}
			}

			String password;
			
			while(true) {
				System.out.print("비밀번호를 입력하세요: ");
				password = br.readLine();
				
				//비밀번호 길이 확인하기
				if(password.length() <PASSWORD_MINIMUM_LENGTH) {
					System.out.println("비밀번호가 짧습니다. 다시 작성해주세요.");
				} else {
					break;
				}
			}
			
			//회원 저장하기
			Account account = new Account(userId, password);
			return accountRepository.save(account);			

		}catch(IOException e) {
			System.out.println("입력 중 오류가 발생했습니다: " + e.getMessage());
			return -1; // 실패 코드
		}
	}

	@Override
	public int signIn() {
		System.out.println("로그인을 진행합니다.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			try {
				System.out.print("아이디를 입력하세요: ");
				String userId = br.readLine();
				System.out.print("비밀번호를 입력하세요: ");
				String password = br.readLine();
				
				Optional<Account> maybeAccount = accountRepository.findByUserId(userId);
				
				if(maybeAccount.isPresent()) {
					Account account = maybeAccount.get();
					if(account.getPassword().equals(password)) {
						System.out.println("로그인 성공");
						return (int) account.getId();
					}
				}
				
				System.out.println("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
				
			}catch(IOException e) {
				System.out.println("입력 중 오류가 발생했습니다: " + e.getMessage());
				return -1;
			}
		}
	}
}
