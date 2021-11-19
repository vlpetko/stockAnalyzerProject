package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.sql.Connection;

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

    }

    /**
     * Метод реализует генерацию отчета
     */
    @Override
    public void generateReport() {

    }

}
