package ru.netology;

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
    private final String fileName = "src\\test\\resources\\test.xml";
    List<Employee> listOfEmployee = List.of(
            new Employee(1, "John", "Smith", "USA", 25),
            new Employee(2, "Inav", "Petrov", "RU", 23));

    //Создаем файл xml для теста
    @BeforeEach
    void createFileXML() throws IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("staff");
        doc.appendChild(rootElement);

        for (Employee employee : listOfEmployee) {
            rootElement.appendChild(createEmployee(doc, employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getCountry(), employee.getAge()));
        }
        // Записываем xml в файл
        saveToXMLFile(doc);
    }

    private Element createEmployee(Document doc, long id, String firstName, String lastName, String country, int age) {
        Element employee = doc.createElement("employee");

        employee.appendChild(createElement(doc, columnMapping[0], String.valueOf(id)));
        employee.appendChild(createElement(doc, columnMapping[1], firstName));
        employee.appendChild(createElement(doc, columnMapping[2], lastName));
        employee.appendChild(createElement(doc, columnMapping[3], country));
        employee.appendChild(createElement(doc, columnMapping[4], String.valueOf(age)));

        return employee;
    }

    private Element createElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }

    private void saveToXMLFile(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Красивый вывод

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));

        transformer.transform(source, result);
    }

//  Удаляем файл xml для теста
    @AfterEach
    void deleteFileXML() throws IOException {
        File file = new File(fileName);
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
        FileWriter fw = new FileWriter(fileName, false);
        fw.close();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> new XMLParser().parseXML(fileName));
        assertEquals("Ошибка при парсинге XML: Premature end of file.", exception.getMessage());
    }

    //Проверка, что парсинг возвращает корректные данные
    @Test
    void testParseXML() {
        List<Employee> result = new XMLParser().parseXML(fileName);
        for (int i = 0; i < listOfEmployee.size(); i++) {
            assertEquals(listOfEmployee.get(i).getId(), result.get(i).getId());
            assertEquals(listOfEmployee.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(listOfEmployee.get(i).getLastName(), result.get(i).getLastName());
            assertEquals(listOfEmployee.get(i).getCountry(), result.get(i).getCountry());
            assertEquals(listOfEmployee.get(i).getAge(), result.get(i).getAge());
        }
    }
}
