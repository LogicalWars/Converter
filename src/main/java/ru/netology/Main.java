package ru.netology;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = CSVParser.parseCSV(columnMapping, fileName, Employee.class);
        //TASK №1
        writeString(JSONParser.listToJson(list, Employee.class), "data.json");
        //TASK №2
        writeString(JSONParser.listToJson(new XMLParser().parseXML("data.xml"), Employee.class), "data.json");
        //TASK №3
        JSONParser.jsonToList(readString("data.json"), Employee.class).forEach(System.out::println);

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