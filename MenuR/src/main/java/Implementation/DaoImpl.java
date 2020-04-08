package Implementation;

import entity.Menu;
import reposiitory.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

public class DaoImpl implements DAO {
    EntityManagerFactory emf;
    EntityManager em;

    public DaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("MenuR");
        this.em = emf.createEntityManager();
    }

    @Override
    public void add(Scanner scanner) {
        System.out.println("Will this dish be with discount?(yes/no)");
        String response = scanner.nextLine();
        boolean discount = false;
        if (response.equals("yes") || response.equals("y")) {
            discount = true;
        }
        System.out.println("Enter dish name: ");
        String dish = scanner.nextLine();
        System.out.println("Enter cost of dish: ");
        int cost = scanner.nextInt();
        System.out.println("Enter weight of dish: ");
        int weight = scanner.nextInt();

        em.getTransaction().begin();
        try {
            Menu menu = new Menu(dish, cost, weight, discount);
            em.persist(menu);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Enter dish id: ");
        String dishId = scanner.nextLine();
        int id = Integer.parseInt(dishId);

        Menu menu = em.find(Menu.class, id);
        if (menu == null) {
            System.out.println("Client not found!");
            return;
        }
        em.getTransaction().begin();
        try {
            em.remove(menu);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    @Override
    public void viewMenu() {
        Query query = em.createQuery("SELECT m FROM Menu m", Menu.class);
        List<Menu> resultList = query.getResultList();
        for (Menu temp : resultList) {
            System.out.println(temp);
        }
    }

    @Override
    public void findByPrice(Scanner scanner) {
        System.out.println("Enter min cost: ");
        int minPrice = scanner.nextInt();
        System.out.println("Enter max cost: ");
        int maxPrice = scanner.nextInt();
        List<Menu> resultList;
        Query query = em.createQuery("SELECT m FROM Menu m WHERE (m.price> :minPrice)AND (m.price<:maxPrice)", Menu.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        resultList = query.getResultList();
        for (Menu temp : resultList) {
            System.out.println(temp);
        }
    }

    @Override
    public void findByDiscount() {
        List<Menu> resultList;
        Query query = em.createQuery("SELECT m from Menu m where m.discount = true", Menu.class);
        resultList = query.getResultList();
        for (Menu temp : resultList) {
            System.out.println(temp);
        }
    }

    @Override
    public void menuWeight(Scanner scanner) {
        List<Menu> resultOrder = new ArrayList<>();
        Menu dish;
        double template = 0;
        int maxWeight = 1000;
        int temp = 0;
        while (true) {
            System.out.println("Choose the dish id: ");
            long id = scanner.nextLong();
            try {
                dish = em.find(Menu.class, id);
                template += dish.getWeight();
            } catch (Exception e) {
                System.out.println("Invalid id");
                continue;
            }
            if (template <= maxWeight) {
                resultOrder.add(dish);
            } else {
                System.out.println("Total weight more than 1 kg!");
                break;
            }
        }
        System.out.println("Your order:");
        for (Menu order: resultOrder) {
            System.out.println(order);
            temp+=order.getWeight();
        }
        System.out.println("------------------------------");
        System.out.println("Total weight of your order: " + temp);
    }
}

