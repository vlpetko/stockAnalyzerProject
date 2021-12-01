package ru.arkaleks.stockanalyzer.config;

import java.awt.*;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DataBaseConnector {
    private static final String TABLE = "traid_stocks";

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
            List<String> tables = new ArrayList<>(Arrays.asList(TABLE,REPCOUNT));
            List<String> tablesFromShema = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet res = metaData.getTables(null,"public","%",null);
            while (res.next()) {
                tablesFromShema.add(res.getString("TABLE_NAME"));
            }
            res.close();
            for (String st:tables
                 ) {
                if (!tablesFromShema.contains(st)){
                    createTable(connection,st);
                }
            }
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
        String createStockTable = "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                + "traidStocks_id SERIAL, "
                + "traidStocks_tradingDate TIMESTAMP NOT NULL, "
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

        String createReportTable = "CREATE TABLE IF NOT EXISTS " + REPCOUNT + " ("
                + "report_counter_id SERIAL, "
                + "report_counter_amount INTEGER "
                + ")";

        if(tableName.equals(TABLE)){
            createTable = createStockTable;
        }
        if(tableName.equals(REPCOUNT)){
            createTable = createReportTable;
        }

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

}


