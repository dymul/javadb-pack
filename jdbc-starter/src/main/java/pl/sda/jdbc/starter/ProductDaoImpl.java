package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    private final ConnectionFactory connectionFactory;

    public ProductDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void save(Product p) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
           String query = "INSERT INTO products (productCode, productName, productLine, productScale, " +
                   "productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, p.getProductCode());
            statement.setString(2, p.getProductName());
            statement.setString(3, p.getProductLine());
            statement.setString(4, p.getProductScale());
            statement.setString(5, p.getProductVendor());
            statement.setString(6, p.getProductDescription());
            statement.setInt(7, p.getQuantityInStock());
            statement.setBigDecimal(8, p.getBuyPrice());
            statement.setBigDecimal(9, p.getMSRP());

            statement.executeUpdate();
        }
    }

    @Override
    public void update(Product p) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "UPDATE products SET productName=?, productLine=?, productScale=?, productVendor=?, " +
                    "productDescription=?, quantityInStock=?, buyPrice=?, MSRP=? WHERE productCode=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, p.getProductName());
            statement.setString(2, p.getProductLine());
            statement.setString(3, p.getProductScale());
            statement.setString(4, p.getProductVendor());
            statement.setString(5, p.getProductDescription());
            statement.setInt(6, p.getQuantityInStock());
            statement.setBigDecimal(7, p.getBuyPrice());
            statement.setBigDecimal(8, p.getMSRP());
            statement.setString(9, p.getProductCode());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(String productCode) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "DELETE FROM products WHERE productCode=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, productCode);

            int count = statement.executeUpdate(); // handle
        }
    }

    @Override
    public Product find(String productCode) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "SELECT * FROM products WHERE productCode=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, productCode);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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
                return p;
            }
            return null;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "SELECT * FROM products";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
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
}
