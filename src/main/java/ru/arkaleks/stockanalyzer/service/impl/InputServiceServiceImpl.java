package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.exception.MenuOutException;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.util.List;
import java.util.Scanner;

public class InputServiceServiceImpl implements InputService {

    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (Integer value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Вне диапазона");
        }
        return key;
    }
}
