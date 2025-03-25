package ru.netology;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public List<Employee> parseXML(String filename) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));
            NodeList employeeList = doc.getElementsByTagName("employee");
            for (int i = 0; i < employeeList.getLength(); i++) {
                Node node = employeeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        employees.add(parseEmployee((Element) node));
                    }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Ошибка при парсинге XML: " + e.getMessage(), e);
        }
        return employees;
    }

    private Employee parseEmployee(Element employeeElement) {
        long id = Long.parseLong(getTextContent(employeeElement, "id"));
        String firstName = getTextContent(employeeElement, "firstName");
        String lastName = getTextContent(employeeElement, "lastName");
        String country = getTextContent(employeeElement, "country");
        int age = Integer.parseInt(getTextContent(employeeElement, "age"));
        return new Employee(id, firstName, lastName, country, age);
    }

    private String getTextContent(Element employeeElement, String tagName) {
        NodeList nodeList = employeeElement.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : "";
    }
}

