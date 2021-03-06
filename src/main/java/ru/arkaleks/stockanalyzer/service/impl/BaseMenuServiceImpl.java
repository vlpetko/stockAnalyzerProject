package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.BaseActionService;
import ru.arkaleks.stockanalyzer.service.BaseMenuService;
import ru.arkaleks.stockanalyzer.service.BaseService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BaseMenuServiceImpl implements BaseMenuService {

    private InputService inputService;

    private BaseService baseService;

    private List<BaseActionService> actions = new ArrayList<>();


    public BaseMenuServiceImpl(InputService inputService, BaseService baseService) {
        this.inputService = inputService;
        this.baseService = baseService;
    }


    /**
     * Метод формирует массив пунктов меню.
     */
    @Override
    public List<Integer> getActions() {
        List<Integer> range = new ArrayList<>();
        for (BaseActionService action : actions
        ) {
            range.add(action.key());
        }
        return range;
    }


    /**
     * Метод инициализирует события.
     */
    @Override
    public void fillActions() {
        System.out.println("   Основное меню.");
        this.actions.add(new AddData(0, "Добавить новые данные."));
        this.actions.add(new EditData(1, "Редактировать данные."));
        this.actions.add(new GenerateReport(2, "Сформировать отчёт."));
        this.actions.add(new ExitAnalyzer(3, "Выйти из приложения."));
    }


    /**
     * Метод реализует выбранное событие.
     */
    @Override
    public void select(int key) {
        this.actions.get(key).execute(this.inputService, this.baseService);
    }

    /**
     * Метод выводит на печать меню.
     */
    @Override
    public void show() {
        Consumer<BaseActionService> consumer = action -> System.out.println((action.info()));
        actions.forEach(consumer);
    }

    /**
     * Класс реализует добавление новой записи в хранилище.
     */
    private class AddData extends BaseActionServiceImpl {

        public AddData(int key, String name) {
            super(key, name);
        }

        public void execute(InputService inputService, BaseService baseService) {

            String position = inputService.ask("0. Загрузка данных из файла\n" +
                    "1. Ручное добавление записей\n" +
                    "Введите пункт меню : ");

            try {
                baseService.addData(position);
            } catch (IOException e) {
                System.out.println("Не выбран пункт");
                e.printStackTrace();
            }

        }

    }

    /**
     * Класс реализует изменение записи в хранилище.
     */
    private class EditData extends BaseActionServiceImpl {

        public EditData(int key, String name) {
            super(key, name);
        }

        public void execute(InputService inputService, BaseService baseService) {
            try {
                baseService.redactData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Класс реализует добавление новой записи в хранилище.
     */
    private class GenerateReport extends BaseActionServiceImpl {

        public GenerateReport(int key, String name) {
            super(key, name);
        }

        public void execute(InputService inputService, BaseService baseService) {
            try {
                baseService.generateReport();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Класс реализует выход из программы
     */
    private static class ExitAnalyzer extends BaseActionServiceImpl {

        public ExitAnalyzer(int key, String name) {
            super(key, name);
        }

        public void execute(InputService inputService, BaseService baseService) {

        }
    }
}
