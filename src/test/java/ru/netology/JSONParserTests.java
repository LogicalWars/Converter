package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.JSONParser.jsonToList;
import static ru.netology.JSONParser.listToJson;
public class JSONParserTests {

    private final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private final String fileName = "src\\test\\resources\\data.json";
    private static final String fileNameToWrite = "src\\test\\resources\\test.json";
    List<Employee> listOfEmployee = List.of(
            new Employee(1, "John", "Smith", "USA", 25),
            new Employee(2, "Inav", "Petrov", "RU", 23));

    @AfterAll
    static void deleteTestFile() {
        File file = new File(fileNameToWrite);
        if (file.exists()) file.delete();
    }


    //Проверка, что парсинг возвращает не пустой массив, когда файл JSON имеет данные
    @Test
    void testParseJSONNotEmpty() throws IOException {
        List<Employee> result = jsonToList(readJson(fileName), Employee.class);
        assertNotNull(result);
        assertEquals(listOfEmployee.size(), result.size());
    }

    //Проверка, что парсинг возвращает пустой массив, когда файл JSON не имеет данные
    @Test
    void testParseJSONEmpty() throws IOException {
        FileWriter fw = new FileWriter(fileNameToWrite, false);
        fw.close();
        List<Employee> result = jsonToList(readJson(fileNameToWrite), Employee.class);
        assertNull(result);
    }

    //Проверка, что парсинг возвращает корректные данные
    @Test
    void testParseJSON() throws IOException {
        List<Employee> result = jsonToList(readJson(fileName), Employee.class);
        assertEmployeeListEquals(listOfEmployee, result);
    }

    //Проверка, что запись в файл корректна
    @Test
    void testWriteJson() throws IOException {
        writeJson(listToJson(listOfEmployee, Employee.class), fileNameToWrite);
        List<Employee> result = jsonToList(readJson(fileNameToWrite), Employee.class);
        assertEmployeeListEquals(listOfEmployee, result);
    }

    private void assertEmployeeListEquals(List<Employee> expected, List<Employee> actual) {
        for (int i = 0; i < listOfEmployee.size(); i++) {
            assertEquals(listOfEmployee.get(i), actual.get(i));
        }
    }

    private String readJson(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();
    }

    private void writeJson(String text, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
