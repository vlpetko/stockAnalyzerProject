package ru.arkaleks.stockanalyzer.service.impl;

import org.apache.poi.ss.usermodel.*;
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
        String[] names = new String[]{"Номер отчета", "Наименование акции", "Начало периода", "Конец периода", "Максимальная стоимость",
                "Дата максимальной стоимости", "Минимальная стоимость", "Дата минимальной стоимости", "Суммарный объем"};
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("report");
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        BorderStyle borderStyle = BorderStyle.MEDIUM;
        style.setBorderBottom(borderStyle);
        style.setBorderRight(borderStyle);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Row row = sheet.createRow(0);
        Row row1 = sheet.createRow(1);

        for (int i = 0; i < reportDataList.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(names[i]);
            cell.setCellStyle(style);

            Cell cell1 = row1.createCell(i);
            cell1.setCellValue(reportDataList.get(i));
            sheet.autoSizeColumn(i);
        }

        File currDir = new File(filepath);
        String path = currDir.getAbsolutePath();

        String fileLocation = path + "\\" + reportDataList.get(1) + ".xlsx";

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
