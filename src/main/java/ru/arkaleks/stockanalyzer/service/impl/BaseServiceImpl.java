package ru.arkaleks.stockanalyzer.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl implements BaseService {

    private final Connection connection;
    private final Map<String, String> tickers = new HashMap<>();

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
          String pathToFile = inputService.ask("Введите путь к файлу");
            uploadFile(pathToFile);

        }
    }

    /**
     * Метод реализует генерацию отчета
     */
    @Override
    public void generateReport() {

    }

    private void uploadFile(String path){
        if(path.toLowerCase().endsWith(".csv")){
            EditorServiceImpl editorService = new EditorServiceImpl(connection);
            try {
                List<Stock> stocksFromFile = new CsvToBeanBuilder(new FileReader(path))
                        .withSkipLines(1)
                        .withType(Stock.class)
                        .build()
                        .parse();

                String tickerName = path.substring(path.lastIndexOf("\\") + 1,path.lastIndexOf(".csv"));

                for (Stock stock : stocksFromFile){
                    stock.setStockName(findFullStockNameByTicker(tickerName));
                    stock.setReportNumber(777);
                    editorService.add(stock);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
                e.printStackTrace();
            }
        }else{
            System.out.println("Выберите csv-файл");
        }
    }

    private String findFullStockNameByTicker(String keyName){

        tickers.put("TSLA","Tesla");

        return tickers.get(keyName);
    }

}
