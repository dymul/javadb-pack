package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.WeakHashMap;

public class ClassicModelsManager {
    private static Logger logger = LoggerFactory.getLogger(ClassicModelsManager.class);

    ConnectionFactory myDB;

    {
        try {
            myDB = new ConnectionFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void printAllOffices() {
        try (Statement statement = myDB.getConnection().createStatement()) {
            String query = "SELECT * FROM offices";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                int officeCode = result.getInt("officeCode");
                String city = result.getString("city");
                String phone = result.getString("phone");
                String addressLine1 = result.getString("addressLine1");
                String addressLine2 = result.getString("addressLine2");
                String state = result.getString("state");
                String country = result.getString("country");
                String postalCode = result.getString("postalCode");
                logger.info("id: {}, city: {}, phone: {}, addressLine1: {}, addressLine2: {}, state: {}, country: {}, postalCode: {}",
                        officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrices() {
        try (Statement statement = myDB.getConnection().createStatement()) {
            String query = "UPDATE products SET buyPrice = buyPrice*1.1";
            statement.executeUpdate(query);
            query = "SELECT productName, buyPrice FROM products";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {

                String name = result.getString("productName");
                BigDecimal price = result.getBigDecimal("buyPrice");

                logger.info("price1: {}, productName: {}", price, name);
            }
            query = "UPDATE products SET buyPrice = buyPrice/1.1";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printAllCustomersWithSalesRepName() {
        try (Statement statement = myDB.getConnection().createStatement()) {
            String query = "SELECT customerName, firstName, lastName FROM customers LEFT JOIN employees on customers.salesRepEmployeeNumber = employees.employeeNumber ORDER BY customerName";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String customerName = result.getString("customerName");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                logger.info("customerName: {}, e_firstName: {}, e_lastName: {}", customerName, firstName, lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Product> findProductByName(String nameMatcher){

        List<Product> productList = new ArrayList<>();
        String query = "select * FROM products WHERE productName LIKE LOWER (?)";

        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.setString(1, "%"+nameMatcher+"%");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                String productCode = result.getString("productCode");
                String productName = result.getString("productName");
                String productLine = result.getString("productLine");
                String productScale = result.getString("productScale");
                String productVendor = result.getString("productVendor");
                String productDescription = result.getString("productDescription");
                int quantityInStock = result.getInt("quantityInStock");
                BigDecimal buyPrice = result.getBigDecimal("buyPrice");
                double MSRP = result.getDouble("MSRP");
                productList.add(new Product(productCode,productName,productLine,productScale,productVendor,productDescription,
                        quantityInStock, buyPrice, MSRP));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Product a: productList) {
            System.out.println(a.toString());
        }

        return productList;
    }

    public List<Order> findOrdersByEmloyeeId(int id){
        List<Order> orderList = new ArrayList<>();

        String query = "select orderNumber, orderDate, requiredDate, shippedDate, status, comments, customers.customerNumber from employees left join customers on customers.salesRepEmployeeNumber = employees.employeeNumber\n" +
                "left join orders on customers.customerNumber = orders.customerNumber where employeeNumber =?";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                int orderNumber = result.getInt("orderNumber");
                java.sql.Date orderDate = result.getDate("orderDate");
                java.sql.Date requiredDate = result.getDate("requiredDate");
                java.sql.Date shippedDate = result.getDate("shippedDate");
                String status= result.getString("status");
                String comments = result.getString("comments");
                int customerNumber = result.getInt("customers.customerNumber");

                orderList.add(new Order(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Order a: orderList) {
            System.out.println(a.toString());
        }

        return orderList;
    }

    public List<Order> findOrdersByDate(Date from, Date to){
        List<Order> orderList = new ArrayList<>();

        String query = "select * from orders where orderDate between ? and ?";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.setDate(1,from);
            statement.setDate(2,to);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                int orderNumber = result.getInt("orderNumber");
                java.sql.Date orderDate = result.getDate("orderDate");
                java.sql.Date requiredDate = result.getDate("requiredDate");
                java.sql.Date shippedDate = result.getDate("shippedDate");
                String status= result.getString("status");
                String comments = result.getString("comments");
                int customerNumber = result.getInt("customerNumber");

                orderList.add(new Order(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Order a: orderList) {
            System.out.println(a.toString());
        }

        return orderList;

    }



    public static void main(String[] args) throws SQLException {

     ClassicModelsManager model = new ClassicModelsManager();
//      ProductDao product = new ProductImpl();
//      Product product1 = new Product("678_uhy", "Audi A6",
//              "Planes",
//              "1:10",
//              "Min Lin Diecast",
//              "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.",
//              7453, BigDecimal.valueOf(89.99), 109.70);

     // product.save(product1);
      //product.update(product1);
    // product.find("S10_1678");
       // product.findAll();


        Scanner sc = new Scanner(System.in);
        System.out.println("Wybierz zestaw danych: \n" +
                "1 - informacje o biurach \n" +
                "2 - ceny po 10% podwyżce \n" +
                "3 - klienci i ich przedstawiciele handlowi \n" +
                "4 - lista produktów zawierających w nazwie podane hasło \n" +
                "5 - lista zamówień klientów obsługiwanych przez przedstawiciela o podanym id \n" +
                "6 - lista zamówień złożonych pomiędzy podanymi datami \n" +
                "7 - zakończ");

        int choice = sc.nextInt();

        while (choice != 7) {
            if (choice == 1) {
                model.printAllOffices();
            } else if (choice == 2) {
                model.updateProductPrices();
            } else if (choice == 3) {
                model.printAllCustomersWithSalesRepName();
            } else if (choice == 4){
                System.out.println("Podaj hasło wyszukiwania");
                String st = sc.next();
                model.findProductByName(st);
            } else if (choice == 5){
                System.out.println("Podaj Id pracownika");
                int id = sc.nextInt();
                model.findOrdersByEmloyeeId(id);
            } else if (choice == 6){
                System.out.println("Od:");
                String from = sc.next();

                System.out.println("Do: ");
                String to = sc.next();
                model.findOrdersByDate(Date.valueOf(from), Date.valueOf(to));
            } else {
                System.out.println("Wybierz odpowiednie pole");
            }
            System.out.println();
            System.out.println("Wybierz zestaw danych: \n" +
                    "1 - informacje o biurach \n" +
                    "2 - ceny po 10% podwyżce \n" +
                    "3 - klienci i ich przedstawiciele handlowi \n" +
                    "4 - lista produktów zawierających w nazwie podane hasło \n" +
                    "5 - lista zamówień klientów obsługiwanych przez przedstawiciela o podanym id \n" +
                    "6 - lista zamówień złożonych pomiędzy podanymi datami \n" +
                    "7 - zakończ");
            choice = sc.nextInt();
        }
}


}
