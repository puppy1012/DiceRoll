package CodeTest.unchul.system;

import java.util.*;

public class DiceGame {
    private static Scanner scanner = new Scanner(System.in);
    private static Player player1;
    private static Player player2;
    private static int gameCount = 1;

    public static void run() {
        while (true) {
            if (player1 == null || player2 == null) {
                showInitialMenu();
            } else {
                showGameMenu();
            }
        }
    }

    private static void showInitialMenu() {
        System.out.println("1. 유저 등록");
        System.out.println("2. 게임 종료");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                registerPlayers();
                break;
            case 2:
                System.out.println("게임을 종료합니다.");
                System.exit(0);
        }
    }

    private static void registerPlayers() {
        System.out.print("플레이어 1 이름 입력: ");
        String name1 = scanner.nextLine();
        player1 = new Player(name1);

        System.out.print("플레이어 2 이름 입력: ");
        String name2 = scanner.nextLine();
        player2 = new Player(name2);
    }

    private static void showGameMenu() {
        System.out.println("\n===== 게임 메뉴 =====");
        System.out.println("1. 게임 시작");
        System.out.println("2. 기록 열람");
        System.out.println("3. 게임 종료");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                startGame();
                break;
            case 2:
                showRecords();
                break;
            case 3:
                System.out.println("게임을 종료합니다.");
                System.exit(0);
        }
    }

    private static void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void startGame() {
        System.out.println("\n=== " + gameCount + "번째 게임 시작 ===");
        Random rand = new Random();

        int[] d1 = new int[2];
        int[] d2 = new int[2];
        int[] d3 = new int[2];
        boolean[] rolledThird = new boolean[2];

        System.out.println("플레이어 1 첫 번째 주사위:");
        delay();
        d1[0] = rand.nextInt(6) + 1;
        System.out.println("▶ " + d1[0]);

        System.out.println("플레이어 2 첫 번째 주사위:");
        delay();
        d1[1] = rand.nextInt(6) + 1;
        System.out.println("▶ " + d1[1]);

        System.out.println("플레이어 1 두 번째 주사위:");
        delay();
        d2[0] = rand.nextInt(6) + 1;
        System.out.println("▶ " + d2[0]);

        System.out.println("플레이어 2 두 번째 주사위:");
        delay();
        d2[1] = rand.nextInt(6) + 1;
        System.out.println("▶ " + d2[1]);

        if (d1[0] % 2 == 0) {
            System.out.println("플레이어 1 세 번째 주사위:");
            delay();
            d3[0] = rand.nextInt(6) + 1;
            System.out.println("▶ " + d3[0]);
            rolledThird[0] = true;
        }

        if (d1[1] % 2 == 0) {
            System.out.println("플레이어 2 세 번째 주사위:");
            delay();
            d3[1] = rand.nextInt(6) + 1;
            System.out.println("▶ " + d3[1]);
            rolledThird[1] = true;
        }

        int[] totals = new int[2];
        String[] results = new String[] {"OK", "OK"};

        int[] baseTotals = new int[2];
        baseTotals[0] = d1[0] + d2[0];
        baseTotals[1] = d1[1] + d2[1];

        // 기본 합산 및 d3 추가
        for (int i = 0; i < 2; i++) {
            totals[i] = baseTotals[i];
            if (rolledThird[i] && d3[i] != 4) {
                totals[i] += d3[i];
            }
        }

        // 도둑 처리 - base 점수를 기준으로
        boolean[] stole = new boolean[] {false, false};
        if (rolledThird[0] && d3[0] == 3) {
            stole[0] = true;
            results[0] = "Stole";
        }
        if (rolledThird[1] && d3[1] == 3) {
            stole[1] = true;
            results[1] = "Stole";
        }

        if (stole[0]) totals[0] += baseTotals[1];
        if (stole[1]) totals[1] += baseTotals[0];

        // 죽음 처리
        for (int i = 0; i < 2; i++) {
            if (rolledThird[i] && d3[i] == 4) {
                results[i] = "Dead";
                totals[i] = baseTotals[i];
            }
        }

        for (int i = 0; i < 2; i++) {
            int[] rolls = rolledThird[i] ? new int[] {d1[i], d2[i], d3[i]} : new int[] {d1[i], d2[i]};
            (i == 0 ? player1 : player2).addRecord(new GameRecord(rolls, totals[i], results[i]));
        }

        System.out.println();
        System.out.println(player1.getName() + " 총합: " + totals[0]);
        System.out.println(player2.getName() + " 총합: " + totals[1]);

        if (results[0].equals("Dead")) {
            System.out.println(player1.getName() + "가 죽었습니다. 게임 종료.");
        } else if (results[1].equals("Dead")) {
            System.out.println(player2.getName() + "가 죽었습니다. 게임 종료.");
        } else {
            if (totals[0] > totals[1]) {
                System.out.println(player1.getName() + "의 승리입니다!");
            } else if (totals[1] > totals[0]) {
                System.out.println(player2.getName() + "의 승리입니다!");
            } else {
                System.out.println("무승부!");
            }
        }

        gameCount++;
    }

    private static void showRecords() {
        for (Player p : new Player[] { player1, player2 }) {
            System.out.println("\n" + p.getName() + "의 게임 기록:");
            List<GameRecord> records = p.getRecords();
            for (int i = 0; i < records.size(); i++) {
                GameRecord r = records.get(i);
                System.out.println((i + 1) + "번째 게임: 주사위 " + Arrays.toString(r.getDiceRolls()) + ", 총합: " + r.getTotal() + ", 결과: " + r.getResult());
            }
        }
    }
}