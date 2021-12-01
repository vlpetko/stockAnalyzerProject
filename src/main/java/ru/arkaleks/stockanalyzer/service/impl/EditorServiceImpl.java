package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.EditorService;

import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class EditorServiceImpl implements EditorService,AutoCloseable {

    private final Connection connection;
    private static final String TABLE = "traid_stocks";
    private static final String REPCOUNTTABLE = "report_counter";

    public EditorServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void add(Stock stock) {
        String insertTableSQL = "INSERT INTO " + TABLE + " (traidstocks_tradingdate, traidstocks_openprice," +
                " traidstocks_highprice,traidstocks_lowprice,traidstocks_closeprice,traidstocks_adjcloseprice," +
                "traidstocks_volume,traidStocks_stockName,traidStocks_reportNumber,traidStocks_uploadDate) VALUES" +
                "(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.setTimestamp(1, Timestamp.valueOf(stock.getTradingDate().atStartOfDay()));
            ps.setDouble(2,stock.getOpenPrice());
            ps.setDouble(3,stock.getHighPrice());
            ps.setDouble(4,stock.getLowPrice());
            ps.setDouble(5,stock.getClosePrice());
            ps.setDouble(6,stock.getAdjClosePrice());
            ps.setInt(7,stock.getVolume());
            ps.setString(8,stock.getStockName());
            ps.setInt(9,stock.getReportNumber());
            ps.setTimestamp(10,new java.sql.Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setReportCounter(int count){
        String insertTableSQL = "INSERT INTO " + REPCOUNTTABLE + " (report_counter_amount) VALUES" + "(?)";
        try(PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.setInt(1,count);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateReportCounter(int count){
        String insertTableSQL = "UPDATE " + REPCOUNTTABLE + " SET report_counter_amount = " + count
                + " WHERE report_counter_id = 1";
        try(PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getReportCounter(){
        int res = 0;
        String getTableSQL = "SELECT report_counter_amount FROM " + REPCOUNTTABLE + " WHERE report_counter_id = 1";
        try(PreparedStatement ps = connection.prepareStatement(getTableSQL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                res = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void replace(Long id, Stock stock) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Stock> findAll() {
        return null;
    }

    @Override
    public List<Stock> findByName(String name) {
        return null;
    }

    @Override
    public Stock findById(Predicate<String> predicate) {
        return null;
    }

    @Override
    public Long getReportNumberFromDataBase() {
        return null;
    }

    @Override
    public void updateReportNumberToDataBase(Long number) {

    }

    @Override
    public List<Stock> getAllReports() {
        return null;
    }

    @Override
    public List<String> findMaxPriceAndTradeDateByReportNumber(Long reportNumber) {
        return null;
    }

    @Override
    public List<String> findMinPriceAndTradeDateByReportNumber(Long reportNumber) {
        return null;
    }

    @Override
    public List<String> getReportPeriodByReportNumber(Long reportNumber) {
        return null;
    }

    @Override
    public String getTotalVolumeByReportNumber(Long reportNumber) {
        return null;
    }
}
