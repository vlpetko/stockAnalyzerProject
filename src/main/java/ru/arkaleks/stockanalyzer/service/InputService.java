package ru.arkaleks.stockanalyzer.service;

import java.util.List;

public interface InputService {

    String ask(String question);

    int ask(String question, List<Integer> range);
}

