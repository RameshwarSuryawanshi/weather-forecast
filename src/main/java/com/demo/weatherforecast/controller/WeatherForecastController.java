package com.demo.weatherforecast.controller;


import com.demo.weatherforecast.jwt.JWTUtility;
import com.demo.weatherforecast.model.JwtRequest;
import com.demo.weatherforecast.model.JwtResponse;
import com.demo.weatherforecast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class WeatherForecastController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new JwtResponse(token);
    }

    @GetMapping(value = "/{city}/summary", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/{city}/hourly", produces = MediaType.APPLICATION_JSON_VALUE)
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
