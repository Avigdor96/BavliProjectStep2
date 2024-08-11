package Level2;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class MyFuncLevel2 {
    // מקבלת מחרוזת ומחזירה מאיזה סוג היא
    public static String typeLine(String line) {
        if (line.isEmpty()) {
            return "Empty";
        }
        if (line.startsWith("מסכת ") && line.contains("פרק א")) {
            return "Book";
        }
        if (line.startsWith("דף ") && (line.endsWith(" א") || line.endsWith(" ב")) && (line.split(" ").length < 4)) {
            return "Page";
        }
        if (line.startsWith("גמרא") || line.startsWith("משנה")) {
            return "Content";
        }
        return "";
    }
    // יוצרת תיקיות של מסכתות בתוכן תיקיות דפים ובתוכן קבצי טקסט של עמודים
    public static String createBavliDirctory(){
        String path = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli";
        File base = new File("C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli");
        if (!base.exists()) {
            base.mkdir();

            String textPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\__תלמוד בבלי טקסט_.txt";
            File fileOfText = new File(textPath);
            String currentBook = "";
            String currentFatherPage = "";
            String currentPage = "";
            try {
                Scanner scanner = new Scanner(fileOfText);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    switch (MyFuncLevel2.typeLine(line)) {
                        case "Book":
                            String correctBookName = MyFuncLevel2.correctBookName(line);
                            File newBook = new File(base, correctBookName);
                            if (!newBook.exists()) {
                                newBook.mkdir();
                            }
                            currentBook = newBook.getAbsolutePath().trim();
                            break;
                        case "Page":
                            File fatherPage = new File(currentBook, MyFuncLevel2.correctNamePage(line));
                            if (!fatherPage.exists()) {
                                fatherPage.mkdir();
                            }

                            currentFatherPage = currentBook + "/" + fatherPage.getName();
//
                            File file = new File(currentFatherPage, line);
                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            currentPage = file.getAbsolutePath().trim();
                            break;


                        case "Content":
                            FileWriter fw = new FileWriter(currentPage, true);
                            fw.write(line + "\n");
                            fw.close();
                            break;

                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return path;
    }

    // מקבלת מחרוזת של תחילת מסכת ומחזירה את שם המסכת
    public static String correctBookName(String line){
        return line.substring(0, line.indexOf(" פרק"));
    }

    // מקבלת מחרוזת שם של דף ומחזירה את שם הדף תקין
    public static String correctNamePage(String line) {
        return line.split(" ")[0] + " " + line.split(" ")[1];
    }

    // חיפוש מסכת בתיקיית הבסיס באמצעות מערך ומחזירה את מיקומו אם קיים, אם לא קיים מחזירה לתחילת הפונקציה
    public static int searchBook() {
        Scanner scanner = new Scanner(System.in);
        boolean bookFound = false;
        int loc = -1;
        System.out.println("Enter name of Book: ");
        String book = "מסכת " + scanner.nextLine();
        String myPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli";
        File file = new File(myPath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(book)) {
                bookFound = true;
                loc = i;
            }
        }
        if (loc == -1){ // אם הספר לא נמצא חזרה לתחילת הפונקציה
            System.out.println("Book is not found enter again.");
            return searchBook();
        }
        return loc;
    }

    // מקבלת מיקום ספר מבקשת דף ומחפשת אותו בתיקיית המסכת באמצעות מערך
    // ומדפיסה את התוכן. אם לא נמצא מחזירה לתחילת הפונקציה
    public static void searchPage2(int locBook){
        int locPage = - 1;
        Scanner scanner = new Scanner(System.in);
        boolean pageFound = false;
        System.out.println("Enter page: ");
        String page = "דף " + scanner.nextLine();
        String myPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli";
        File file = new File(myPath);
        File[] files = file.listFiles();
        assert files != null;
        File[] filesCurrentBook = files[locBook].listFiles(); // יצירת מערך של המסכת המבוקשת
        for (int i = 0; i < filesCurrentBook.length; i++) {
            if (page.equals(filesCurrentBook[i].getName())){
                pageFound = true;
                locPage = i;  // מיקום הדף
            }
        }

        if (locPage == - 1){ // אם הדף לא נמצא
            System.out.println("Page is not found enter again.");
            searchPage2(locBook);
            return;
        }
        File [] files1 = filesCurrentBook[locPage].listFiles(); // מערך קבצים של עמודי הדף המבוקש
        for (int i = 0; i < files1.length; i++) {
            try {
                Scanner scanner1 = new Scanner(files1[i]);
                while (scanner1.hasNextLine()){
                    String line = scanner1.nextLine();
                    System.out.println(line);
                }

            }catch (Exception e){
                System.out.println(e);
            }


        }


    }

//    public static void main(String[] args) {
////        String myPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli";
////        File file = new File(myPath);
////        File[] books = file.listFiles();
////        assert books != null;
////        File currentBook = books[searchBook()];
////        File[] pages = currentBook.listFiles();
//        int b = searchBook();
//        searchPage2(b);
//    }
//    public static void searchPage() {
//        Scanner scan = new Scanner(System.in);
//        boolean bBook = true;
//        boolean bPage = true;
//        System.out.println("Enter name of Book: ");
//        String book = "מסכת " + scan.nextLine();
//        System.out.println("Enter page: ");
//        String page = "דף " + scan.nextLine();
//        String myPath = "C:\\Users\\אביגדור\\OneDrive\\שולחן העבודה\\Sorted Bavli";
//        File file = new File(myPath);
//        File[] files = file.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            if (files[i].getName().equals(book)) {
//                bBook = false;
//                File[] currentBook = files[i].listFiles();
//                for (int j = 0; j < currentBook.length; j++) {
//                    if (currentBook[j].getName().equals(page)) {
//                        bPage = false;
//                        File[] files1 = currentBook[j].listFiles();
//                        for (int k = 0; k < files1.length; k++) {
//                            File file1 = new File(files1[k].getAbsolutePath());
//                            try {
//                                Scanner scanner = new Scanner(file1);
//                                while (scanner.hasNextLine()) {
//                                    String line = scanner.nextLine();
//                                    System.out.println(line);
//                                }
//                            } catch (Exception e) {
//                                System.out.println(e);
//                            }
//                        }
//                    }
//                }
//                if (bPage) {
//                    System.out.println("Page not founded");
//                    System.out.println("Enter again: ");
//                    searchPage();
//                }
//            }
//        }
//        if (bBook) {
//            System.out.println("Book not founded Enter again: ");
//            searchPage();
//        }
//    }
}
