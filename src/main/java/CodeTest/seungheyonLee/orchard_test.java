package CodeTest.seungheyonLee;
import java.util.Scanner;

public class orchard_test {
    public static Scanner scan = new Scanner(System.in);
    public static final int THIRD_DICE_INDEX = 2;
    public static final int TOTAL_SCORE_INDEX = 3;
    public static final int DICE_COUNT =3;
    public static final int MinDiceValue=1;
    public static final int MaxDiceValue=6;
    public static void main(String[] args) {
        int[][][] gameRecord=new int[2][100][4];//객체적용전 임시용 1,2,3번째,총합
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 100; j++) {
                //초기값0에서 오류발생 방지용 --본프로젝트에서는 쓸일 절대없음
                gameRecord[i][j][THIRD_DICE_INDEX] = -1; // 3번째 주사위 초기화
            }
        }

        String Player1=inputPlayerName(1);
        String Player2=inputPlayerName(2);
        System.out.println(Player1+" vs "+Player2);

        int gameCount=0;
        while (true){
            int[] scores=new int[2];
            System.out.println((gameCount+1)+"번째 게임");
            for(int diceIndex = 0; diceIndex< DICE_COUNT; diceIndex++){
                System.out.println((diceIndex+1)+"번째 주사위 결과");
                if(diceIndex==THIRD_DICE_INDEX) {
                    handleThirdDice(gameRecord, gameCount, diceIndex, Player1, Player2,scores);
                    continue;
                }
                //gameRecord,player(n)번째 유저,gameCount번째 게임,i번째 주사위
                int player1Roll=rollAndRecord(gameRecord,1,gameCount,diceIndex,Player1);
                int player2Roll=rollAndRecord(gameRecord,2,gameCount,diceIndex,Player2);
                System.out.println();

                scores[0]+=player1Roll;
                scores[1]+=player2Roll;


            }
            updateTotalScore(gameRecord,gameCount,scores);
            //gameRecord[1][gameCount][TOTAL_SCORE_INDEX]이나 scores[1]이나 같은값이잖아?
            System.out.println(Player1+"의 총합 : "+scores[0]+" , "+Player2+"의 총합 : "+scores[1]+" 으로");
            System.out.println((gameCount+1)+"번째 게임 결과는 : "+determineWinner(gameRecord,gameCount,Player1,Player2));
            boolean isContinue=handleMenuInput(gameRecord,Player1,Player2,gameCount);
            if(!isContinue){//handleMenuInput 결과값이 종료이면
                System.out.println("게임을 종료합니다");
                break;
            }
            System.out.println("======================================");
            System.out.println("다음게임을 시작합니다");
            System.out.println("======================================");
            gameCount++;
        }
    }
    public static String determineWinner(int[][][] gameRecord, int gameIndex, String Player1, String Player2) {
        int p1Score = gameRecord[0][gameIndex][TOTAL_SCORE_INDEX];
        int p2Score = gameRecord[1][gameIndex][TOTAL_SCORE_INDEX];
        if (p1Score > p2Score) {
            return Player1 + " 승리";
        } else if (p2Score > p1Score) {
            return Player2 + " 승리";
        } else {
            return "무승부";
        }
    }


    public static void updateTotalScore(int[][][] gameRecord,int gameCount,int[]scores){
        gameRecord[0][gameCount][TOTAL_SCORE_INDEX]=scores[0];//총합점수 입력
        gameRecord[1][gameCount][TOTAL_SCORE_INDEX]=scores[1];
    }
    public static void handleThirdDice(int[][][] gameRecord,int gameCount,int diceIndex,String Player1,String Player2,int[]scores){
        boolean isPlayer1Even=gameRecord[0][gameCount][0]%2==0;
        boolean isPlayer2Even=gameRecord[1][gameCount][0]%2==0;
        int player1Roll = -1;
        int player2Roll = -1;
        if(!isPlayer1Even){
            System.out.print(Player1+"의 3번째 주사위 미발동");
        }else{
            player1Roll=rollAndRecord(gameRecord,1,gameCount,THIRD_DICE_INDEX,Player1);
        }
        System.out.print(" , ");
        if(!isPlayer2Even){
            System.out.print(Player2+"의 3번째 주사위 미발동");
        }else{
            player2Roll=rollAndRecord(gameRecord,2,gameCount,THIRD_DICE_INDEX,Player2);
        }
        System.out.println();

        //특수효과적용
        if(player1Roll==3){
            scores[0]+=scores[1];
            scores[1]=0;//상대점수 제거
        }else if(player2Roll==3){
            scores[1]+=scores[0];
            scores[0]=0;
        }
        if(player1Roll==4){
            scores[0]=0;
        }else if(player2Roll==4){
            scores[1]=0;
        }
        //들어갈일이 없지만 혹시나 정말로 진짜 모르니까 -1제외처리
        if(isNormalRoll(player1Roll))scores[0]+=player1Roll;
        if(isNormalRoll(player2Roll))scores[1]+=player2Roll;
    }
    private static boolean isNormalRoll(int roll) {
        return roll != -1 && roll != 3 && roll != 4;
    }
    //gameRecord,player(n)번째 유저,gameCount번째 게임,i번째 주사위
    public static int rollAndRecord(int[][][] gameRecord,int playerIndex,int gameCount,int diceIndex,String playerName){
        int roll=randomNumber();
        gameRecord[playerIndex-1][gameCount][diceIndex]=roll;
        System.out.print(playerName+" : "+roll+"     ");
        return roll;
    }
    public static String inputPlayerName(Integer Player_count){
        System.out.print(Player_count+"번째 플레이어 이름 : ");
        try{
             return scan.nextLine();
        }catch(Exception e){
            System.out.println("잘못된 입력값이잖아 다시입력해");
            return inputPlayerName(Player_count);
        }
    }
    public static boolean handleMenuInput(int[][][] gameRecord,String Player1,String Player2,Integer gameCount){
        System.out.println("계속할거면 1\n기록출력은 2\n종료는 3");
        String input=scan.nextLine();
        switch (input) {
            case "1" -> {
                return true;
            }
            case "2" -> {
                //한번도 게임안한거 쳐내기위함
                if (gameRecord[0][0][0] == 0 && gameRecord[1][0][0] == 0) {
                    System.out.println("한번도 게임을 안했잖아 다시 입력해");
                    return handleMenuInput(gameRecord, Player1, Player2, gameCount);
                }
                System.out.println("기록 출력");
                printRecord(gameRecord, Player1, Player2, gameCount);
                return handleMenuInput(gameRecord, Player1, Player2, gameCount);
            }
            case "3" -> {
                return false;
            }
            default -> {
                System.out.println("잘못된입력이잖아");
                return handleMenuInput(gameRecord, Player1, Player2, gameCount);
            }
        }
    }
    public static int randomNumber(){
        return (int)(Math.random()*(MaxDiceValue-MinDiceValue+1))+MinDiceValue;
    }
    public static void printRecord(int[][][] gameRecord,String Player1,String Player2,Integer gameCount){
        //gameCount는 1부터 시작이니까 +1
        for(int round=0;round<gameCount+1;round++){
            System.out.println((round+1)+"번째 게임 결과");
            for(int diceIndex = 0; diceIndex< DICE_COUNT; diceIndex++){
                System.out.println((diceIndex+1)+"번째 주사위 결과");
                //배열의 경우 기본값을 0으로 잡기에 입력이 없어도 0으로 값이 안들어온걸로 처리가능
                if(diceIndex==THIRD_DICE_INDEX){
                    if(gameRecord[0][round][diceIndex]==-1){
                        System.out.print(Player1+"의 3번째 주사위 미발동");
                    }else{
                        System.out.print(Player1+" : "+gameRecord[0][round][diceIndex]);
                    }
                    System.out.print(" , ");
                    if(gameRecord[1][round][diceIndex]==-1){
                        System.out.print(Player2+"의 3번째 주사위 미발동");
                    }else{
                        System.out.print(Player2+" : "+gameRecord[1][round][diceIndex]);
                    }
                    System.out.println();
                    continue;
                }

                System.out.println(Player1+" : "+gameRecord[0][round][diceIndex]+" , "+Player2+" : "+gameRecord[1][round][diceIndex]);
            }
            System.out.println(Player1+"의 총합 : "+gameRecord[0][round][TOTAL_SCORE_INDEX]+" , "+Player2+"의 총합 : "+gameRecord[1][round][TOTAL_SCORE_INDEX]+" 으로");
            System.out.println((round+1)+"번째 게임 결과는 : "+determineWinner(gameRecord,round,Player1,Player2));
            System.out.println("======================================");
        }
    }
}
