package ru.netology;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.CSVParser.parseCSV;

public class CSVParserTests {

    private final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private final String fileName = "test.csv";
    List<Employee> listOfEmployee = List.of(
            new Employee(1, "John", "Smith", "USA", 25),
            new Employee(2, "Inav", "Petrov", "RU", 23));

    //Создаем файл csv для теста
    @BeforeEach
    void createFileCSV() throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (Employee employee : listOfEmployee) {
            fw.write("%d,%s,%s,%s,%d\n".formatted(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getCountry(), employee.getAge()));
        }
        fw.close();
    }

    //Удаляем файл csv для теста
    @AfterEach
    void deleteFileCSV() throws IOException {
        File file = new File(fileName);
        if (file.exists()) file.delete();
    }

    //Проверка, что парсинг возвращает не пустой массив, когда файл csv имеет данные
    @Test
    void testParseCSVNotEmpty() {
        List<Employee> result = parseCSV(columnMapping, fileName, Employee.class);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    //Проверка, что парсинг возвращает пустой массив, когда файл csv не имеет данные
    @Test
    void testParseCSVEmpty() throws IOException {
        FileWriter fw = new FileWriter(fileName, false);
        fw.close();
        List<Employee> result = parseCSV(columnMapping, fileName, Employee.class);
        assertEquals(List.of(), result);
    }

    //Проверка, что парсинг возвращает корректные данные
    @Test
    void testParseCSV() {
        List<Employee> result = parseCSV(columnMapping, fileName, Employee.class);
        for (int i = 0; i < listOfEmployee.size(); i++) {
            assertEquals(listOfEmployee.get(i).getId(), result.get(i).getId());
            assertEquals(listOfEmployee.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(listOfEmployee.get(i).getLastName(), result.get(i).getLastName());
            assertEquals(listOfEmployee.get(i).getCountry(), result.get(i).getCountry());
            assertEquals(listOfEmployee.get(i).getAge(), result.get(i).getAge());
        }
    }
}
