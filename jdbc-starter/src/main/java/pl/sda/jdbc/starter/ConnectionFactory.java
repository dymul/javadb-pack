package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionFactory{
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);


    private static final String DB_SERVER_NAME = "127.0.0.1";
    private static final String DB_NAME = "classicmodels";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sqw2230329asd";
    private static final int DB_PORT = 3306;
    MysqlDataSource dataSource;
    ConnectionFactory connectionFactory;

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);
            if (propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }
        return properties;
    }

    public ConnectionFactory() throws SQLException {

//        try {
//            dataSource = new MysqlDataSource();
//            dataSource.setServerName(DB_SERVER_NAME);
//            dataSource.setDatabaseName(DB_NAME);
//            dataSource.setUser(DB_USER);
//            dataSource.setPassword(DB_PASSWORD);
//            dataSource.setPort(DB_PORT);
//            dataSource.setServerTimezone("Europe/Warsaw");
//            dataSource.setUseSSL(false);
//            dataSource.setCharacterEncoding("UTF-8");
//        } catch (
//                SQLException e) {
//            logger.error("Error during creating MysqlDataSource", e);
//            return;
//        }
//        logger.info("Connecting to a selected database...");

        this("/database.properties");
    }

    public ConnectionFactory(String filename) throws SQLException {
        dataSource = new MysqlDataSource();
        Properties dataBaseProperties = getDataBaseProperties(filename);
        dataSource.setServerName(dataBaseProperties.getProperty("pl.sda.jdbc.db.server"));
        dataSource.setDatabaseName(dataBaseProperties.getProperty("pl.sda.jdbc.db.name"));
        dataSource.setUser(dataBaseProperties.getProperty("pl.sda.jdbc.db.user"));
        dataSource.setPassword(dataBaseProperties.getProperty("pl.sda.jdbc.db.password"));
        dataSource.setPort(Integer.parseInt(dataBaseProperties.getProperty("pl.sda.jdbc.db.port")));
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF-8");

    }

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }



    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection1 = connectionFactory.getConnection();
        System.out.println(connection1.getCatalog());


    }


}