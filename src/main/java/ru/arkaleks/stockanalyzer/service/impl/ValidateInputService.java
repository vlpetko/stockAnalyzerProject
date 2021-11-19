package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.exception.MenuOutException;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.util.List;

public class ValidateInputService implements InputService {

    private final InputService inputService;

    public ValidateInputService(final InputService inputService) {
        this.inputService = inputService;
    }

    @Override
    public String ask(String question) {
        return this.inputService.ask(question);
    }

    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.inputService.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Пожалуйста, выберите пункт Меню.");
            } catch (NumberFormatException nfe) {
                System.out.println("Пожалуйста, введите верные данные.");
            }
        } while (invalid);
        return value;
    }
}
