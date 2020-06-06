package pl.sda.jdbc.starter;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void save(Product p) throws SQLException;

    void update (Product p)throws SQLException;

    void delete (String id)throws SQLException;

    Product find (String id)throws SQLException;

    List<Product> findAll()throws SQLException;

}
