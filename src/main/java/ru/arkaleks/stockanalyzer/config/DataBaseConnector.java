package ru.arkaleks.stockanalyzer.config;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseConnector {

    public DataBaseConnector() {
    }

    public Connection init() {
        Connection connection;
        try (InputStream in = DataBaseConnector.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties config = new Properties();
            config.load(in);
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            checkSchema(connection);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }


    /**
     * Метод определяет, создана ли необходимая схема в базе данных
     *
     * @param
     * @return true
     * @throws SQLException
     */
    private void checkSchema(Connection connection) {
        try {
          //TODO:реализовать метод
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает необходимую схему в базе данных
     *
     * @param
     * @return true
     * @throws SQLException
     */
    private boolean createTable(Connection connection, String tableName) {
        boolean result = false;
        String createTable = null;

        //TODO:реализовать метод

        if (createTable != null) {
            try (
                    PreparedStatement ps = connection.prepareStatement(createTable)) {
                ps.executeUpdate();
                result = true;
                System.out.println("Table \"tableName\" was created successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}


