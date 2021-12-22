package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.EditorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditorServiceImpl implements EditorService, AutoCloseable {

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
            ps.setDouble(2, stock.getOpenPrice());
            ps.setDouble(3, stock.getHighPrice());
            ps.setDouble(4, stock.getLowPrice());
            ps.setDouble(5, stock.getClosePrice());
            ps.setDouble(6, stock.getAdjClosePrice());
            ps.setInt(7, stock.getVolume());
            ps.setString(8, stock.getStockName());
            ps.setInt(9, stock.getReportNumber());
            ps.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setReportCounter(int count) {
        String insertTableSQL = "INSERT INTO " + REPCOUNTTABLE + " (report_counter_amount) VALUES" + "(?)";
        try (PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.setInt(1, count);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateReportCounter(int count) {
        String insertTableSQL = "UPDATE " + REPCOUNTTABLE + " SET report_counter_amount = " + count
                + " WHERE report_counter_id = 1";
        try (PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getReportCounter() {
        int res = 0;
        String getTableSQL = "SELECT report_counter_amount FROM " + REPCOUNTTABLE + " WHERE report_counter_id = 1";
        try (PreparedStatement ps = connection.prepareStatement(getTableSQL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                res = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void replace(Long id, Stock stock) {

        String insertTableSQL = "UPDATE " + TABLE + " SET traidstocks_tradingdate = ?, traidstocks_openprice = ?," +
                " traidstocks_highprice = ?, traidstocks_lowprice = ?, traidstocks_closeprice = ?," +
                " traidstocks_adjcloseprice = ?, traidstocks_volume = ?, traidStocks_stockName = ?," +
                " traidStocks_reportNumber = ?, traidStocks_uploadDate = ? WHERE traidstocks_id = " + id;
        try (PreparedStatement ps = connection.prepareStatement(insertTableSQL)) {
            ps.setTimestamp(1, Timestamp.valueOf(stock.getTradingDate().atStartOfDay()));
            ps.setDouble(2, stock.getOpenPrice());
            ps.setDouble(3, stock.getHighPrice());
            ps.setDouble(4, stock.getLowPrice());
            ps.setDouble(5, stock.getClosePrice());
            ps.setDouble(6, stock.getAdjClosePrice());
            ps.setInt(7, stock.getVolume());
            ps.setString(8, stock.getStockName());
            ps.setInt(9, stock.getReportNumber());
            ps.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {

        int idNumber = Integer.parseInt(id);
        String delId = "DELETE FROM " + TABLE + " WHERE traidstocks_id = " + idNumber;

        try (PreparedStatement ps = connection.prepareStatement(delId)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Stock> findAll() {

        List<Stock> result = new ArrayList<>();
        String asd = "SELECT * FROM " + TABLE;
        try (PreparedStatement ps = connection.prepareStatement(asd)) {
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Stock stock = new Stock();
                stock.setId(res.getInt(1));
                stock.setTradingDate(res.getDate(2).toLocalDate());
                stock.setOpenPrice(res.getDouble(3));
                stock.setHighPrice(res.getDouble(4));
                stock.setLowPrice(res.getDouble(5));
                stock.setClosePrice(res.getDouble(6));
                stock.setAdjClosePrice(res.getDouble(7));
                stock.setVolume(res.getInt(8));
                stock.setStockName(res.getString(9));
                stock.setReportNumber(res.getInt(10));
                stock.setUploadDate(res.getDate(11).toLocalDate());

                result.add(stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Stock> findByName(String name) {

        List<Stock> result = new ArrayList<>();
        String tickerName = "SELECT traidstocks_id, traidstocks_tradingdate, traidstocks_openprice," +
                " traidstocks_highprice, traidstocks_lowprice, traidstocks_closeprice, traidstocks_adjcloseprice," +
                " traidstocks_volume, traidStocks_stockName, traidStocks_reportNumber, traidStocks_uploadDate FROM " +
                TABLE + " WHERE traidStocks_stockName = '" + name + "'";

        try (PreparedStatement ps = connection.prepareStatement(tickerName)) {
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Stock stock = new Stock();
                stock.setId(res.getInt(1));
                stock.setTradingDate(res.getDate(2).toLocalDate());
                stock.setOpenPrice(res.getDouble(3));
                stock.setHighPrice(res.getDouble(4));
                stock.setLowPrice(res.getDouble(5));
                stock.setClosePrice(res.getDouble(6));
                stock.setAdjClosePrice(res.getDouble(7));
                stock.setVolume(res.getInt(8));
                stock.setStockName(res.getString(9));
                stock.setReportNumber(res.getInt(10));
                stock.setUploadDate(res.getDate(11).toLocalDate());

                result.add(stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Stock findById(String idNumber) {
        int number = Integer.parseInt(idNumber);
        Stock stock = new Stock();

        String idOnTable = "SELECT traidstocks_id, traidstocks_tradingdate, traidstocks_openprice," +
                " traidstocks_highprice, traidstocks_lowprice, traidstocks_closeprice, traidstocks_adjcloseprice," +
                " traidstocks_volume, traidStocks_stockName, traidStocks_reportNumber, traidStocks_uploadDate FROM " +
                TABLE + " WHERE traidstocks_id = " + number;

        try (PreparedStatement ps = connection.prepareStatement(idOnTable)) {
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                stock.setId(res.getInt(1));
                stock.setTradingDate(res.getDate(2).toLocalDate());
                stock.setOpenPrice(res.getDouble(3));
                stock.setHighPrice(res.getDouble(4));
                stock.setLowPrice(res.getDouble(5));
                stock.setClosePrice(res.getDouble(6));
                stock.setAdjClosePrice(res.getDouble(7));
                stock.setVolume(res.getInt(8));
                stock.setStockName(res.getString(9));
                stock.setReportNumber(res.getInt(10));
                stock.setUploadDate(res.getDate(11).toLocalDate());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stock;
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
        List<Stock> result = new ArrayList<>();
        String allReports = "SELECT traidStocks_reportNumber, traidStocks_stockName, cast(traidstocks_uploaddate as date) " +
                " FROM " + TABLE + " GROUP BY traidStocks_reportNumber, traidStocks_stockName, cast(traidstocks_uploaddate as date)";

        try (PreparedStatement ps = connection.prepareStatement(allReports)) {
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Stock stock = new Stock();
                stock.setStockName(res.getString("traidstocks_stockname"));
                stock.setReportNumber(res.getInt("traidstocks_reportnumber"));
                stock.setUploadDate(res.getDate("traidstocks_uploaddate").toLocalDate());

                result.add(stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public String findMaxPriceAndTradeDateByReportNumber(int reportNumber) {
        String maxPrice = "SELECT traidstocks_highprice, traidstocks_tradingdate FROM " + TABLE +
                " WHERE traidstocks_highprice = (SELECT max(traidstocks_highprice) FROM " + TABLE +
                " WHERE traidstocks_reportnumber = " + reportNumber +
                ") GROUP BY traidstocks_highprice, traidstocks_tradingdate";
        String priceValue = "";
        String dateValue = "";

        try (PreparedStatement ps = connection.prepareStatement(maxPrice)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                priceValue = res.getString("traidstocks_highprice");
                dateValue = String.valueOf(res.getDate("traidstocks_tradingdate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return priceValue + " (" + dateValue + ")";
    }

    @Override
    public String findMinPriceAndTradeDateByReportNumber(int reportNumber) {
        String minPrice = "SELECT traidstocks_lowprice, traidstocks_tradingdate FROM " + TABLE +
                " WHERE traidstocks_lowprice = (SELECT min (traidstocks_lowprice) FROM " + TABLE +
                " WHERE traidstocks_reportnumber = " + reportNumber +
                ") GROUP BY traidstocks_lowprice, traidstocks_tradingdate";
        String priceValue = "";
        String dateValue = "";

        try (PreparedStatement ps = connection.prepareStatement(minPrice)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                priceValue = res.getString("traidstocks_lowprice");
                dateValue = String.valueOf(res.getDate("traidstocks_tradingdate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return priceValue + " (" + dateValue + ")";
    }

    @Override
    public String getReportPeriodByReportNumber(int reportNumber) {
        String minPeriod = "SELECT traidstocks_tradingdate FROM " + TABLE +
                " WHERE traidstocks_tradingdate = (SELECT min(cast(traidstocks_tradingdate AS date)) FROM traid_stocks"
                + " WHERE traidstocks_reportnumber = " + reportNumber + ") GROUP BY traidstocks_tradingdate";
        String maxPeriod = "SELECT traidstocks_tradingdate FROM " + TABLE +
                " WHERE traidstocks_tradingdate = (SELECT max(cast(traidstocks_tradingdate AS date)) FROM traid_stocks"
                + " WHERE traidstocks_reportnumber = " + reportNumber + ") GROUP BY traidstocks_tradingdate";
        String minData = "";
        String maxData = "";

        try (PreparedStatement ps = connection.prepareStatement(minPeriod)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                minData = String.valueOf(res.getDate("traidstocks_tradingdate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement ps = connection.prepareStatement(maxPeriod)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                maxData = String.valueOf(res.getDate("traidstocks_tradingdate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return minData + " - " + maxData;
    }

    @Override
    public String getTotalVolumeByReportNumber(int reportNumber) {
        String totalVolume = "SELECT sum(traidstocks_volume) FROM " + TABLE + " WHERE traidstocks_reportnumber = " +
                reportNumber;
        String amount = "";
        try (PreparedStatement ps = connection.prepareStatement(totalVolume)) {
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                amount = String.valueOf(res.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return amount;
    }
}
