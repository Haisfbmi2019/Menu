import Implementation.DaoImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        DaoImpl dao = new DaoImpl();
        int command = 0;
        while (true) {
            System.out.print("1. Add new dish to menu\n2. Delete dish from menu\n3. Show all\n4. Find by price\n5. Find by discount\n" +
                    "6. Choose a set of dishes\n7. Exit\n>> ");
            command = in.nextInt();
            switch (command) {
                case 1: {
                    dao.add(in);
                }
                break;
                case 2: {
                    dao.delete(in);
                }
                break;
                case 3: {
                    dao.viewMenu();
                }
                break;
                case 4: {
                    dao.findByPrice(in);
                }
                break;
                case 5: {
                    dao.findByDiscount();
                }
                break;
                case 6: {
                    dao.menuWeight(in);
                }
                break;
                case 7: {
                    System.exit(0);
                }
                default:
                    System.out.println("Wrong Command");
            }
        }
    }
}
