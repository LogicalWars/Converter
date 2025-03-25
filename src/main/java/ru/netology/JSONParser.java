package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JSONParser {
    public static <T> String listToJson(List<T> list, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();

        return gson.toJson(list, listType);
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, listType);
    }
}
