package CodeTest.unchul.system;

import java.util.*;

public class Player {
    private String name;
    private List<GameRecord> records = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRecord(GameRecord record) {
        records.add(record);
    }

    public List<GameRecord> getRecords() {
        return records;
    }
}