package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.entity.Stock;
import ru.arkaleks.stockanalyzer.service.BaseActionService;
import ru.arkaleks.stockanalyzer.service.EditorMenuService;
import ru.arkaleks.stockanalyzer.service.EditorService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EditorMenuServiceImpl implements EditorMenuService {

    private InputService inputService;

    private EditorServiceImpl editorService;

    public EditorMenuServiceImpl(InputService inputService, EditorServiceImpl editorService) {
        this.inputService = inputService;
        this.editorService = editorService;
    }

    private List<UserActionServiceImpl> actions = new ArrayList<>();

    @Override
    public List<Integer> getActions() {
        List<Integer> range = new ArrayList<>();
        for (UserActionServiceImpl action : actions
        ) {
            range.add(action.key());
        }
        return range;
    }

    @Override
    public void fillActions() {
        System.out.println("Меню редактора");
        this.actions.add(new EditStockById(0,"Редактировать запись"));
        this.actions.add(new ShowAllStocks(1,"Показать все записи"));
        this.actions.add(new ShowStockById(2,"Найти запись по идентификатору"));
        this.actions.add(new DeleteStockById(3,"Удалить запись"));
        this.actions.add(new ShowStocksByName(4,"Найти запись по наименованию акции"));
    }

    @Override
    public void select(int key) {
        this.actions.get(key).execute(this.inputService,this.editorService);
    }

    @Override
    public void show() {
        Consumer<UserActionServiceImpl> consumer = action -> System.out.println((action.info()));
        actions.forEach(consumer);
    }

    class EditStockById extends UserActionServiceImpl{

        protected EditStockById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(InputService inputService, EditorService editorService) {
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
    }

    class ShowAllStocks extends UserActionServiceImpl {
        protected ShowAllStocks(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(InputService inputService, EditorService editorService) {

        }
    }

    class ShowStockById extends UserActionServiceImpl {
        protected ShowStockById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(InputService inputService, EditorService editorService) {

        }
    }

    class DeleteStockById extends UserActionServiceImpl {
        protected DeleteStockById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(InputService inputService, EditorService editorService) {

        }
    }

    class ShowStocksByName extends UserActionServiceImpl {
        protected ShowStocksByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(InputService inputService, EditorService editorService) {

        }
    }
}
