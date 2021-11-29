package ru.arkaleks.stockanalyzer.config;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseConnector {
    private static final String TABLE = "traid_stoks";

    private static final String REPCOUNT = "report_counter";

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
            createTable(connection, TABLE);
            createReportCounter(connection,REPCOUNT);
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
    private boolean checkSchema(Connection connection) {
        boolean result = false;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet res = metaData.getTables(null,null,"%",null);
            while (res.next()) {
                if (res.getString("TABLE_NAME").equals(TABLE)) {
                    result = true;
                }
            }
            res.close();
          //TODO:реализовать метод
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
        String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "traidStoks_id SERIAL, "
                + "traidStoks_tradingDate TIMESTAMP NOT NULL, "
                + "traidStocks_openPrice NUMERIC(20,14) NOT NULL, "
                + "traidStocks_highPrice NUMERIC(20,14) NOT NULL, "
                + "traidStocks_lowPrice NUMERIC(20,14) NOT NULL, "
                + "traidStocks_closePrice NUMERIC(20,14) NOT NULL, "
                + "traidStocks_adjClosePrice NUMERIC(20,14) NOT NULL, "
                + "traidStocks_volume INTEGER NOT NULL, "
                + "traidStocks_stockName VARCHAR (30), "
                + "traidStocks_reportNumber INTEGER, "
                + "traidStocks_uploaddate TIMESTAMP "
                + ")";


        if (createTable != null) {
            try (
                    PreparedStatement ps = connection.prepareStatement(createTable)) {
                ps.executeUpdate();
                result = true;
                System.out.println("Table " + tableName + " was created successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean createReportCounter(Connection connection, String tableName){
        boolean result = false;
        String createtable = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "report_counter_id SERIAL, "
                + "report_counter_amount INTEGER "
                + ")";

        if(createtable != null){
            try (PreparedStatement ps = connection.prepareStatement(createtable)){
                ps.executeUpdate();
                result = true;
                System.out.println("Table " + tableName + " was created successfully!");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }
}


