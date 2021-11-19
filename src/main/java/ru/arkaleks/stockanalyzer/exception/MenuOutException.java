package ru.arkaleks.stockanalyzer.exception;

public class MenuOutException extends  RuntimeException {
    public MenuOutException(String msg) {
        super(msg);
    }
}
