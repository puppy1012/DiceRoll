package CodeTest.seungheyonLee.Account.Service;

import CodeTest.seungheyonLee.Account.Entity.Account;
import CodeTest.seungheyonLee.Account.Repository.AccountRepository;
import CodeTest.seungheyonLee.Account.Repository.AccountRepositoryImpl;

import java.util.Optional;

import static CodeTest.seungheyonLee.Utility.InputStream.getStringInput;

public class AccountServiceImpl implements AccountService {

    private static AccountServiceImpl instance;
    private final AccountRepository accountRepository;
    private final int PASSWORD_MINIMUM_LENGTH = 4;

    private AccountServiceImpl() {
        this.accountRepository = AccountRepositoryImpl.getInstance();
    }

    private static AccountServiceImpl getInstance() {
        if (instance == null) {
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    @Override
    public int registerAccount() {
        System.out.println("Registering account...");
        String userId = getStringInput("Enter user ID");
        String password = getStringInput("Enter password");
        int passwordLength = password.length();
        while (passwordLength < PASSWORD_MINIMUM_LENGTH) {
            System.out.println("Password length must be at least " + PASSWORD_MINIMUM_LENGTH);
            password = getStringInput("Enter password");
            passwordLength = password.length();
        }
        Account account = new Account(userId, password);
        return accountRepository.saveAccount(account);
    }

    @Override
    public int signInAccount() {
        // 콘솔에 로그인 시작 메시지 출력
        System.out.println("Signing In account...");
        // 사용자로부터 아이디와 비밀번호를 입력받음
        String userId = getStringInput("Enter user ID");
        String password = getStringInput("Enter password");
        // 입력받은 아이디로 계정을 DB에서 찾음
        Optional<Account> maybeAccount = accountRepository.findByUserId(userId);
        // 계정이 없을 경우 반복해서 입력을 다시 받음
        while (maybeAccount.isEmpty()) {
            // 아이디가 존재하지 않을 경우 오류 메시지 출력
            System.out.println("아이디 혹은 비밀번호를 잘못 입력했습니다.");
            // 다시 아이디와 비밀번호 입력 받음
            userId = getStringInput("아이디를 입력하세요: ");
            password = getStringInput("비밀번호를 입력하세요: ");
            // 입력받은 아이디로 다시 계정 탐색
            maybeAccount = accountRepository.findByUserId(userId);
        }
        // Optional 에서 실제 Account 객체 추출
        Account account = maybeAccount.get();
        // 추출한 계정의 비밀번호와 입력된 비밀번호 비교
        if (account.getPassword().equals(password)) {
            // 비밀번호 일치하면 로그인 성공 메시지 출력
            System.out.println("로그인 성공");
            // 해당 계정의 고유 ID를 반환 (로그인 성공의 증거로)
            return (int) account.getId();
        }
        // 비밀번호가 일치하지 않으면 실패 메시지 출력
        System.out.println("아이디 혹은 비밀번호를 잘못 입력했습니다.");
        // 재귀 호출을 통해 로그인 재시도 (주의: 너무 많은 호출 시 스택오버플로우 위험)
        return this.signInAccount();
    }

}
