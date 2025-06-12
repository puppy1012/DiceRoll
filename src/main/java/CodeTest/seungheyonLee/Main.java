package CodeTest.seungheyonLee;

import CodeTest.seungheyonLee.Account.Entity.Account;
import CodeTest.seungheyonLee.Account.Repository.AccountRepository;
import CodeTest.seungheyonLee.Account.Repository.AccountRepositoryImpl;
import static CodeTest.seungheyonLee.Utility.InputStream.getStringInput;

public class Main {
    public static void main(String[] args) {
        Account account= new Account(getStringInput("name"),getStringInput("password"));
        System.out.println(account);
        AccountRepository repository= AccountRepositoryImpl.getInstance();
        System.out.println(repository.saveAccount(account));
    }
}
