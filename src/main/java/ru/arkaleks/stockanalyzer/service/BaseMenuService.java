package ru.arkaleks.stockanalyzer.service;

import java.util.List;

public interface BaseMenuService {

    List<Integer> getActions();

    void fillActions();

    void select(int key);

    void show();


}
