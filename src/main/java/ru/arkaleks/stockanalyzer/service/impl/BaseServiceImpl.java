package ru.arkaleks.stockanalyzer.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.ConvertToXLSXService;
import ru.arkaleks.stockanalyzer.service.EditorService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.awt.*;
import java.io.File;
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
        EditorServiceImpl editorService = new EditorServiceImpl(connection);
        List<Stock> reports = editorService.getAllReports();

        for (Stock st:reports) {
            System.out.println("Номер отчета: " + st.getReportNumber() + ", наименование акции: " + st.getStockName() +
                    ", дата загрузки: " + st.getUploadDate());
        }
        String requestNumber = inputService.ask("Введите номер отчета: ");
        int repNumber = Integer.parseInt(requestNumber);

        List<String> repData = getReportData(repNumber,editorService,reports);
        String saveReport = inputService.ask("Выберите дальнейшее действие:\n" +
                "0. Сохранить отчет в файл\n" +
                "1. Продолжить без сохранения");
        if(saveReport.equals("0")){
            saveReportToFile(repData);
        }
    }

    private void saveReportToFile(List<String> reportDataList) {
        String path = inputService.ask("Введите путь для сохранения файла: ");
        File file = new File(path);
        if (file.isDirectory()) {
            ConvertToXLSXService converter  =  new ConvertToXLSXServiceImpl();
            boolean  convertationResult  = converter.convertToXLSXFile(reportDataList, path);
            if(convertationResult) {
                System.out.println("Файл успешно сохранен.");
            }
        } else {
            System.out.println("Указанной директории не существует.");
        }
    }

    private List<String> getReportData(int repNumber,EditorServiceImpl editorService,List<Stock> reports){

        List<String>reportData = new ArrayList<>();
        String period = editorService.getReportPeriodByReportNumber(repNumber);
        String maxPrice = editorService.findMaxPriceAndTradeDateByReportNumber(repNumber);
        String minPrice = editorService.findMinPriceAndTradeDateByReportNumber(repNumber );
        String totalVolume = editorService.getTotalVolumeByReportNumber(repNumber);
        for (Stock stock : reports) {
            if(stock.getReportNumber() == repNumber){
                System.out.println("Отчет номер: " + repNumber + ".\n"
                        + "Наименование акции: " + stock.getStockName() + ".\n"
                        + "Отчет за период: " + period + ".\n"
                        + "Максимальная стоимость акции (дата): " + maxPrice + ".\n"
                        + "Минимальная стоимость акции (дата): " + minPrice + ".\n"
                        + "Суммарный объем : " + totalVolume + ".");
                reportData.add(String.valueOf(repNumber));
                reportData.add(stock.getStockName());
                String lastPeriod = period.substring(period.lastIndexOf(" - ") + 3);
                String firstPeriod = period.substring(0,period.lastIndexOf(" - "));
                reportData.add(firstPeriod);
                reportData.add(lastPeriod);
                String maxPriceOnly = maxPrice.substring(0,maxPrice.indexOf("(") - 1);
                String maxPriceData = maxPrice.substring(maxPrice.indexOf("(") + 1,maxPrice.length() - 1);
                reportData.add(maxPriceOnly);
                reportData.add(maxPriceData);
                String minPriceOnly = minPrice.substring(0,minPrice.indexOf("(") - 1);
                String minPriceData = minPrice.substring(minPrice.indexOf("(") + 1,minPrice.length() - 1);
                reportData.add(minPriceOnly);
                reportData.add(minPriceData);
                reportData.add(totalVolume);
            }
        }
        return reportData;
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
    public void redactData(){
        EditorServiceImpl editorService = new EditorServiceImpl(connection);

        EditorMenuServiceImpl service = new EditorMenuServiceImpl(inputService,editorService);
        service.fillActions();
        service.show();
        int key = this.inputService.ask("Введите пункт меню : ", service.getActions());
        service.select(key);
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
