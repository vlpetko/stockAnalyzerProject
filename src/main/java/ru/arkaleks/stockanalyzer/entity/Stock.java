package ru.arkaleks.stockanalyzer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    private String id;

    private LocalDate tradingDate;

    private double openPrice;

    private double highPrice;

    private double lowPrice;

    private double closePrice;

    private double adjClosePrice;

    private int volume;
}
