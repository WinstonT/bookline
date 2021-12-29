package com.example.demo.controller.utils;

import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDataReader {

    @Autowired
    private BookService bookService;

    public void readData(){
        List<Book> bookList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/static/data/dataset.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for(int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
                XSSFRow row = sheet.getRow(i);
                Book book = Book.builder()
                        .bookIsbn(String.valueOf((long)row.getCell(0).getNumericCellValue()))
                        .bookTitle(row.getCell(2).getStringCellValue())
                        .bookAuthor(row.getCell(4).getStringCellValue())
                        .bookCategory(row.getCell(5).getStringCellValue())
                        .bookDescription(row.getCell(7).getStringCellValue())
                        .bookImage(row.getCell(6).getStringCellValue())
                        .yearPublished(String.valueOf((int)row.getCell(8).getNumericCellValue()))
                        .bookRating(row.getCell(9).getNumericCellValue())
                        .bookPages(String.valueOf((int)row.getCell(10).getNumericCellValue()))
                        .bookPrice(bookPriceCalculator((int) row.getCell(10).getNumericCellValue()))
                        .build();
                bookList.add(book);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        bookService.createBookIndexBulk(bookList);
    }

    private int bookPriceCalculator(int pages){
        if(pages >= 1000){
            return (int) (Math.random() * 700 + 500) * 1000;
        }
        else if(pages >= 100){
            return pages / 2  * 1000;
        }
        else return 45000;
    }
}
