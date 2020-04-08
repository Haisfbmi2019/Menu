package reposiitory;

import java.util.Scanner;

public interface DAO {
    void add(Scanner scanner);
    void delete(Scanner scanner);
    void viewMenu();
    void findByPrice(Scanner scanner);
    void findByDiscount();
    void menuWeight(Scanner scanner);

}