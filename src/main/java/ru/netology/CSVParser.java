package ru.netology;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser {
    public static <T> List<T> parseCSV(String[] columnMapping, String fileName, Class<T> clazz) {
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);
        strategy.setColumnMapping(columnMapping);
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<T> csv = new CsvToBeanBuilder<T>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при работе с файлом: " + e.getMessage(), e);
        }

    }
}
