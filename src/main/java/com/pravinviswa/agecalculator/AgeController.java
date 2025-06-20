package com.pravinviswa.agecalculator;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "https://pravinviswa.github.io") 
@RestController
@RequestMapping("/api")
public class AgeController {

    @GetMapping("/calculate-age")
    public Map<String, Object> calculateAge(
            @RequestParam String dob,
            @RequestParam(required = false) String timezone) {

        Map<String, Object> response = new HashMap<>();

        try {
            LocalDate birthDate = LocalDate.parse(dob);
            ZoneId userZone = (timezone != null && !timezone.isEmpty()) ? ZoneId.of(timezone) : ZoneId.of("UTC");
            LocalDate today = ZonedDateTime.now(userZone).toLocalDate();

            if (birthDate.isAfter(today)) {
                Period until = Period.between(today, birthDate);
                response.put("notBornYet", true);
                response.put("years", until.getYears());
                response.put("months", until.getMonths());
                response.put("days", until.getDays());
            } else {
                Period age = Period.between(birthDate, today);
                response.put("notBornYet", false);
                response.put("years", age.getYears());
                response.put("months", age.getMonths());
                response.put("days", age.getDays());
            }

            response.put("timezone", userZone.toString());

        } catch (DateTimeParseException e) {
            response.put("error", "Invalid date format. Use yyyy-mm-dd");
        }

        return response;
    }
}
