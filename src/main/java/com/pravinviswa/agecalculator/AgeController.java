package com.pravinviswa.agecalculator;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

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
                response.put("status", "future");
                response.put("yearsLeft", until.getYears());
                response.put("monthsLeft", until.getMonths());
                response.put("daysLeft", until.getDays());
            } else {
                Period age = Period.between(birthDate, today);
                response.put("status", "past");
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
