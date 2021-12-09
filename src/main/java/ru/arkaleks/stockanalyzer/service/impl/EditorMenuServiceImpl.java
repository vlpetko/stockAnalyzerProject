package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.BaseActionService;
import ru.arkaleks.stockanalyzer.service.EditorMenuService;
import ru.arkaleks.stockanalyzer.service.EditorService;
import ru.arkaleks.stockanalyzer.service.InputService;

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
