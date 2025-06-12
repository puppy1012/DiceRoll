package CodeTest.seungheyonLee.Account.Entity;

public class Account {
    private static long idcounter=1;
    private long id;
    private String userId;
    private String password;
    private Account() {}
    public Account(String name, String password) {
        this.id = idcounter++;
        this.userId = name;
        this.password = password;
    }
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
}
