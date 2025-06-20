package com.pravin.agecalculator.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://pravinviswa.github.io") // 👈 this line fixes CORS!
public class AgeController {

    @GetMapping("/calculate-age")
    public Map<String, Object> calculateAge(@RequestParam String dob) {
        Map<String, Object> response = new HashMap<>();

        try {
            LocalDate birthDate = LocalDate.parse(dob);
            LocalDate today = LocalDate.now();
            Period age = Period.between(birthDate, today);

            response.put("years", age.getYears());
            response.put("months", age.getMonths());
            response.put("days", age.getDays());
        } catch (DateTimeParseException e) {
            response.put("error", "Invalid date format. Use yyyy-mm-dd");
        }

        return response;
    }
}
