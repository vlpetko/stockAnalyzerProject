package ru.arkaleks.stockanalyzer.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.EditorService;
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
        EditorServiceImpl editorService = new EditorServiceImpl(connection);

        ArrayList<Stock> list = new ArrayList<Stock>();

        int key = Integer.parseInt(position);
        if(key == 0){
          String pathToFile = inputService.ask("Введите путь к файлу");
            uploadFile(pathToFile);
        }
        if(key == 1){
            AddStock addStock = new AddStock(0,"Выберите раздел");
            addStock.execute(inputService,editorService);
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

                int repCount = editorService.getReportCounter() + 1;

                for (Stock stock : stocksFromFile){
                    stock.setStockName(findFullStockNameByTicker(tickerName));
                    stock.setReportNumber(repCount);
                    editorService.add(stock);
                }
                if(repCount == 1){
                    editorService.setReportCounter(repCount);
                }else{
                    editorService.updateReportCounter(repCount);
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
        tickers.put("PBF","PBF Energy");

        return tickers.get(keyName);
    }
    @Override
    public void redactor(String pos){
        int key = Integer.parseInt(pos);

        if (key == 0){

        }else if (key == 1){
            replaceData();
        }
    }

    public void replaceData(){

        EditorServiceImpl editorService = new EditorServiceImpl(connection);

        Stock stock = new Stock();

        long idNumber = Integer.parseInt(inputService.ask("Введите id редактируемой записи"));

        LocalDate data = LocalDate.parse((inputService.ask("Введите дату в формате гггг-мм-дд")));
        stock.setTradingDate(data);
        double openPrice = Integer.parseInt(inputService.ask("Введите цену открытия"));
        stock.setOpenPrice(openPrice);
        double highPrice = Integer.parseInt(inputService.ask("Введите максимальную цену"));
        stock.setHighPrice(highPrice);
        double lowPrice = Integer.parseInt(inputService.ask("Введите минимальную цену"));
        stock.setLowPrice(lowPrice);
        double closePrice = Integer.parseInt(inputService.ask("Введите цену закрытия"));
        stock.setClosePrice(closePrice);
        double adjClosePrice = Integer.parseInt(inputService.ask("Введите уточненную цену закрытия"));
        stock.setAdjClosePrice(adjClosePrice);
        int volume = Integer.parseInt(inputService.ask("Введите объем торгов"));
        stock.setVolume(volume);
        String stockName = inputService.ask("Введите название акции");
        stock.setStockName(stockName);
        int reportNumber = Integer.parseInt(inputService.ask("Введите номер записи"));
        stock.setReportNumber(reportNumber);

        editorService.replace(idNumber,stock);
    }

    private class AddStock extends UserActionServiceImpl {

        public AddStock(int key, String name) {
            super(key, name);
        }

        public void execute(InputService inputService, EditorService editorService) {

            int repCount = editorService.getReportCounter() + 1;

            Stock stock = new Stock();

            String inputData = inputService.ask("Введите дату торгов в формате гггг-мм-дд:");
            stock.setTradingDate(LocalDate.parse(inputData));
            String openPrice = inputService.ask("Введите цену открытия:");
            stock.setOpenPrice(Double.parseDouble(openPrice));
            String highPrice = inputService.ask("Введите максимальную цену:");
            stock.setHighPrice(Double.parseDouble(highPrice));
            String lowPrice = inputService.ask("Введите минимальную цену:");
            stock.setLowPrice(Double.parseDouble(lowPrice));
            String closePrice = inputService.ask("Введите цену закрытия:");
            stock.setClosePrice(Double.parseDouble(closePrice));
            String adjClosePrice = inputService.ask("Введите уточненую цену закрытия:");
            stock.setAdjClosePrice(Double.parseDouble(adjClosePrice));
            String volume = inputService.ask("Введите объем торгов:");
            stock.setVolume(Integer.parseInt((volume)));
            String stockName = inputService.ask("Введите наименование акции:");
            stock.setStockName(stockName);
            stock.setReportNumber(repCount);
            editorService.add(stock);

            if(repCount == 1){
                editorService.setReportCounter(repCount);
            }else{
                editorService.updateReportCounter(repCount);
            }
        }
    }
}
