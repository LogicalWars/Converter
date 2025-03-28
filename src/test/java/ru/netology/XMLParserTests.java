package ru.netology;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XMLParserTests {
    private final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private final String fileName = "src\\test\\resources\\data.xml";
    private static final String fileNameToWrite = "src\\test\\resources\\test.json";
    List<Employee> listOfEmployee = List.of(
            new Employee(1, "John", "Smith", "USA", 25),
            new Employee(2, "Inav", "Petrov", "RU", 23));

    @AfterAll
    static void deleteTestFile() {
        File file = new File(fileNameToWrite);
        if (file.exists()) file.delete();
    }

    //Проверка, что парсинг возвращает не пустой массив, когда файл xml имеет данные
    @Test
    void testParseXMLNotEmpty() {
        List<Employee> result = new XMLParser().parseXML(fileName);
        assertNotNull(result);
        assertEquals(listOfEmployee.size(), result.size());
    }

    //Проверка, что парсинг выкидывает exception, когда файл xml не имеет данные
    @Test
    void testParseXMLEmpty() throws IOException {
        FileWriter fw = new FileWriter(fileNameToWrite, false);
        fw.close();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> new XMLParser().parseXML(fileNameToWrite));
        assertEquals("Ошибка при парсинге XML: Premature end of file.", exception.getMessage());
    }

    //Проверка, что парсинг возвращает корректные данные
    @Test
    void testParseXML() {
        List<Employee> result = new XMLParser().parseXML(fileName);
        for (int i = 0; i < listOfEmployee.size(); i++) {
            assertEquals(listOfEmployee.get(i), result.get(i));
        }
    }
}
