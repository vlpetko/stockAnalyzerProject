package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.EditorMenuService;
import ru.arkaleks.stockanalyzer.service.EditorService;
import ru.arkaleks.stockanalyzer.service.InputService;

public class EditorMenuServiceImpl implements EditorMenuService {
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
