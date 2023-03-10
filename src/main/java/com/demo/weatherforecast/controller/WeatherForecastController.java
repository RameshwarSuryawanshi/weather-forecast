package com.demo.weatherforecast.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class WeatherForecastController {

    @GetMapping("/{city}/summary")
    public ResponseEntity<String> getWeatherForecastSummaryByCity(@PathVariable(value = "city") String city) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://forecast9.p.rapidapi.com/rapidapi/forecast/" + city + "/summary/"))
                .header("X-RapidAPI-Key", "fb71857d0bmsh021e84cceacd605p1a0a7cjsn9014e878e6db")
                .header("X-RapidAPI-Host", "forecast9.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok(response.body());
    }

    @GetMapping("/{city}/hourly")
    public ResponseEntity<String> getHourlyWeatherForecastSummaryByCity(@PathVariable(value = "city") String city) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://forecast9.p.rapidapi.com/rapidapi/forecast/" + city + "/hourly/"))
                .header("X-RapidAPI-Key", "fb71857d0bmsh021e84cceacd605p1a0a7cjsn9014e878e6db")
                .header("X-RapidAPI-Host", "forecast9.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return ResponseEntity.ok(response.body());
    }
}
