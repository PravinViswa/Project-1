package com.pravin.agecalculator.controller;

import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

@CrossOrigin(origins = "https://pravinviswa.github.io")
@RestController
public class AgeController {

    @GetMapping("/calculate-age")
    public Map<String, Object> calculateAge(@RequestParam String dob, @RequestParam(required = false) String timezone) {
        Map<String, Object> response = new HashMap<>();

        try {
            LocalDate birthDate = LocalDate.parse(dob);

            //To Use timezone if sent from frontend, fallback to UTC
            ZoneId zone = (timezone != null && !timezone.isEmpty()) ? ZoneId.of(timezone) : ZoneOffset.UTC;
            LocalDate today = LocalDate.now(zone);
            Period age = Period.between(birthDate, today);

            response.put("years", age.getYears());
            response.put("months", age.getMonths());
            response.put("days", age.getDays());
            response.put("timezone", zone.toString());

            if (birthDate.isAfter(today)) {
                response.put("status", "future");
            } else {
                response.put("status", "valid");
            }

        } catch (DateTimeParseException | DateTimeException e) {
            response.put("error", "Invalid date or timezone. Format: yyyy-mm-dd, e.g. 2000-01-01");
        }

        return response;
    }
}
