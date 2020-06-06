package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClassicModelsManager {

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    private final ConnectionFactory connectionFactory;

    public ClassicModelsManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void printAllEmployees() {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                String employeeNumber = resultSet.getString("employeeNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                logger.info("Employee: number: {}, name: {} {}", employeeNumber, firstName, lastName);
            }

        } catch (SQLException e) {
            logger.error("Problem with connection", e);
        }
    }

    public void insertProductLine() {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            String newProductLine = "INSERT INTO productlines (productLine) VALUES ('NewLine')";
            int numberOfRows = statement.executeUpdate(newProductLine);
            logger.info("{} Rows affected", numberOfRows);

        } catch (SQLException e) {
            logger.error("Problem with connection", e);
        }
    }

    public void printAllOffices() {

    }

    public void updateProductPrices() {

    }

    public void printAllCustomersWithSalesRepName() {

    }

    public List<Product> findProductByName(String nameMatcher) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "SELECT * FROM products WHERE productName LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + nameMatcher + "%");
            ResultSet resultSet = statement.executeQuery();
            List<Product> result = new LinkedList<>();
            while (resultSet.next()) {
                Product p = new Product();
                p.setMSRP(resultSet.getBigDecimal("MSRP"));
                p.setBuyPrice(resultSet.getBigDecimal("buyPrice"));
                p.setProductDescription(resultSet.getString("productDescription"));
                p.setProductVendor(resultSet.getString("productVendor"));
                p.setProductScale(resultSet.getString("productScale"));
                p.setProductLine(resultSet.getString("productLine"));
                p.setProductName(resultSet.getString("productName"));
                p.setProductCode(resultSet.getString("productCode"));
                p.setQuantityInStock(resultSet.getInt("quantityInStock"));
                result.add(p);
            }
            return result;

        }
    }


    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        ClassicModelsManager manager = new ClassicModelsManager(connectionFactory);
        manager.findProductByName("har").forEach(System.out::println);
    }


}
