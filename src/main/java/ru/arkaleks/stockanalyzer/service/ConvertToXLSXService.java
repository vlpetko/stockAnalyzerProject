package ru.arkaleks.stockanalyzer.service;

import org.apache.poi.sl.draw.geom.Path;

import java.util.List;

public interface ConvertToXLSXService {

    void saveReportToFile(List<String> reportDataList);

    void convertToXLSXFile(List<String> reportDataList, Path path);
}
