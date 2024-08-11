package Level2;

import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        String myBavli = MyFuncLevel2.createBavliDirctory(); // יצירת תיקיות השס וקבלת נתיב
        boolean flag = true;
        while (flag){
            System.out.println("Press 1 to print page: ");
            System.out.println("Press 2 to exit: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    int b = MyFuncLevel2.searchBook();
                    MyFuncLevel2.searchPage2(b);
                    break;
                case 2:
                    flag = false;
                    break;
            }
        }

    }
}
