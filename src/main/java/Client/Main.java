package Client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Podaj adres: ");
        String adres = sc.nextLine();
        System.out.println("Podaj port");
        int port = sc.nextInt();

        ConnectionHandler ch = new ConnectionHandler(adres, port);



    }
}
