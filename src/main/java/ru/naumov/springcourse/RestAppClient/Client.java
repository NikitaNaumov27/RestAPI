package ru.naumov.springcourse.RestAppClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.print("Введите название нового датчика: ");
        String sensorName = new Scanner(System.in).nextLine();
        registerSensor(sensorName);
        System.out.println();
        create1000Measurement(sensorName);
        System.out.println();
        getInfoMeasurement();
    }


    private static void create1000Measurement(String sensorName) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            sendMeasurement((int)(Math.random()*(200 + 1)) - 100,
                    random.nextBoolean(), sensorName);
        }
    }


    public static void registerSensor(String sensorName){
        final String url = "http://localhost:8080/sensors/registration";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);
        makePostRequestWithJSONData(url, jsonData);
        System.out.println("Sensor: " + sensorName + " registered successfully");
    }


    public static void getInfoMeasurement(){
        String url = "http://localhost:8080/measurement/";
        String response = new RestTemplate().getForObject(url, String.class);
        System.out.println("Получаем измерения с сервера");
        System.out.println(response);
    }


    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String url = "http://localhost:8080/measurement/add";
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));
        makePostRequestWithJSONData(url, jsonData);
    }


    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);
        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Измерение успешно отправлено на сервер!");
        } catch (HttpClientErrorException e) {
            System.out.println("ОШИБКА!");
            System.out.println(e.getMessage());
        }
    }
}
