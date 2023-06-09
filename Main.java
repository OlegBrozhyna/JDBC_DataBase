package MarketPlaceDB;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {


    private static final String DB_URL = "jdbc:mysql://localhost/marketplacedb?";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "982210QwE";

    private final static Scanner scanner = new Scanner(System.in);
    private static MarketPlaceDB marketPlaceDB;

    public static void main(String[] args) {
        try {
            try {
                marketPlaceDB = MarketPlaceDB.getInstance();
                marketPlaceDB.connect(DB_URL, DB_USER, DB_PASSWORD);
                menu();
            } finally {
                scanner.close();
                marketPlaceDB.disconnect();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void menu() throws SQLException {
        while (true) {
            System.out.println("-------DataBase--------");
            System.out.println("1: new client");
            System.out.println("2: new product");
            System.out.println("3: new order");
            System.out.println("4: view clients");
            System.out.println("5: view products");
            System.out.println("6: view orders");
            System.out.println("0: exit");
            System.out.println("-+-+-+-+-+-+-+-+-+-");
            System.out.print("-> ");
            String menuNumber = scanner.nextLine();
            switch (menuNumber) {
                case "1":
                    addClient();
                    System.out.println("The new client is added.\n");
                    break;
                case "2":
                    addProduct();
                    System.out.println("The new product is added.\n");
                    break;
                case "3":
                    addOrder();
                    System.out.println("The new order is added.\n");
                    break;
                case "4":
                    viewClients();
                    break;
                case "5":
                    viewProducts();
                    break;
                case "6":
                    viewOrders();
                case "0":
                    return;
                default:
                    System.err.println("Unknown command! Try again...");
            }
        }
    }

    private static void addClient() throws SQLException {
        System.out.print("Enter name: ");
        final String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        final String surname = scanner.nextLine();
        System.out.print("Enter phone: ");
        final String phone = scanner.nextLine();
        final Client client = new Client(name, surname, phone);
        marketPlaceDB.insertClient(client);
    }

    private static void addProduct() throws SQLException {
        System.out.print("Enter name: ");
        final String name = scanner.nextLine();
        System.out.print("Enter price: ");
        final double price = scanner.nextDouble();
        System.out.print("Enter information: ");
        final String information = scanner.nextLine();
        final Product product = new Product(name, price, information);
        marketPlaceDB.insertProduct(product);
    }

    private static void addOrder() throws SQLException {
        System.out.println("Choose a user's number!");
        viewClients();
        System.out.print("Your choice id = ");
        int clientId = scanner.nextInt();
        System.out.println("Choose a product's number!");
        viewProducts();
        System.out.print("Your choice id = ");
        int productId = scanner.nextInt();
        System.out.println("Write a comment: ");
        String comment = scanner.nextLine();
        final Order order = new Order(clientId, productId, comment);
        marketPlaceDB.insertOrder(order);
    }

    private static void viewClients() throws SQLException {
        final String clients = marketPlaceDB.getClientsTable();
        System.out.println(clients);
    }

    private static void viewProducts() throws SQLException {
        final String products = marketPlaceDB.getProductsTable();
        System.out.println(products);
    }

    private static void viewOrders() throws SQLException {
        final String orders = marketPlaceDB.getOrdersTable();
        System.out.println(orders);
    }
}