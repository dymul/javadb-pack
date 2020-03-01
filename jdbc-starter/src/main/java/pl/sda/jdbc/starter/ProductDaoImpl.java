package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao{

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    ConnectionFactory connectionFactory;

    public ProductDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void save(Product p) {

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("INSERT INTO products" +
                " (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock," +
                " buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, p.getProductCode());
            preparedStatement.setString(2, p.getProductName());
            preparedStatement.setString(3, p.getProductLine());
            preparedStatement.setString(4, p.getProductScale());
            preparedStatement.setString(5, p.getProductVendor());
            preparedStatement.setString(6, p.getProductDescription());
            preparedStatement.setString(7, p.getQuantityInStock());
            preparedStatement.setBigDecimal(8, p.getBuyPrice());
            preparedStatement.setBigDecimal(9, p.getMSRP());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            logger.error("Error during saving in database", e);
        }
    }

    @Override
    public void update(Product p) {

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("UPDATE products SET" +
                " productName = ?, productLine = ?, productScale = ?, productVendor = ?, productDescription = ?, quantityInStock = ?," +
                " buyPrice = ?, MSRP = ? WHERE productCode = ?")) {
            preparedStatement.setString(1, p.getProductName());
            preparedStatement.setString(2, p.getProductLine());
            preparedStatement.setString(3, p.getProductScale());
            preparedStatement.setString(4, p.getProductVendor());
            preparedStatement.setString(5, p.getProductDescription());
            preparedStatement.setString(6, p.getQuantityInStock());
            preparedStatement.setBigDecimal(7, p.getBuyPrice());
            preparedStatement.setBigDecimal(8, p.getMSRP());
            preparedStatement.setString(9, p.getProductCode());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            logger.error("Error during updating in database", e);
        }
    }

    @Override
    public void delete(String id) {

        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("DELETE FROM products" +
                " WHERE productCode = ?")) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            logger.error("Error during deletion from database", e);
        }
    }

    @Override
    public Product find(String id) {
        Product product = new Product();
        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("SELECT * FROM products" +
                " WHERE productCode = ?")) {

            preparedStatement.setString(1, id);

            ResultSet result = preparedStatement.executeQuery();

            product.setQuantityInStock(result.getString("quantityInStock"));
            product.setProductVendor(result.getString("productVendor"));
            product.setProductScale(result.getString("productScale"));
            product.setProductLine(result.getString("productLine"));
            product.setProductDescription(result.getString("productDescription"));
            product.setProductName(result.getString("productName"));
            product.setProductCode(result.getString("productCode"));
            product.setMSRP(result.getBigDecimal("MSRP"));
            product.setBuyPrice(result.getBigDecimal("buyPrice"));


        } catch (Exception e) {
            logger.error("Error during finding products in database", e);
        }
        return product;
    }

    @Override
    public List<Product> findAll() {

        List<Product> productList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connectionFactory.getConnection().prepareStatement("SELECT * FROM products")) {
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
            logger.error("Error during finding all products in database", e);
        }
        return productList;
    }
}
