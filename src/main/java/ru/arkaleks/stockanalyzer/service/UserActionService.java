package ru.arkaleks.stockanalyzer.service;

public interface UserActionService {

    /**
     * Метод запрашивает у пользователя пункт меню для выполнения.
     */
    int key();

    /**
     * Метод реализует основное действие пользователя.
     */
    void execute(InputService inputService, EditorService editorService);

    /**
     * Метод сообщает пользователю информацию о выбранном действии.
     */
    String info();

}
