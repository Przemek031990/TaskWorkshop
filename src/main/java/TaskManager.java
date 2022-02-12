
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static String NAZWA_PLIKU = "tasks.csv";
    public static String[][] tabZadan;
    public static String[] opcje = {"add", "remove", "list", "exit"};

    public static void wydrukOpcji(String[] arr) {
        for (String opcja : arr) {
            System.out.println(ConsoleColors.WHITE + opcja);
        }
    }

    public static int iloscWierszy(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        int lines = 0;
        while (scan.hasNextLine()) {
            scan.nextLine();
            lines++;
        }
        scan.close();
        return lines;
    }

    public static String[][] pobieranie(String fileName) throws FileNotFoundException {
        int ilosc = iloscWierszy(NAZWA_PLIKU);
        String[][] zPliku = new String[ilosc][3];
        String[] temp = new String[ilosc];
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        for (int i = 0; i < ilosc; i++) {
            temp[i] = (scan.nextLine());
            String[] split = temp[i].split(",");
            for (int j = 0; j < temp[i].split(",").length; j++) {
                zPliku[i][j] = split[j];
            }

        }
        return zPliku;
    }

    public static void wydrukListy(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();

        }
    }

    public static String[][] dodaj() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        String tresc = (scan.nextLine().toLowerCase());
        System.out.println("Please add task due date");
        String data = (scan.nextLine().toLowerCase());
        System.out.println("Is your task is important: true/false");
        String wazne = (scan.nextLine());
        tabZadan = Arrays.copyOf(tabZadan, tabZadan.length + 1);
        tabZadan[tabZadan.length - 1] = new String[3];
        tabZadan[tabZadan.length - 1][0] = tresc;
        tabZadan[tabZadan.length - 1][1] = data;
        tabZadan[tabZadan.length - 1][2] = wazne;
        return tabZadan;
    }

    public static void zapis(String[][] arr, String nadpisz) throws FileNotFoundException {
        String[] doPliku = new String[arr.length];
        PrintWriter napisz = new PrintWriter(nadpisz);
        for (int i = 0; i < arr.length; i++) {
            doPliku[i] = String.join(",", arr[i]);
            napisz.println(doPliku[i]);

        }
        napisz.close();
    }

    public static String[][] kasowanie(String[][] arr) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        int doUsuniecia = scan.nextInt();
        String[][] naChwile = new String[arr.length - 1][3];
        int nowyIndeks = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != doUsuniecia) {
                for (int j = 0; j < arr[i].length; j++) {
                    naChwile[nowyIndeks][j] = arr[i][j];
                }
                nowyIndeks++;
            }

        }
        tabZadan = Arrays.copyOf(naChwile, tabZadan.length - 1);
        return tabZadan;
    }

    public static void main(String[] args) throws FileNotFoundException {
        pobieranie(NAZWA_PLIKU);
        tabZadan = pobieranie(NAZWA_PLIKU);

        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        wydrukOpcji(opcje);

        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {

            String zKonsoli = (scan.nextLine().toLowerCase());
            switch (zKonsoli) {
                case "list":
                    wydrukListy(tabZadan);
                    break;
                case "add":
                    dodaj();
                    break;
                case "remove":
                    kasowanie(tabZadan);
                    System.out.println(ConsoleColors.WHITE + "Value was successfully deleted.");
                    break;
                case "exit":
                    zapis(tabZadan, NAZWA_PLIKU);
                    System.out.println(ConsoleColors.RED + "Bye, bye");
                    System.exit(0);
                default:
                    System.out.println("Incorrect argument passed. Please give number greater or equal 0.");
            }
            System.out.println(ConsoleColors.BLUE + "Wybierz opcje:");
            wydrukOpcji(opcje);
        }
    }


}
