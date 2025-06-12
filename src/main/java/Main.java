import java.util.Scanner;

public class Main {
    // 게임 중단 조건
    static boolean isGameOver = false;

    public static void main(String[] args) {
        // 메뉴 흐름 제어용
        int status = 1;

        Scanner sc = new Scanner(System.in);

        System.out.println("======================");
        System.out.println("1. 플레이어 등록");
        System.out.println("0. 종료");
        System.out.println("======================");
        System.out.print("작업을 선택하세요 : ");
        status = Integer.parseInt(sc.nextLine());

        while (status != 0) {
            System.out.println("======================");
            System.out.println("1. 게임 시작");
            System.out.println("2. 기록 열람");
            System.out.println("0. 종료");
            System.out.println("======================");
            System.out.print("작업을 선택하세요 : ");
            status = Integer.parseInt(sc.nextLine());


            if(status == 1) {
                //게임 시작
            } else if(status == 2) {
                //기록 열람
            } else {
                break; //게임 종료
            }
        }

        System.out.println("게임을 종료합니다.");
    }
}