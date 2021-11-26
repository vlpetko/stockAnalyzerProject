package ru.arkaleks.stockanalyzer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    public Stock(LocalDate tradingDate, double openPrice, double highPrice, double lowPrice, double closePrice, double adjClosePrice, int volume) {
        this.tradingDate = tradingDate;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.adjClosePrice = adjClosePrice;
        this.volume = volume;
    }

    private int id;

    private LocalDate tradingDate;

    private double openPrice;

    private double highPrice;

    private double lowPrice;

    private double closePrice;

    private double adjClosePrice;

    private int volume;
}
