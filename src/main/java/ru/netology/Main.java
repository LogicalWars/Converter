package ru.netology;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static ru.netology.CSVParser.parseCSV;
import static ru.netology.JSONParser.jsonToList;
import static ru.netology.JSONParser.listToJson;

public class Main {
    static final String PATH = "src\\main\\resources\\";
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        //TASK №1
        writeString(listToJson(parseCSV(columnMapping, PATH+"data.csv", Employee.class), Employee.class), PATH+"data.json");
        //TASK №2
        writeString(listToJson(new XMLParser().parseXML(PATH+"data.xml"), Employee.class), PATH+"data.json");
        //TASK №3
        jsonToList(readString(PATH+"data.json"), Employee.class).forEach(System.out::println);

    }

    public static void writeString(String text, String fileName) {
        try {
            Files.writeString(Path.of(fileName), text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}