package ru.arkaleks.stockanalyzer.service;

import ru.arkaleks.stockanalyzer.entity.Stock;

import java.util.List;
import java.util.function.Predicate;

public interface EditorService {

    void add(Stock stock);

    void setReportCounter(int count);

    void updateReportCounter(int count);

    int getReportCounter();

    void replace(Long id, Stock stock);

    void delete(String id);

    List<Stock> findAll();

    List<Stock> findByName(String name);

    Stock findById(String idNumber);

    Long getReportNumberFromDataBase();

    void updateReportNumberToDataBase(Long number);

    List<Stock> getAllReports();

    List<String> findMaxPriceAndTradeDateByReportNumber(Long reportNumber);

    List<String> findMinPriceAndTradeDateByReportNumber(Long reportNumber);

    List<String> getReportPeriodByReportNumber(Long reportNumber);

    String getTotalVolumeByReportNumber(Long reportNumber);
}
