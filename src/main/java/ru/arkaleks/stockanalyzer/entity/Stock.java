package ru.arkaleks.stockanalyzer.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    public Stock(LocalDate tradingDate, double openPrice, double highPrice, double lowPrice, double closePrice,
                 double adjClosePrice, int volume) {
        this.tradingDate = tradingDate;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.adjClosePrice = adjClosePrice;
        this.volume = volume;
    }

    public Stock(int id, LocalDate tradingDate, double openPrice, double highPrice, double lowPrice, double closePrice,
                 double adjClosePrice, int volume, LocalDate uploadDate) {
        this.id = id;
        this.tradingDate = tradingDate;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.adjClosePrice = adjClosePrice;
        this.volume = volume;
        this.uploadDate = uploadDate;
    }

    public Stock(String stockName, int reportNumber, LocalDate uploadDate) {
        this.stockName = stockName;
        this.reportNumber = reportNumber;
        this.uploadDate = uploadDate;
    }

    private int id;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 0)
    private LocalDate tradingDate;

    @CsvBindByPosition(position = 1)
    private double openPrice;

    @CsvBindByPosition(position = 2)
    private double highPrice;

    @CsvBindByPosition(position = 3)
    private double lowPrice;

    @CsvBindByPosition(position = 4)
    private double closePrice;

    @CsvBindByPosition(position = 5)
    private double adjClosePrice;

    @CsvBindByPosition(position = 6)
    private int volume;

    private String stockName;

    private int reportNumber;

    private LocalDate uploadDate;
}
