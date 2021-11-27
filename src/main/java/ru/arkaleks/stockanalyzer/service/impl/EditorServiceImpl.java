package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.EditorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Predicate;

public class EditorServiceImpl implements EditorService,AutoCloseable {

    private final Connection connection;
    private static final String TABLE = "traid_stoks";

    public EditorServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void add(Stock stock) {
        String insertTableSQL = "INSERT INTO " + TABLE + " (traidstoks_tradingdate, traidstocks_openprice," +
                " traidstocks_highprice,traidstocks_lowprice,traidstocks_closeprice,traidstocks_adjcloseprice," +
                "traidstocks_volume) VALUES" + "(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.setTimestamp(1, Timestamp.valueOf(stock.getTradingDate().atStartOfDay()));
            ps.setDouble(2,stock.getOpenPrice());
            ps.setDouble(3,stock.getHighPrice());
            ps.setDouble(4,stock.getLowPrice());
            ps.setDouble(5,stock.getClosePrice());
            ps.setDouble(6,stock.getAdjClosePrice());
            ps.setInt(7,stock.getVolume());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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