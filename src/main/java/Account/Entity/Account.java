package Account.entity;

public class Account {
	private static long idCounter = 1;
	
	private final long id;
	private String userId;
	private String password;
	
	public Account(String userId, String password) {
		this.id = idCounter++;
		this.userId = userId;
		this.password = password;
	}

	public static long getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(long idCounter) {
		Account.idCounter = idCounter;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
