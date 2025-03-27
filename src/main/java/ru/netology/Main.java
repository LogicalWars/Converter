package ru.netology;
import java.io.*;
import java.util.List;

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

    public static void writeString(String text, String fileName){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            bw.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String filename){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}