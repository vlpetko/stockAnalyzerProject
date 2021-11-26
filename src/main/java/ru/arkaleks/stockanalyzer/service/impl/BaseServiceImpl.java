package ru.arkaleks.stockanalyzer.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class BaseServiceImpl implements BaseService {

    private final Connection connection;


    /**
     * Получение данных от пользователя.
     */
    private InputService inputService;

    public BaseServiceImpl(Connection connection, InputService inputService) {
        this.connection = connection;
        this.inputService = inputService;
    }

    /**
     * Метод реализует добавление новых записей
     */
    @Override
    public void addData(String position) {

        ArrayList<Stock> list = new ArrayList<Stock>();

        int key = Integer.parseInt(position);
        if(key == 0){
            String choice = inputService.ask("Введите путь к файлу");
            try {
                CSVReader csvReader = new CSVReader(new FileReader(choice));

                String[] record = null;

                while ((record = csvReader.readNext()) != null ){
                    Stock stock = new Stock();
                    stock.setTradingDate(LocalDate.parse(record[0]));
                    stock.setOpenPrice(Double.parseDouble(record[1]));
                    stock.setHighPrice(Double.parseDouble(record[2]));
                    stock.setLowPrice(Double.parseDouble(record[3]));
                    stock.setClosePrice(Double.parseDouble(record[4]));
                    stock.setAdjClosePrice(Double.parseDouble(record[5]));
                    stock.setVolume(Integer.parseInt(record[6]));
                    System.out.println(record);
                    list.add(stock);
                }

                csvReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    /**
     * Метод реализует генерацию отчета
     */
    @Override
    public void generateReport() {

    }

}
