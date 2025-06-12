package CodeTest.unchul.system;

public class GameRecord {
    private int[] diceRolls;
    private int total;
    private String result;

    public GameRecord(int[] diceRolls, int total, String result) {
        this.diceRolls = diceRolls;
        this.total = total;
        this.result = result;
    }

    public int[] getDiceRolls() {
        return diceRolls;
    }

    public int getTotal() {
        return total;
    }

    public String getResult() {
        return result;
    }
}
