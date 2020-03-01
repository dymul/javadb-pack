package pl.sda.jdbc.starter;

import com.mysql.cj.protocol.Resultset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ClassicModelsManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static Scanner sc = new Scanner(System.in);
    private static java.sql.Date from;
    private static java.sql.Date to;


    ConnectionFactory connectionFactory;

    public ClassicModelsManager() throws SQLException {
        connectionFactory = new ConnectionFactory();
    }

    public void printAllOffices() throws Exception {

        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM offices");
            while (result.next()) {
                String officeCode = result.getString("officeCode");
                String city = result.getString("city");
                String phone = result.getString("phone");
                String addressLine1 = result.getString("addressLine1");
                String addressLine2 = result.getString("addressLine2");
                String state = result.getString("state");
                String country = result.getString("country");
                String postalCode = result.getString("postalCode");
                String territory = result.getString("territory");

                System.out.println(officeCode + " " + city + " " + phone + " " + addressLine1
                        + " " + addressLine2 + " " + state + " " + country + " " + postalCode + " " + territory);
            }
        } catch (Exception e) {
            logger.error("Error during creating offices statement");
        }

    }

    public void updateProductPrices() throws Exception {
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            statement.executeUpdate("UPDATE products SET buyPrice = ROUND(buyPrice * 1.1, 2)");
        } catch (Exception e) {
            logger.error("Error during updating price");
        }
    }

    public void printAllCustomersWithSalesRepName() {

        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            ResultSet result = statement.executeQuery("SELECT contactFirstName as Customer_First_Name, contactLastName as Customer_Last_Name," +
                    " firstName as Sales_Representative_First_Name, lastName as Sales_Representative_Last_Name FROM customers\n" +
                    " LEFT JOIN employees on customers.salesRepEmployeeNumber = employees.employeeNumber");
            while (result.next()) {

                String Customer_First_Name = result.getString("Customer_First_Name");
                String Customer_Last_Name = result.getString("Customer_Last_Name");
                String Sales_Representative_First_Name = result.getString("Sales_Representative_First_Name");
                String Sales_Representative_Last_Name = result.getString("Sales_Representative_Last_Name");

                System.out.println("Customer First Name: " + Customer_First_Name + " " + " Customer Last Name: " + Customer_Last_Name + "\n" +
                        "Sales Representative First Name: " + Sales_Representative_First_Name + " Sales Representative Last Name: "
                        + Sales_Representative_Last_Name);

            }
        } catch (Exception e) {
            logger.error("Error during creating customers with sales representatives statement");
        }

    }

    public List<Product> findProductByName(String nameMatcher) {

        List<Product> productList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("SELECT * FROM products" +
                " WHERE products.name LIKE ?")) {

            preparedStatement.setString(1, "%" + nameMatcher + "%");
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Product product = new Product();
                product.setBuyPrice(result.getBigDecimal("buyPrice"));
                product.setMSRP(result.getBigDecimal("MSRP"));
                product.setProductCode(result.getString("productCode"));
                product.setProductName(result.getString("productName"));
                product.setProductDescription(result.getString("productDescription"));
                product.setProductLine(result.getString("productLine"));
                product.setProductScale(result.getString("productScale"));
                product.setProductVendor(result.getString("productVendor"));
                product.setQuantityInStock(result.getString("quantityInStock"));
                productList.add(product);
            }
        } catch (Exception e) {
            logger.error("Error during finding products");
        }
        return productList;
    }

    public List<Order> findOrdersByEmloyeeId(int id) {
        List<Order> orderList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("SELECT * FROM orders" +
                " LEFT JOIN customers on orders.customerNumber = customers.customerNumber" +
                " LEFT JOIN employees on customers.salesRepEmployeeNumber = employees.employeeNumber WHERE" +
                " employeeNumber LIKE ?")) {
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Order order = new Order();
                order.setComments(result.getString("comments"));
                order.setCustomerNumber(result.getInt("customerNumber"));
                order.setOrderDate(result.getDate("orderDate"));
                order.setOrderNumber(result.getInt("orderNumber"));
                order.setRequiredDate(result.getDate("requiredDate"));
                order.setShippedDate(result.getDate("shippedDate"));
                order.setStatus(result.getString("status"));

                orderList.add(order);
            }
        } catch (Exception e) {
            logger.error("Error during finding orders");
        }
        return orderList;
    }


    public List<Order> findOrdersByDate(Date from, Date to) {
        List<Order> orderList = new ArrayList<>();
        java.sql.Date dateFrom = java.sql.Date.valueOf(from.toString());
        java.sql.Date dateTo = java.sql.Date.valueOf(to.toString());

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("SELECT * from orders" +
                " WHERE orderDate BETWEEN ? AND ?;")) {
            preparedStatement.setDate(1, dateFrom);
            preparedStatement.setDate(2, dateTo);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Order order = new Order();
                order.setComments(result.getString("comments"));
                order.setCustomerNumber(result.getInt("customerNumber"));
                order.setOrderDate(result.getDate("orderDate"));
                order.setOrderNumber(result.getInt("orderNumber"));
                order.setRequiredDate(result.getDate("requiredDate"));
                order.setShippedDate(result.getDate("shippedDate"));
                order.setStatus(result.getString("status"));

                orderList.add(order);
            }
        } catch (Exception e) {
            logger.error("Error during finding orders");
        }
        return orderList;
    }

    public static void main(String[] args) throws Exception {

        ClassicModelsManager classicModelsManager = new ClassicModelsManager();

        while (true) {
            System.out.println("Select option:");
            System.out.println("    1: Show information about all offices");
            System.out.println("    2: Update product prices by 10%");
            System.out.println("    3: Show information about Customers and their Sales Representatives");
            System.out.println("    4: Show information about Products with provided name");
            System.out.println("    5: Show information about Orders with provided employee id as Sales Representative");
            System.out.println("    6: Show information about Orders between provided dates");
            System.out.println("    q: quit");
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    classicModelsManager.printAllOffices();
                    break;
                case "2":
                    classicModelsManager.updateProductPrices();
                    break;
                case "3":
                    classicModelsManager.printAllCustomersWithSalesRepName();
                    break;
                case "4":
                    classicModelsManager.findProductByName(sc.nextLine());
                    break;
                case "5":
                    classicModelsManager.findOrdersByEmloyeeId(sc.nextInt());
                    break;
                case "6":
                    classicModelsManager.findOrdersByDate(from = java.sql.Date.valueOf(sc.nextLine()),to = java.sql.Date.valueOf(sc.nextLine()));
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println("Invalid command");
            }
        }

    }
}