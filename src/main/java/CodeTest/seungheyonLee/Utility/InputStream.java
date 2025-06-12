package CodeTest.seungheyonLee.Utility;

import java.util.Scanner;

public class InputStream {
    private static Scanner scanner = new Scanner(System.in);
    private InputStream() {}//외부에서 건드리지못하게 private설정


    public static String getStringInput(String s){
        System.out.print(s+": ");
        return scanner.nextLine();
    }
    public static int getIntInput(String s){
        System.out.print(s+": ");
        return Integer.parseInt(scanner.nextLine());
    }
}
