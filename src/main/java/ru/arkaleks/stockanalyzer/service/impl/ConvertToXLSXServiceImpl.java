package ru.arkaleks.stockanalyzer.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.arkaleks.stockanalyzer.service.ConvertToXLSXService;
import ru.arkaleks.stockanalyzer.service.InputService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ConvertToXLSXServiceImpl implements ConvertToXLSXService {

    private InputService inputService;

    @Override
    public boolean convertToXLSXFile(List<String> reportDataList, String filepath) {
        boolean result = false;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("report");
        Row row = sheet.createRow(0);

        for (int i = 0; i < reportDataList.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(reportDataList.get(i));
            sheet.autoSizeColumn(i);
        }

        File currDir = new File(filepath);
        String path = currDir.getAbsolutePath();

        String fileLocation = path + "\\"+ reportDataList.get(1) + ".xlsx";

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
