package ru.arkaleks.stockanalyzer;

import ru.arkaleks.stockanalyzer.config.DataBaseConnector;
import ru.arkaleks.stockanalyzer.service.BaseMenuService;
import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.InputService;
import ru.arkaleks.stockanalyzer.service.impl.BaseMenuServiceImpl;
import ru.arkaleks.stockanalyzer.service.impl.BaseServiceImpl;
import ru.arkaleks.stockanalyzer.service.impl.InputServiceServiceImpl;
import ru.arkaleks.stockanalyzer.service.impl.ValidateInputService;

import java.sql.Connection;

public class Application {

    /**
     * Получение данных от пользователя.
     */
    private final InputService inputService;

    /**
     * Хранилище заявок.
     */
    private final BaseService baseService;

    /**
     * Конструтор инициализирующий поля.
     *
     * @param inputService ввод данных.
     * @param baseService  хранилище заявок.
     */
    public Application(InputService inputService, BaseService baseService) {
        this.inputService = inputService;
        this.baseService = baseService;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        boolean exit = false;
        while (!exit) {
            BaseMenuService menu = new BaseMenuServiceImpl(this.inputService, this.baseService);
            menu.fillActions();
            menu.show();
            int key = this.inputService.ask("Введите пункт меню : ", menu.getActions());
            menu.select(key);
        }
    }





    public static void main(String[] args) {
        DataBaseConnector connector = new DataBaseConnector();
        Connection conn = connector.init();
        new Application(
                new ValidateInputService(new InputServiceServiceImpl()),
                new BaseServiceImpl(conn, new InputServiceServiceImpl())
        ).init();
    }
}
