package ru.arkaleks.stockanalyzer.service;

import java.io.IOException;

public interface BaseService {

    void addData(String position) throws IOException;

    void generateReport();

    void replaceData();

    void redactor(String pos);
}
