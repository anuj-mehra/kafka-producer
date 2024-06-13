package com.poc.kafkaproducer.jsonmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class CompareJsonsUsingJackson {

    public static void main(String[] args) throws JsonProcessingException {
        String json1 = "[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}]";
        String json2 = "[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}, {\"odsname\": \"posnid\", \"hbasename\":\"pid\"}]";
        String json3 = "[{\"odsname\": \"acct_key\", \"hbasename\":\"akey\"}, {\"odsname\": \"posnid\", \"hbasename\":\"pid\"}]";


        ObjectMapper mapper = new ObjectMapper();

        if(mapper.readTree(json1).equals(mapper.readTree(json2))){
            System.out.println("---same----");
        }else{
            System.out.println("---different----");
        }

        if(mapper.readTree(json3).equals(mapper.readTree(json2))){
            System.out.println("---same----");
        }else{
            System.out.println("---different----");
        }

        List<MyPojo> json1Elements = convertJsonToPojo(json1);
        List<MyPojo> json2Elements = convertJsonToPojo(json2);

        System.out.println("----printing the differences----");

        List<MyPojo> union = new ArrayList<MyPojo>(json1Elements);
        union.addAll(json2Elements);

        // Prepare an intersection
        List<MyPojo> intersection = new ArrayList<MyPojo>(json1Elements);
        intersection.retainAll(json2Elements);

        // Subtract the intersection from the union
        union.removeAll(intersection);

        // Print the result
        for (MyPojo n : union) {
            System.out.println(n.getOdsname());
            System.out.println(n.getHbasename());
        }

    }

    private static List<MyPojo> convertJsonToPojo(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<MyPojo> jsonElements = objectMapper.readValue(json,
                new TypeReference<>() {
                });

        /*for (MyPojo element : jsonElements) {
            System.out.println(element.getHbasename());
            System.out.println(element.getOdsname());
        }*/

        return jsonElements;
    }
}
