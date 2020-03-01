package pl.sda.jdbc.starter;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    private MysqlDataSource dataSource;

    public ConnectionFactory(String filename) throws SQLException {
        dataSource = new MysqlDataSource();
        Properties file = getDataBaseProperties(filename);
        dataSource.setServerName(file.getProperty("pl.sda.jdbc.db.server"));
        dataSource.setDatabaseName( file.getProperty("pl.sda.jdbc.db.name"));
        dataSource.setUser(file.getProperty("pl.sda.jdbc.db.user"));
        dataSource.setPassword( file.getProperty("pl.sda.jdbc.db.password"));
        dataSource.setPort(Integer.valueOf(file.getProperty("pl.sda.jdbc.db.port")));
        dataSource.setServerTimezone("Europe/Warsaw");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF-8");
    }

    public ConnectionFactory() throws SQLException {
        this("/database.properties");
    }

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

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.info("Connected database successfully...");
        logger.info("Connection = " + connection);
        logger.info("Database name = " + connection.getCatalog());
        return connection;
    }

    public static void main(String[] args) throws SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.getConnection();

    }
}