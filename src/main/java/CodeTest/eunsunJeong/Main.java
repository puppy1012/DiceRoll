package CodeTest.eunsunJeong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	// 전역 기록 저장소
	static List<Map<String, Object>> gameLog = new ArrayList<>();
	// 게임 중단 조건
	static boolean isGameOver = false;
	// 총합 저장소
	static int[] totalScores = new int[2];

	// 게임화면 보여주기
	public static void showPlay(String player1, String player2) {
		System.out.println("======================");
		System.out.println("1번 플레이어 [" + player1 + "]님의 결과");
		diceRoll(0, player1);
		if (isGameOver)
			return;
		System.out.println();

		System.out.println("2번 플레이어 [" + player2 + "]님의 결과");
		diceRoll(1, player2);
		if (isGameOver)
			return;
		System.out.println("======================");
	}

	// 주사위 굴리기
	public static int diceRoll(int playerIndex, String playerName) {
		Map<String, Object> record = new HashMap<String, Object>();

		int d1 = (int) (Math.random() * 6) + 1;
		int d2 = (int) (Math.random() * 6) + 1;
		Integer d3 = null; // 3번 주사위가 null일 수도 있으니까 객체형으로 선언
		int total = d1 + d2;

		System.out.println("주사위1 : " + d1);
		System.out.println("주사위2 : " + d2);

		if (d1 % 2 == 0) {
			System.out.println("스킬 발동!");
			d3 = (int) (Math.random() * 6) + 1;
			System.out.println("주사위3 : " + d3);

			// 3번 주사위 로직 시작
			if (d3 == 3) {
				// 상대방의 인덱스로 치환
				int opponent = (playerIndex == 0) ? 1 : 0;
				// 상대방의 점수를 내 점수에 더하기
				total += totalScores[opponent];
				// 상대방 점수를 0으로 만들기
				totalScores[opponent] = 0;
				System.out.println("상대 점수 강탈!");
			} else if (d3 == 4) {
				total = 0;
				isGameOver = true;
				System.out.println("즉사! 게임이 종료됩니다.");
				// 여기서 메인 화면으로 돌아가도록 로직 수정 필요
			} else {
				total += d3;
			}
		}

		totalScores[playerIndex] += total;
		
		record.put("playerName", playerName);
		record.put("player", playerIndex);
		record.put("dice1", d1);
		record.put("dice2", d2);
		record.put("dice3", d3); // value는 Object이므로 null값도 허용
		record.put("sum", total);

		gameLog.add(record);

		return total;
	}

	// 게임 기록 보여주기
	public static void showLog() {
		int round = 1;
		for (Map<String, Object> record : gameLog) {
			System.out.println("Name : " + record.get("playerName"));
			System.out.println("player : " + (int) record.get("player"));
			System.out.println("dice1 : " + (int) record.get("dice1"));
			System.out.println("dice2 : " + (int) record.get("dice2"));
			System.out.println("dice3 : " + (record.get("dice3") != null ? (int) record.get("dice3") : "null"));
			System.out.println("sum : " + (int) record.get("sum"));
			System.out.println("현재 점수 합계 : [1번 : " + totalScores[0] + " / 2번 : " + totalScores[1] + "]");
			round++;
		}
	}

	// 메인 메소드
	public static void main(String args[]) {
		// 메뉴 흐름 제어용
		int status = 1;
		Scanner sc = new Scanner(System.in);
		String[] playerName = new String[2];

		System.out.println("======================");
		System.out.println("1. 플레이어 등록");
		System.out.println("0. 종료");
		System.out.println("======================");
		System.out.print("작업을 선택하세요 : ");
		status = Integer.parseInt(sc.nextLine());

		while (status != 0) {
			// 플레이어 등록은 처음 한 번만 실행되어야지
			if (status == 1) {
				System.out.print("1번 플레이어 이름을 입력하세요 : ");
				playerName[0] = sc.nextLine();
				System.out.print("2번 플레이어 이름을 입력하세요 : ");
				playerName[1] = sc.nextLine();

				// 게임 루프
				while (status != 0) {
					System.out.println("======================");
					System.out.println("1. 게임 시작");
					System.out.println("2. 기록 열람");
					System.out.println("0. 종료");
					System.out.println("======================");
					System.out.print("작업을 선택하세요 : ");
					status = Integer.parseInt(sc.nextLine());

					if (status == 1) {
						// 플레이어 등록 이후에는 종료 전까지 게임만 플레이 되도록
						showPlay(playerName[0], playerName[1]);
						if (isGameOver) {
							System.out.println("즉사로 인해 게임 종료!");
							status = 0; // while 루프 완전히 종료
							break;
						}
					} else if (status == 2) {
						//기록 보여주기
						showLog();
					} else {
						// 게임 종료
						break;
					}
				}
			}
		}

		System.out.println("게임을 종료합니다.");
	}
}
