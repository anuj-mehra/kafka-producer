package com.poc.kafkaproducer.jsonmessage;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class CompareJsonMessages {

    public static void main(String[] args) {
        String json1 = "{\"name\":\"ABC\", \"city\":\"XYZ\", \"state\":\"CA\"}";
        String json2 = "{\"city\":\"XYZ\", \"street\":\"123 anyplace\", \"name\":\"ABC\"}";

        CompareJsonMessages compareJsonMessages = new CompareJsonMessages();
        compareJsonMessages.compare(json1, json2);
    }

    public void compare(String json1, String json2){
        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> firstMap = g.fromJson(json1, mapType);
        Map<String, Object> secondMap = g.fromJson(json2, mapType);

        MapDifference<String, Object> differences = Maps.difference(firstMap, secondMap);
        System.out.println(differences);

        System.out.println(differences.areEqual());
        System.out.println(differences.entriesOnlyOnLeft());
        System.out.println(differences.entriesOnlyOnRight());
        //System.out.println(Maps.difference(firstMap, secondMap));
    }

}
