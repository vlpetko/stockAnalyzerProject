package ru.arkaleks.stockanalyzer.service;

import java.util.List;

public interface ConvertToXLSXService {

    boolean convertToXLSXFile(List<String> reportDataList, String path);
}
