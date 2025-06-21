package com.pravinviswa.agecalculator;

import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AgeController {

    @GetMapping("/calculate-age")
    public Map<String, Object> calculateAge(
            @RequestParam String dob, // Format: yyyy-MM-ddTHH:mm
            @RequestParam(required = false) String timezone) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Parse birth datetime from request
            LocalDateTime birthDateTime = LocalDateTime.parse(dob, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Get user timezone, fallback to UTC
            ZoneId zoneId = (timezone != null && !timezone.isEmpty()) ? ZoneId.of(timezone) : ZoneId.of("UTC");

            ZonedDateTime birthZoned = birthDateTime.atZone(zoneId);
            ZonedDateTime nowZoned = ZonedDateTime.now(zoneId);

            if (birthZoned.isAfter(nowZoned)) {
                // Future birth
                Period period = Period.between(nowZoned.toLocalDate(), birthZoned.toLocalDate());
                Duration duration = Duration.between(nowZoned.toLocalTime(), birthZoned.toLocalTime());

                response.put("status", "future");
                response.put("yearsLeft", period.getYears());
                response.put("monthsLeft", period.getMonths());
                response.put("daysLeft", period.getDays());
                response.put("hoursLeft", duration.toHoursPart());
                response.put("minutesLeft", duration.toMinutesPart());
                response.put("secondsLeft", duration.toSecondsPart());
            } else {
                // Past birth
                Period period = Period.between(birthZoned.toLocalDate(), nowZoned.toLocalDate());
                Duration duration = Duration.between(birthZoned.toLocalTime(), nowZoned.toLocalTime());

                response.put("status", "past");
                response.put("years", period.getYears());
                response.put("months", period.getMonths());
                response.put("days", period.getDays());
                response.put("hours", duration.toHoursPart());
                response.put("minutes", duration.toMinutesPart());
                response.put("seconds", duration.toSecondsPart());
            }

            response.put("timezone", zoneId.toString());

        } catch (DateTimeParseException e) {
            response.put("error", "Invalid date-time format. Use format yyyy-MM-ddTHH:mm");
        } catch (Exception ex) {
            response.put("error", "Server error: " + ex.getMessage());
        }

        return response;
    }
}
