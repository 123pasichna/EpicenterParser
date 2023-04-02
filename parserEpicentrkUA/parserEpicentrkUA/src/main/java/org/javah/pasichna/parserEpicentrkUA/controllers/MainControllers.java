package org.javah.pasichna.parserEpicentrkUA.controllers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javah.pasichna.parserEpicentrkUA.model.SearchProduct;
import org.javah.pasichna.parserEpicentrkUA.service.ParserManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainControllers {
    private static final String[] COLUMN_HEADERS = {"Найменування", "Посилання", "Стара ціна", "Актуальна ціна"};

    private String searchQuery;

    @GetMapping("/about")
    public String sentGreetingMessage(){
        return "about";
    }

    @PostMapping("/search")
    public ResponseEntity<byte[]> searchProducts(@RequestParam("searchQuery") String searchQuery) throws IOException {

        List<SearchProduct> productList = new ArrayList<>();
        ParserManager parserManager = new ParserManager();
        productList = parserManager.getProductList(searchQuery);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(searchQuery);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUMN_HEADERS[i]);
        }

        int rowNum = 1;
        for (SearchProduct product : productList) {
            Row row = sheet.createRow(rowNum++);

            Cell nameCell = row.createCell(0);
            nameCell.setCellValue(product.getName());

            Cell urlCell = row.createCell(1);
            urlCell.setCellValue(product.getUrl());

            Cell saleCell = row.createCell(2);
            saleCell.setCellValue(product.getSale());

            Cell priceCell = row.createCell(3);
            priceCell.setCellValue(product.getPrice());
        }

        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData("attachment", "resultsSearch.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
    @GetMapping("/parser")
    public String sentParserMessage(){
        return "parser";
    }
}
