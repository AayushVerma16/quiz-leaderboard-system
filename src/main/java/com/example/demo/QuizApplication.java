package com.example.demo;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://devapigw.vidalhealthtpa.com/srm-quiz-task/quiz/messages";
        String submitUrl = "https://devapigw.vidalhealthtpa.com/srm-quiz-task/quiz/submit";
        String regNo = "RA2311056030081";

        System.out.println("Started...");

        Set<String> uniqueSet = new HashSet<>();
        Map<String, Integer> leaderboard = new HashMap<>();

        for (int i = 0; i < 10; i++) {

    int retryCount = 0;
    boolean success = false;

    while (!success && retryCount < 10) {

        String url = baseUrl + "?regNo=" + regNo + "&poll=" + i;

        System.out.println("Calling API: Poll " + i);

        try {
            ResponseEntity<Map> response =
                restTemplate.getForEntity(url, Map.class);

            Map body = response.getBody();

            List<Map<String, Object>> events =
                (List<Map<String, Object>>) body.get("events");

            for (Map<String, Object> event : events) {

                String roundId = (String) event.get("roundId");
                String participant = (String) event.get("participant");
                int score = (int) event.get("score");

                String key = roundId + "_" + participant;

                if (!uniqueSet.contains(key)) {
                    uniqueSet.add(key);

                    leaderboard.put(participant,
                        leaderboard.getOrDefault(participant, 0) + score);

                    System.out.println("Added: " + participant + " +" + score);
                } else {
                    System.out.println("Duplicate Ignored: " + participant + " " + roundId);
                }
            }

            success = true; // ✅ exit retry loop

        } catch (Exception e) {
            retryCount++;
            System.out.println("Retry " + retryCount + " for poll " + i);

            Thread.sleep(5000);
        }
    }

    if (!success) {
        System.out.println("Skipping poll " + i + " after 3 retries");
    }

    Thread.sleep(10000);
}

    List<Map.Entry<String, Integer>> sortedList =
    leaderboard.entrySet()
    .stream()
    .sorted((a, b) -> b.getValue() - a.getValue())
    .toList();

    System.out.println("\nFinal Leaderboard:");

for (Map.Entry<String, Integer> entry : sortedList) {
    System.out.println(entry.getKey() + " : " + entry.getValue());
}

int total = leaderboard.values()
    .stream()
    .mapToInt(Integer::intValue)
    .sum();

System.out.println("Total Score: " + total);

List<Map<String, Object>> finalLeaderboard = new ArrayList<>();

for (Map.Entry<String, Integer> entry : sortedList) {
    Map<String, Object> obj = new HashMap<>();
    obj.put("participant", entry.getKey());
    obj.put("totalScore", entry.getValue());
    finalLeaderboard.add(obj);
}

Map<String, Object> requestBody = new HashMap<>();
requestBody.put("regNo", regNo);
requestBody.put("leaderboard", finalLeaderboard);

HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);

HttpEntity<Map<String, Object>> request =
    new HttpEntity<>(requestBody, headers);

ResponseEntity<String> submitResponse =
    restTemplate.postForEntity(submitUrl, request, String.class);

System.out.println("\nSubmission Response:");
System.out.println(submitResponse.getBody());

    }
}