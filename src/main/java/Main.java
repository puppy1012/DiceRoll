import Account.Entity.Account;
import Account.Repository.AccountRepository;
import Account.Repository.AccountRepositoryImpl;

import static Utility.InputStream.getStringInput;

public class Main {
    public static void main(String[] args) {
        Account account= new Account(getStringInput("name"),getStringInput("password"));
        System.out.println(account);
        AccountRepository repository= AccountRepositoryImpl.getInstance();
        System.out.println(repository.saveAccount(account));
    }
}
