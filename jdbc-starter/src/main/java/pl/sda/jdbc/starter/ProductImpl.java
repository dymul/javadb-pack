package pl.sda.jdbc.starter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProductImpl implements ProductDao {

    ConnectionFactory myDB;

    {
        try {
            myDB = new ConnectionFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product p) {

        String query = "INSERT INTO products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES(?,?,?,?,?,?, ?, ?,?)";

        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.setString(1, p.getProductCode());
            statement.setString(2, p.getProductName());
            statement.setString(3, p.getProductLine());
            statement.setString(4, p.getProductScale());
            statement.setString(5, p.getProductVendor());
            statement.setString(6, p.getProductDescription());
            statement.setInt(7, p.getQuantityInStock());
            statement.setBigDecimal(8, p.getBuyPrice());
            statement.setDouble(9, p.getMSRP());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Product p) {
        String query = "UPDATE products SET buyPrice = buyPrice*1.1 WHERE productCode like '" + p.getProductCode() + "'";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String query2 = "delete from orderdetails where productCode like '" + id + "'";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query2)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "DELETE from products where productCode like '" + id + "'";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(String id) {
        Product product = null;
        String query = "SELECT * from products where productCode like '" + id + "'";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String productCode = result.getString("productCode");
                String productName = result.getString("productName");
                String productLine = result.getString("productLine");
                String productScale = result.getString("productScale");
                String productVendor = result.getString("productVendor");
                String productDescription = result.getString("productDescription");
                int quantityInStock = result.getInt("quantityInStock");
                BigDecimal buyPrice = result.getBigDecimal("buyPrice");
                double MSRP = result.getDouble("MSRP");
                product = new Product(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * from products";
        try (PreparedStatement statement = myDB.getConnection().prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String productCode = result.getString("productCode");
                String productName = result.getString("productName");
                String productLine = result.getString("productLine");
                String productScale = result.getString("productScale");
                String productVendor = result.getString("productVendor");
                String productDescription = result.getString("productDescription");
                int quantityInStock = result.getInt("quantityInStock");
                BigDecimal buyPrice = result.getBigDecimal("buyPrice");
                double MSRP = result.getDouble("MSRP");
                productList.add(new Product(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Product a: productList) {
            System.out.println(a.toString());
        }

        return productList;
    }
}
